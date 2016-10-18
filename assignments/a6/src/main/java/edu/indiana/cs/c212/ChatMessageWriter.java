package edu.indiana.cs.c212;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * An instance of this class writes ChatMessages across a network. It does so by
 * wrapping itself around an instance of ObjectOutputStream, letting its
 * superclass do the heavy lifting of Serializing ChatMessage objects into bit
 * streams to send down a network socket via its superclass' writeObject()
 * method, then immediately following that with a flush() to trigger sending the
 * buffer's contents at that moment rather than the method's default behavior of
 * waiting until the buffer is full.
 * 
 * Note that its put() method returns true if it manages to successfully write
 * the ChatMessage, otherwise it returns false.
 * 
 * This class, paired with ChatMessageReader, hides some of the complexity of
 * network communication from upper levels of the chat session code.
 * 
 * This class depends on class ChatMessage.
 */
public class ChatMessageWriter extends ObjectOutputStream {
	public ChatMessageWriter(Socket socket) throws IOException {
		super(socket.getOutputStream());
	}

	public boolean put(ChatMessage chatMessage) {
		try {
			writeObject(chatMessage);
			flush();
			return true;
		} catch (IOException exception) {
			return false;
		}
	}
}
