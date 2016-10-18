package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.StringTokenizer;

/**
* @author <YOUR USER NAME GOES HERE>
*  @author <YOUR PARTNER`S USERNAME GOES HERE>
*
**/
@SuppressWarnings("serial")
public class WordPlay extends JFrame {
    public static final Color COLOR = Fridge.COLOR;

    private Fridge fridge;
    private Album album;

    public WordPlay() {
        JPanel panel = setupScreen();
        fetchWords();
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
    }

    public final void paint(Graphics tablet) {
        fridge.repaint();
        album.repaint();
    }

    private JPanel setupScreen() {
        fridge = new Fridge();
        album = new Album(fridge);

        JPanel holderPanel = new JPanel();
        holderPanel.setLayout(new BorderLayout());
        holderPanel.add(fridge, BorderLayout.CENTER);
        holderPanel.add(album, BorderLayout.EAST);
        holderPanel.setBackground(COLOR);

        return holderPanel;
    }

    /**
     * This method gets words from the provided input string.
     * It parses the string through a number of pattern matchers to remove whitespace, punctuation,
     * and digits. Then it removes those characters, and runs the string through a tokenizer to 
     * extract individual words.
     * 
     * To implement this method, look at the String class (particularly the replaceAll() method)
     * And the StringTokenizer class.
     */
    //FIXME
    private void fetchWords() {
        String inputString = null;
        if (inputString == null) {
            inputString = "please feed me";
        }
        //FIXME
    }

    public static void main(final String[] args) {
        WordPlay words = new WordPlay();
        words.setDefaultCloseOperation(EXIT_ON_CLOSE);
        words.setSize(Fridge.PREFERRED_SIZE);
        words.setVisible(true);
    }
}
