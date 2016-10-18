package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
* @author <YOUR USER NAME GOES HERE>
*  @author <YOUR PARTNER`S USERNAME GOES HERE>
*
**/
public class Magnet {
    public static Random randomNumberSource = new Random();

    public static final Color MAGNET_COLOR = Color.YELLOW;
    public static final Color WORD_COLOR = Color.BLUE;
    public static final int COLOR_RANGE = 255;

    public static final Dimension FRIDGE_SIZE = Fridge.PREFERRED_SIZE;

    public static final int HORIZONTAL_PADDING = 3; // pixels
    public static final int VERTICAL_PADDING = 2; // pixels

    private int width, height;
    private String word;
    private Rectangle rectangle;
    private Point origin;
    private Point offset;

    private JLabel textLabel;

    /**
     * Creates a new magnet to hold the word
     * @param word the word to be displayed on the magnet
     */
    public Magnet(String word) {

        this.textLabel = new JLabel();

        this.textLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        FontMetrics fontMetrics = this.textLabel.getFontMetrics(this.textLabel
                .getFont());

        width = fontMetrics.stringWidth(word) + (HORIZONTAL_PADDING * 2);
        height = fontMetrics.getHeight() + (VERTICAL_PADDING * 2);
        this.word = word;
        this.textLabel.setText(word);

        origin = new Point(0, 0);
        int fridgeWidth = FRIDGE_SIZE.width;
        int fridgeHeight = FRIDGE_SIZE.height;
        // absolute value protects against failure from superlong words
        origin.x = randomNumberSource.nextInt(Math.abs(fridgeWidth - width));
        origin.y = randomNumberSource.nextInt(fridgeHeight - height);

        rectangle = new Rectangle(origin.x, origin.y, width, height);
        offset = new Point(0, 0);
    }

    /**
     * Gets the word that the magnet is displaying
     * @return the word displayed on this magnet
     */
    //FIXME
    public String getWord() {
        return null;
    }

    /**
     * Gets the Point in the top left corner of the magnet
     * @return the top left corner of the magnet
     */
    //FIXME
    public Point getOrigin() {
        return null;
    }

    /**
     * Gets the text label used for display
     * @return the text label used for display
     */
    //FIXME
    public JLabel getTextLabel() {
        return null;
    }

    /**
     * Gets the width of this magnet including padding
     * @return the width of the magnet
     */
    //FIXME
    public int getWidth() {
        return Integer.MIN_VALUE;
    }

    /**
     * Gets the height of the magnet, including padding
     * @return the height of the magnet
     */
    //FIXME
    public int getHeight() {
        return Integer.MAX_VALUE;
    }

    /**
     * Gets the rectangle describing the location and size of the magnet
     * @return the rectangle describing the magnet
     */
    //FIXME
    public Rectangle getRectangle() {
        return null;
    }

    /**
     * Gets the point that is where the user clicked
     * @return the offset of the click from the origin
     */
    //FIXME
    public Point getOffset() {
        return null;
    }

    /**
     * Sets the click location
     * The offset is the difference between the point passed to this method
     * and the current origin
     * @param point the location of a click
     */
    //FIXME
    public void setOffset(Point point) {
    }

    /**
     * Moves the magnet to the specified point
     * @param point the new location for the top left corner
     */
    public void moveTo(Point point) {
        rectangle.setLocation(point);
        origin.x = (int) rectangle.getX();
        origin.y = (int) rectangle.getY();
    }

    /**
     * Checks to see if the magnet contains this point
     * @param point the point to check
     * @return true if this point is within the bounds of the magnet, false otherwise
     */
    public boolean contains(Point point) {
        return rectangle.contains(point);
    }

    /**
     * Checks to see if this magnet is intersecting another magnet on the screen
     * @param magnet the magnet to check for intersection
     * @return true if the magnets intersect, false otherwise
     */
    public boolean intersects(Magnet magnet) {
        return rectangle.intersects(magnet.getRectangle());
    }

    @Override
    public boolean equals(Object object) {
        if ((object == null) || (this.getClass() != object.getClass()))
            return false;

        if (this == object)
            return true;

        Magnet magnet = (Magnet) object;

        return offset.equals(magnet.getOffset())
                && word.equals(magnet.getWord());
    }

    /**
     * Draws the JLabel for the magnet onto the screen
     * @param tablet a graphics context for drawing
     * @param isSelected whether or not the magnet is selected by the user
     */
    public void draw(Graphics tablet, boolean isSelected) {
        Color color = MAGNET_COLOR;
        if (isSelected)
            color = brighten(color);

        this.textLabel.setBackground(color);
        this.textLabel.setOpaque(true);
        this.textLabel.setLocation(origin.x, origin.y);
        EmptyBorder padding = new EmptyBorder(VERTICAL_PADDING,
                HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING);
        LineBorder line = new LineBorder(Color.black, 1);
        this.textLabel.setBorder(new CompoundBorder(line, padding));
        this.textLabel.repaint();
    }

    /**
     * Brightens a color and returns the brighter color
     * @param color the color to brighten
     * @return the color that has been brightened
     */
    private Color brighten(Color color) {
        int redValue = COLOR_RANGE - ((COLOR_RANGE - color.getRed()) / 2);
        int greenValue = COLOR_RANGE - ((COLOR_RANGE - color.getGreen()) / 2);
        int blueValue = COLOR_RANGE - ((COLOR_RANGE - color.getBlue()) / 2);
        Color newColor = new Color(redValue, greenValue, blueValue);

        return newColor;
    }
}
