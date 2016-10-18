package edu.indiana.cs.c212;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;

/**
 * An instance of this class reads ChatMessages across a network. It does so by
 * wrapping itself around an instance of ObjectInputStream, letting its
 * superclass do the heavy lifting of reconstituting the Serialized bits sent
 * down a network socket back into objects, then it examines the objects
 * received, via its superclass' readObject() method, to see if those objects
 * are in fact ChatMessages.
 * 
 * This class, paired with ChatMessageWriter, hides some of the complexity of
 * network communication from upper levels of the chat session code.
 * 
 * This class depends on classes ChatMessage and ChatMessageType.
 */
public class ChatMessageReader extends ObjectInputStream {
	public static final ChatMessage END_OF_STREAM = new ChatMessage(
			ChatMessageType.END_OF_STREAM, "");
	public static final ChatMessage ALIEN_MESSAGE = new ChatMessage(
			ChatMessageType.UNKNOWN_TYPE, "");

	public ChatMessageReader(Socket socket) throws IOException {
		super(socket.getInputStream());
	}

	public ChatMessage get() throws IOException {
		try {
			return (ChatMessage) readObject();
		} catch (EOFException exception) {
			return END_OF_STREAM;
		} catch (ClassNotFoundException exception) {
			return ALIEN_MESSAGE;
		}
	}
}
