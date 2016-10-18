package edu.indiana.cs.c212;

import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AccessControlException;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * An instance of this class will act as a chat client in a chat room on
 * SERVER_PORT_NUMBER supported by the machine at serverIPAddress. It is created
 * by an instance of ChatClientView.
 * 
 * This class depends on classes ChatClientView, ChatMessageReader, and
 * ChatMessageWriter.
 * 
 * Special notes (for advanced readers only):
 * 
 * When communicating with another computer over a network, you have to try to
 * open your output stream *before* your input stream. You can always open an
 * output stream (to try to write to the remote computer), but to open an input
 * stream (to try to read from the remote computer) the computer you're trying
 * to establish communication links with must have already opened *its* output
 * stream (so that you can read from it). Since it might be waiting on you to
 * open your output stream (so that it could open its input stream), if you both
 * try to open your input streams before your output streams, you'd both wait on
 * each forever. However, if you both open your output streams first, those
 * actions are guaranteed to work. So you are guaranteeing that by the time that
 * the remote computer gets around to opening its input stream that action will
 * work, and vice versa.
 * 
 * Also, its useful to know that before any communication link can be
 * established, some sort of header information needs to be exchanged. In the
 * case of Socket object streams, the link is being established over TCP/IP and
 * the stream header contains a magic number and version number. The first thing
 * that's suppsoed to happen is that those are read from the stream and
 * verified. That action will block until the remote computer's
 * ObjectOutputStream has written that header information out and flushed its
 * buffer. So unless its ObjectOutputStream's writeObject() method is followed
 * by a flush() nothing will happen. This particular matter is taken care of for
 * you by the low-level plumbing in the custom ChatMessageWriter class, so you
 * don't need to worry about it in this particular program but it's something
 * that's good to know if you ever write other networking programs.
 */
public class ChatClient {
	private static String serverIPAddress;
	public static final int SERVER_PORT_NUMBER = ChatClientView.SERVER_PORT_NUMBER;

	private static final ChatMessage END_OF_STREAM = ChatMessageReader.END_OF_STREAM;

	private ChatClientView chatClientView;
	private String host;
	private boolean isConnected;
	private boolean messageSentSuccessfully;
	private Thread receiverThread;
	private Socket socket;
	private ChatMessageReader messageReader;
	private ChatMessageWriter messageWriter;

	public ChatClient(ChatClientView chatClientView, String serverIPAddress) {
		this.chatClientView = chatClientView;
		this.serverIPAddress = serverIPAddress;
		isConnected = false;
		messageSentSuccessfully = false;
		receiverThread = null;
		socket = null;
		messageReader = null;
		messageWriter = null;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public boolean isSuccessful() {
		return messageSentSuccessfully;
	}

	public void connect() {
		if (isConnected) {
			report("already connected");
			return;
		}

		setUpConnection();
	}

	public void disconnect() {
		if (!isConnected) {
			report("not connected");
			return;
		}

		shutDownConnection();
	}

	public void sendChatMessage(String string) {
		if (!isConnected) {
			report("must first connect");
			return;
		}

		ChatMessageType type = ChatMessageType.TEXT;
		messageSentSuccessfully = sendMessageToServer(type, string);
		if (!messageSentSuccessfully)
			report("**network problem. maybe try again?**");
	}

	private boolean sendMessageToServer(ChatMessageType type, String string) {
		ChatMessage message = new ChatMessage(type, string);
		boolean successfulTransmission = messageWriter.put(message);
		return successfulTransmission;
	}

	private void setUpConnection() {
		String chatName = getChatName();
		if ((chatName == null) || chatName.isEmpty()
				|| chatName.trim().isEmpty()) {
			report("chatname must be non-empty");
			return;
		}

		Thread thread = new Thread(new TryToConnectToServer());
		thread.start();
		waitToSeeIfConnectionToServerWillWork(thread);

		if (isConnected) {
			makeAThreadToHandleIncomingMessages();
		}
	}

	private void shutDownConnection() {
		int MAX_DELIVERY_TIME_IN_MILLISECONDS = 1000;

		try {
			isConnected = false;
			receiverThread.interrupt();
			receiverThread = null;
			sendMessageToServer(ChatMessageType.DISCONNECT, "");
			try {
				Thread.sleep(MAX_DELIVERY_TIME_IN_MILLISECONDS);
			} catch (InterruptedException exception) {
			}
			messageWriter.close();
			messageReader.close();
			socket.close();
		} catch (IOException exception) {
			report("**warning: network problem while disconnecting**");
		}
	}

	private void waitToSeeIfConnectionToServerWillWork(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException exception) {
		}
	}

	private void makeAThreadToHandleIncomingMessages() {
		receiverThread = new Thread(new ReceiveMessagesFromServer());
		receiverThread.start();
	}

	private class TryToConnectToServer implements Runnable {
		public void run() {
			try {
				socket = new Socket(serverIPAddress, SERVER_PORT_NUMBER);
				messageWriter = new ChatMessageWriter(socket);
				messageReader = new ChatMessageReader(socket);

				String chatName = getChatName();
				if (!sendMessageToServer(ChatMessageType.CONNECT, chatName))
					reportDenial();
				else
					isConnected = true;
			} catch (UnknownHostException exception) {
				report("server unknown");
			} catch (IOException exception) {
				reportDenial();
			} catch (AccessControlException exception) {
				report("unable to connect when run as a local applet");
			}
		}
	}

	private class ReceiveMessagesFromServer implements Runnable {
		public void run() {
			try {
				ChatMessage message = null;
				while ((message = messageReader.get()) != END_OF_STREAM) {
					String input = message.getMessage();
					switch (message.getType()) {
					case TEXT: {
						report(input);
						break;
					}
					case UPDATE: {
						reportList(input);
						break;
					}
					}
				}
			} catch (IOException exception) {
				if (!exception.getMessage().equals("Socket closed"))
					report("**warning: network problem while receiving**");
			}
		}
	}

	private void reportDenial() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String time = timeFormat.format(new Date());
		String message = time + " server is dead or is denying entrance";
		report(message);
	}

	private void reportList(String string) {
		chatClientView.reportList(string);
	}

	private void report(String string) {
		chatClientView.report(string);
	}

	private String getChatName() {
		return chatClientView.getChatName();
	}
}
