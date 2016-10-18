package edu.indiana.cs.c212;

/**
 * This enumeration specifies all the types of messages in the chat protocol.
 */
public enum ChatMessageType {
	CONNECT, // client->server: i want to connect as a chat client
	DISCONNECT, // client->server: i want to disconnect as a chat client
	TEXT, // client->server: message body contains some chat content
	UPDATE, // server->client: message body contains a list of chatters
	END_OF_STREAM, // out-of-band communication: socket has already been closed
	UNKNOWN_TYPE; // out-of-band communication: message is of unknown type
}
