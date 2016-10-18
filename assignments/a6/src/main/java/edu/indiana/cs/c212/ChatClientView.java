package edu.indiana.cs.c212;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JApplet;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An instance of this class is the graphical frontend for a client of a chat
 * room at SERVER_PORT_NUMBER supported by a machine at SERVER_IP_ADDRESS.
 * 
 * To execute it as an applet, feed the html file to the appletviewer, or to
 * eclipse, or to a webbrowser
 * 
 * To execute it as an application, call it with java ChatClientView
 * 
 * In either case it will create an instance of ChatClient.
 * 
 * This class depends on class ChatClient.
 * 
 * possible extensions for the adventurous:
 * 
 * - allow private messages - allow a friends list - allow an ignore list -
 * allow client to filter cursing - allow multiple chatroom channels - allow
 * transcripts to be kept and sent - improve ui by moving from jtextarea to
 * jtextpane to support colors, images, emoticons, and general html - add
 * whiteboard and/or mix this project with paint project - add a chatbot like
 * eliza so that server can chat too - allow avatars instead of chatnames and
 * make their creation programmable - let server use multiple ports and let
 * clients port hop - allow use of multiple distributed servers to manage
 * heavier loads
 */
@SuppressWarnings("serial")
public class ChatClientView extends JApplet {
	public static final String SERVER_IP_ADDRESS = "silo.cs.indiana.edu";
	public static final int SERVER_PORT_NUMBER = ChatServerView.PORT_NUMBER;	

	private static final int MIN_COLUMN_WIDTH = 10;

	private JButton connectButton;
	private JTextField chatNameField, chatText;
	private JTextArea chatRoom, chattersList;

	private String chatName;
	private ChatClient chatClient;

	private boolean isApplet = true;

	public void init() {
		JPanel holderPanel = makeHolderPanel();
		makeChatClient();		
		setControlsForConnecting();
		add(holderPanel);
	}

	public String getChatName() {
		return chatName;
	}

	public void reportList(String string) {
		chattersList.setText(string);
	}

	/**
	 * Adds the message to the chatRoom. It should be prefixed 
	 * with the current timestamp surrounded by [] square brackets
	 * and postfixed with a newline so that the message is on its own line.
	 *  
	 * @param string the message to add to the chatRoom
	 */
	public void report(String string) {
		//FIXME
	}

	private void setControlsForConnecting() {
		chatNameField.setEnabled(true);
		chatRoom.setEnabled(false);
		chattersList.setEnabled(false);
		chatText.setEnabled(false);

		chatNameField.requestFocus(); // doesn't work on startup!
	}

	private void setControlsForChatting() {
		chatNameField.setEnabled(false);
		chatRoom.setEnabled(true);
		chattersList.setEnabled(true);
		chatText.setEnabled(true);

		setUpForNewChatInput();
	}

	private void setUpForNewChatInput() {
		chatText.setText("");
		chatText.requestFocus();
	}

	/**
	 * 
	 */
	private void makeChatClient() {
		// have to deal with variations between:
		// 1/ being called as an applet on a webserver,
		// 2/ being called as an applet on a local machine,
		// 3/ being called as an application on the command line

		if (isApplet) {
			report("running as an applet: server = " 
					+ SERVER_IP_ADDRESS + ":" + SERVER_PORT_NUMBER );
		} else {
			report("running as an application: host = " 
					+ SERVER_IP_ADDRESS + ":" + SERVER_PORT_NUMBER );
		}

		chatClient = new ChatClient(this, SERVER_IP_ADDRESS);
	}

	/**
	 * This method sets up the JPanel that takes up the whole window
	 * of the application. The chat application is made up of a <br>
	 * 1) Chat Room <br>
	 * 2) Chatters List <br>
	 * 3) Message Panel <br>
	 * 4) Chat Name Panel <br>
	 * 
	 * Each of these components has a method to make them, specified lower in 
	 * the file. You should figure out some way to add these components to
	 * the panel that you return in such a way that it resembles the provided
	 * runnable sample or screenshot.
	 * 
	 * You may find this tutorial
	 * http://docs.oracle.com/javase/tutorial/uiswing/layout/using.html
	 * on layout managers useful.
	 * 
	 * @return the JPanel that will take up the whole window
	 */
	private JPanel makeHolderPanel() {		
		JPanel panel = new JPanel();
		//FIXME
		return panel;
	}

	private JPanel makeChatRoom() {
		chatRoom = new JTextArea();
		DefaultCaret caret = (DefaultCaret) chatRoom.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		report("Chat Room");
		chatRoom.setLineWrap(true);
		chatRoom.setWrapStyleWord(true);
		chatRoom.setEditable(false);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(chatRoom), BorderLayout.CENTER);

		return panel;
	}

	private JPanel makeChattersList() {
		chattersList = new JTextArea("Chatters List");
		chattersList.setColumns(MIN_COLUMN_WIDTH);
		chattersList.setEditable(false);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(chattersList), BorderLayout.CENTER);

		return panel;
	}

	private JPanel makeMessagePanel() {
		chatText = new JTextField("Chat area");
		chatText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				chatClient.sendChatMessage(chatText.getText());
				if (chatClient.isSuccessful())
					setUpForNewChatInput();
			}
		});
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(chatText, BorderLayout.CENTER);

		return panel;
	}

	private JPanel makeChatNamePanel() {
		JLabel chatNameLabel = new JLabel("Chatname:");
		chatNameField = new JTextField(MIN_COLUMN_WIDTH);				

		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (connectButton.getText().equals("Connect")) {
					chatName = chatNameField.getText();
					chatClient.connect();
					if (chatClient.isConnected()) {
						connectButton.setText("Disconnect");
						setControlsForChatting();
					}
				} else {
					chatClient.disconnect();
					if (!chatClient.isConnected()) {
						connectButton.setText("Connect");
						setControlsForConnecting();
					}
				}
			}
		});

		JPanel panel = new JPanel();
		panel.add(chatNameLabel);
		panel.add(chatNameField);
		panel.add(connectButton);

		return panel;
	}

	public static void main(String[] arguments) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final ChatClientView chatClientView = new ChatClientView();
				chatClientView.isApplet = false;
				chatClientView.init();
				final JFrame jframe = new JFrame("Toy Chat");
				jframe.add(chatClientView);
				jframe.setSize(600, 600);
				jframe.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent windowEvent) {
						// doesn't work on startup!
						chatClientView.chatNameField.requestFocusInWindow();
					}

					public void windowClosing(WindowEvent windowEvent) {
						chatClientView.chatClient.disconnect();
						jframe.dispose();
					}
				});

				jframe.setVisible(true);
			}
		});
	}
}
