package edu.indiana.cs.c212;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Collection;

/**
 * An instance of this class is a server of a chat room hosted on PORT_NUMBER.
 * It is created by an instance of ChatServerView, and for each chat client who
 * asks to join the chat room it creates a new instance of
 * ChatServerClientHandler running in a new thread to handle that client.
 * 
 * 
 * ------------- | | ========== | S | - ChatServerClientHandler1 <-> | Client |
 * | E | ========== PORT | R | ========== | V | - ChatServerClientHandler2 <-> |
 * Client | | E | ========== | R | ========== | | - ChatServerClientHandler3 <->
 * | Client | ------------- ==========
 * 
 * 
 * Background notes on basic client-server networking:
 * 
 * One standard way for a chat room to work is the hub-and-spoke model. That
 * model uses a central server and many distributed clients. The server sits on
 * some machine somewhere and client machines connect to the server to talk to
 * each other by talking only to the server. But the server redirects their
 * communications to all clients who have connected up to that moment. That way,
 * the clients appear to be talking only to each other, yet they don't need to
 * know each other's machine addresses. (This architecture can be generalized
 * for many different applications; for example, multi-player online games.)
 * 
 * Large programs are inherently complicated, but as soon as several instances
 * of them have to work across a network they become even more complicated. That
 * comes about because of two main sources of problems: network failure and
 * human error.
 * 
 * The java.net package supplies two classes, ServerSocket and Socket, to make
 * it a little easier to link people and computers over networks. Those two
 * classes work together.
 * 
 * A ServerSocket represents a listener that waits for network connection
 * requests from clients, while a Socket represents one endpoint of a network
 * connection. (In fact, it's a TCP/IP connection, as opposed to some other one,
 * like UDP or FTP or whatever, but that's not important here.) A Socket can be
 * created by a client and sent as a connection request to a server, but a
 * Socket can also be created by a server to handle a connection request from a
 * client. (That is, Sockets can be either incoming or outgoing.) So a single
 * server can create multiple sockets and can also handle multiple connections.
 * 
 * Note that a ServerSocket does not itself handle connections; it just listens
 * for connection requests and creates Sockets to handle the actual connections.
 * Think of a ServerSocket like a servant who listens for knocks on a door (a
 * port) and a Socket like a servant (one of many) who is sent to actually
 * answer the door.
 * 
 * When constructing a ServerSocket object you have to specify the port number
 * (the door) on which the server will listen. That port number must be in the
 * range 0 to 65535 (i.e. 2^16 - 1), and it should generally be greater than
 * 1024 (i.e. 2^10), as historically those smaller numbers were reserved for
 * special servers (like telnet, mail, http, and so on) before the age of
 * personal computers. The ServerSocket constructor might throw a
 * SecurityException if you specify a smaller port number. Also, if you specify
 * a port number for a port that is already in use, an IOException can occur.
 * (This means, for example, that if you kill the Server and then try to restart
 * it on the same port right away that port may be reported as busy.)
 * 
 * Once you create a ServerSocket, it listens for connection requests. If the
 * port isn't busy, the ServerSocket's accept() method will accept such a
 * request, establish a connection with the currently requesting client, and
 * then it will return a Socket that can be used for communication with that
 * client. However, note that the accept() method will just sit there until
 * someone knocks on the door. It *won't return* (the technical term is that it
 * will *block*) until it receives a connection request (or until some error
 * occurs). Also, note that the ServerSocket will continue listening for
 * connections until it is close()d, or until some error occurs, or until the
 * program dies in some way. So the way it usually works is that you put it in a
 * loop and keep accept()ing as long as you want, and for each connection you
 * accept(), you spawn a new thread to handle that, so that you don't get
 * blocked while waiting for the next one. In this particular class, the class
 * executed by such a newly spawned thread would be a new instance of
 * ChatServerClientHandler.
 * 
 * This class depends on classes ChatServerView, ChatServerClientHandler,
 * ChatMessage, ChatMessageType, and ChatMessageWriter.
 * 
 * Possible extensions for the adventurous (for advanced readers only):
 * 
 * -could keep a log of chat connections and chat messages. -could generate
 * internal ids to separate possible client name clashes. -could support
 * multiple chat rooms. -could check for decayed client lines by spawning a ping
 * thread that wakes up every few minutes to check to see if any connections are
 * dead. that could take care of any clients who close connection without
 * following the disconnect protocol and also any clients whose network
 * connections rot or simply die (their computer died, their network card died,
 * their router died, their isp died, there's a storm, they lost power, someone
 * with a backhoe accidentally cut their cable, a bird pooped in their satellite
 * dish, etc...). -could handle the port bind problem... that has to do with
 * shutting down the ServerSocket itself instead of just the sockets that the
 * threads create off of it. -might consider thread pooling to handle cases of
 * heavy load. -server could be made a singleton since only one can occupy a
 * port.
 * 
 * Technical note (for advanced readers only):
 * 
 * The ideal data structure to use here seems to be a map from a key to a tuple;
 * and, at first, a natural choice for such a map's key might seem to be each
 * client's name or userid. But since this is a network application, and an
 * insecure and unencrypted one to boot, forcing clients to use their real
 * names, or requiring them to use real userids, would be overly demanding---not
 * to mention a security risk. Further, even just forcing clients to try to
 * generate unique chatnames (or having the server generate unique random
 * chatnames for them, which they and other chatters would then have to get used
 * to) seems both artificial and a burden for the clients. So what we want is a
 * casual-use chat system that would be workable over insecure and unencrypted
 * lines by arbitrary clients with no prior agreement between them as to choice
 * of chatname.
 * 
 * Here's the workaround used here: Since the server must generate a new socket
 * each time a client connects, it's feasible to use *that socket* as the map's
 * key. The most natural map would then be from each client's socket to tuples
 * containing that client's name (which is a String) and that client's output
 * stream (which is a ChatMessageWriter, which is a custom subclass of
 * ObjectOutputStream). But maps to tuples are awkward to write in Java because
 * Java has no built-in notion of Tuple. So instead it seems reasonable to use
 * two* maps, one from socket to client string and one from socket to output
 * stream. (Stepping up one more complexity level to, say, a ConcurrentHashMap
 * would be even more overkill for an introductory course.) The one downside is
 * that clients would then be listed either in order of *addition to the
 * chatroom* (if we used a LinkedHashMap for the socket-to-client map), or in
 * sorted order by their *socket* (if we used a TreeMap for the socket-to-client
 * map, which would of course take the client's socket as its key, not by the
 * client's name). Of course, we could do more work and keep yet another map, or
 * just a separate List, of current clients, and keep that sorted, so that
 * whenever it changes that's what is broadcast, but for such a simple chat room
 * program suite there seemed no point. So the simplest strategy seemed to be to
 * go with a LinkedHashMap for the socket-to-client's name map and a plain old
 * HashMap for the socket-to-chatclient's output stream map. Oh well.
 */
public class ChatServer implements Runnable {
	public static final int PORT_NUMBER = ChatServerView.PORT_NUMBER;

	private ChatServerView chatServerView;
	private boolean isRunning;
	private Map<Socket, String> socketToChatClientMap;
	private Map<Socket, ChatMessageWriter> socketToChatWriterMap;

	public ChatServer(ChatServerView chatServerView) {
		this.chatServerView = chatServerView;
		isRunning = false;
		socketToChatClientMap = new LinkedHashMap<Socket, String>();
		socketToChatWriterMap = new HashMap<Socket, ChatMessageWriter>();
	}

	public void run() {
		isRunning = true;
		report("chat server is running.");

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while (isRunning) {
				Socket socket = serverSocket.accept();
				String hostName = socket.getInetAddress().getHostName();
				report("a client has connected from " + hostName);
				ChatMessageWriter writer = new ChatMessageWriter(socket);
				socketToChatWriterMap.put(socket, writer);
				ChatServerClientHandler handler = new ChatServerClientHandler(
						this, socket);
				Thread thread = new Thread(handler);
				thread.start();
			}
		} catch (IOException exception) {
			report("network problem. chat server shutting down.");
			report(exception.getMessage()); // port address already in use...
		} catch (SecurityException exception) {
			report("security problem. chat server shutting down.");
			report(exception.getMessage());
		} finally {
			try {
				serverSocket.close();
			} catch (Exception exception) {
				report("reporting a problem i can't do anything about");
				report(exception.getMessage());
			}
		}
	}

	public void stop() {
		report("chat server is stopping.");
		isRunning = false;
	}

	public void report(String string) {
		chatServerView.report(string);
	}

	public synchronized void remove(Socket socket) throws IOException {
		(socketToChatWriterMap.get(socket)).close();
		socketToChatWriterMap.remove(socket);		
	}

	public synchronized void addToChattersList(Socket socket, String chatClient) {
		socketToChatClientMap.put(socket, chatClient);
	}

	public synchronized void removeFromChattersList(Socket socket) {
		socketToChatClientMap.remove(socket);
	}

	public synchronized void broadcastChattersList() {
		String NEWLINE = "\n";
		String string = "";
		for (String client : socketToChatClientMap.values()) {
			string += client + NEWLINE;
		}

		broadcastMessage(ChatMessageType.UPDATE, string);
	}

	public synchronized void broadcastMessage(ChatMessageType type,
			String string) {
		ChatMessage message = new ChatMessage(type, string);
		for (ChatMessageWriter writer : socketToChatWriterMap.values()) {
			boolean success = writer.put(message);
			if (!success)
				report("warning: network failure on writer " + writer);
		}
	}
}
