package edu.indiana.cs.c212;

import java.io.IOException;
import java.net.Socket;

/**
 * An instance of this class handles a single chat client in a chat room. It is
 * created by an instance of ChatServer and on creation is told which Socket the
 * client is connecting on. To read ChatMessages from its client it first
 * creates a ChatMessageReader for that Socket. It doesn't directly talk to its
 * client. Instead, it passes all ChatMessages it generates to its ChatServer
 * creator so that its ChatServer can broadcast them to all connected clients.
 * 
 * This class depends on classes ChatServer, ChatMessage, ChatMessageType, and
 * ChatMessageReader.
 */
public class ChatServerClientHandler implements Runnable {
	private static final ChatMessage END_OF_STREAM = ChatMessageReader.END_OF_STREAM;
	private static final ChatMessage ALIEN_MESSAGE = ChatMessageReader.ALIEN_MESSAGE;

	private ChatServer chatServer;
	private Socket socket;
	private String chatClient;
	private ChatMessageReader messageReader;

	public ChatServerClientHandler(ChatServer chatServer, Socket socket) {
		this.chatServer = chatServer;
		this.socket = socket;
		chatClient = null;
		messageReader = null;
	}

	public void run() {
		try {
			messageReader = new ChatMessageReader(socket);
			ChatMessage initialMessage = messageReader.get();
			if (!obeysProtocol(initialMessage)) {
				chatServer.report("client isn't obeying protocol");
				chatServer.remove(socket);
				return;
			}
			chatClient = initialMessage.getMessage();
			welcome();

			ChatMessage message;
			ChatMessageType type = ChatMessageType.TEXT;
			while ((message = messageReader.get()) != END_OF_STREAM) {
				switch (message.getType()) {
				case TEXT: {
					String input = message.getMessage();
					String output = "<" + chatClient + "> -> " + input;
					chatServer.report(output);
					chatServer.broadcastMessage(type, output);
					break;
				}
				case DISCONNECT: {
					chatServer.report(chatClient + " has disconnected");
					chatServer.removeFromChattersList(socket);
					chatServer.remove(socket);
					chatServer.broadcastChattersList();
					String output = "server -> " + chatClient + " has left.";
					chatServer.broadcastMessage(type, output);
					return;
				}
				default: {
					chatServer.report(chatClient + " broke protocol");
					chatServer.removeFromChattersList(socket);
					chatServer.remove(socket);
					chatServer.broadcastChattersList();
					return;
				}
				}
			}
		} catch (IOException exception) {
			chatServer.report("ioexception " + exception.getMessage());
		} finally {
			try {
				messageReader.close();
				socket.close();
				chatServer.report("handler " + this + " died cleanly");
			} catch (IOException exception) {
				chatServer.report("handler " + this + " didn't die cleanly");
				chatServer.report(exception.getMessage());
			}
		}
	}

	private boolean obeysProtocol(ChatMessage initialMessage) {
		return ((initialMessage != ALIEN_MESSAGE)
				&& (initialMessage.getType() == ChatMessageType.CONNECT)
				&& (initialMessage.getMessage() != null)
				&& !initialMessage.getMessage().isEmpty() && !initialMessage
				.getMessage().trim().isEmpty());
	}

	private void welcome() {
		chatServer.report(chatClient + " has connected");
		chatServer.addToChattersList(socket, chatClient);
		chatServer.broadcastChattersList();
		String output = "server -> " + chatClient + " has entered.";
		chatServer.broadcastMessage(ChatMessageType.TEXT, output);
	}
}
