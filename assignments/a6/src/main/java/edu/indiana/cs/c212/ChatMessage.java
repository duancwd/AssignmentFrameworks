package edu.indiana.cs.c212;

import java.io.Serializable;

/**
 * An instance of this class is a message sent in a chat session. Each
 * ChatMessage has some type, specified by ChatMessageType, and some content (a
 * String, which may be empty). Also, it has to be Serializable because for
 * network transport it will be converted to a stream of bits and sent down an
 * ObjectOutputStream then reconstituted back into an object at the other end in
 * an ObjectInputStream.
 * 
 * This class depends on class ChatMessageType.
 */
public class ChatMessage implements Serializable {
	protected static final long serialVersionUID = 1L;

	private ChatMessageType type;
	private String message;

	public ChatMessage(ChatMessageType type, String message) {
		this.type = type;
		this.message = message;
	}

	public ChatMessageType getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}
