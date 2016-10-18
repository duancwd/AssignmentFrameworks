import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JApplet;
import java.util.Random;

/**
 * Fill this applet's rectangular display with a grid of randomly colored
 * squares.
 * 
 * Each colored square fits into a square block of pixels such that: 1/ each
 * colored square is 25x25 pixels, 2/ each side of each colored square is
 * outlined by a 1-pixel thick black line, 3/ each side of each outlined colored
 * square is surrounded by a 3-pixel thick cyan background, except for squares
 * in the bottom row or rightmost column, which may have a thinner or thicker
 * cyan background on the bottom or right border of the applet's display,
 * depending on the relative sizes of the display and blocks.
 * 
 * Here's a crude picture of the top of one such block of pixels holding such a
 * colored square:
 * 
 * pixel block starts at '+' -> +---------------------------------+ | B A C K G
 * R O U N D | outer square starts at '+' -> | +------------------------------+
 * | | O U T L I N E | inner square starts at '+' -> | |
 * +------------------------+ | | | | R A N D O M C O L O R | | | | | | |
 * 
 * 
 * There are many ways to make such a grid of blocks. Here's one way:
 * 
 * Starting from the upper-lefthand corner of the applet's display, draw squares
 * row by row until there's no more space to fit another row into the display.
 * Outline each square with 1-pixel thick black-colored lines, and between each
 * square leave a band of 3-pixel thick cyan-colored lines.
 * 
 * One way to do all that is to add just two missing Java statements (two
 * `for-loops', one inside the other) to the paint() method in the following
 * class. (Number the rows and columns counting from zero; so the row count
 * would go from 0 to the number of rows - 1, and the column count would go from
 * 0 to the number of columns - 1.) And to do that, all you really need to
 * understand is that: 1/ you can access the variables 'rowCount' and
 * 'columnCount' from inside the paint() method because they are declared in the
 * class ColoredSquares (that is, the JApplet) and 2/ their values are
 * calculated in the class' init() method.
 * 
 * Notes: ------ This class will not compile as is since the compiler won't know
 * what the variables 'row' and column' (as used in the drawBlock() method call
 * in the paint() method) are. So to start with, one thing you might do is to
 * replace those variables with whole numbers just to see what happens. That
 * will compile. More generally, though, you should leave the drawBlock() line
 * in the paint() method alone, and maybe add a line above it that gives values
 * to those two variables; for example, something like: int row = 0; int column
 * = 1; and see what happens. The compiler would then be happy and you should
 * see output. Is it what you expected? Once you tire of fiddling with that,
 * figure out how to loop through the blocks, perhaps row-by-row (or
 * column-by-column, or some other way).
 */
@SuppressWarnings("serial")
// this class is for education only. don't serialize.
public class ColoredSquares extends JApplet {
	public static final int BACKGROUND_THICKNESS = 3; // pixels
	public static final int OUTLINE_THICKNESS = 1; // pixels
	public static final int INNER_SQUARE_SIZE = 25; // pixels
	public static final int OUTER_SQUARE_SIZE = INNER_SQUARE_SIZE
			+ OUTLINE_THICKNESS * 2;
	public static final int BLOCK_SIZE = OUTER_SQUARE_SIZE
			+ BACKGROUND_THICKNESS;

	public static final Color BACKGROUND_COLOR = Color.CYAN;
	public static final Color OUTLINE_COLOR = Color.BLACK;

	public static final int COLOR_RANGE = 256;
	private Random randomNumberSource = new Random();

	private int rowCount, columnCount;

	public void init() {
		rowCount = this.getSize().height / BLOCK_SIZE;
		columnCount = this.getSize().width / BLOCK_SIZE;

		this.setBackground(BACKGROUND_COLOR);
	}

	public void paint(Graphics tablet) {
		Graphics2D tablet2D = (Graphics2D) tablet;
		//TODO Add your functionallity here, then remove this comment!
		drawBlock(tablet2D, row, column);	
		
	}

	private void drawBlock(Graphics2D tablet, int row, int column) {
		int xOrigin, yOrigin, width, height;
		Rectangle rectangle;

		xOrigin = column * BLOCK_SIZE + BACKGROUND_THICKNESS;
		yOrigin = row * BLOCK_SIZE + BACKGROUND_THICKNESS;
		width = OUTER_SQUARE_SIZE;
		height = OUTER_SQUARE_SIZE;
		rectangle = new Rectangle(xOrigin, yOrigin, width, height);
		tablet.setColor(OUTLINE_COLOR);
		tablet.fill(rectangle);

		xOrigin = xOrigin + OUTLINE_THICKNESS;
		yOrigin = yOrigin + OUTLINE_THICKNESS;
		width = INNER_SQUARE_SIZE;
		height = INNER_SQUARE_SIZE;
		rectangle = new Rectangle(xOrigin, yOrigin, width, height);
		tablet.setColor(randomColor());
		tablet.fill(rectangle);
	}

	private Color randomColor() {
		int redColorValue, greenColorValue, blueColorValue;

		redColorValue = randomColorValue();
		greenColorValue = randomColorValue();
		blueColorValue = randomColorValue();

		return new Color(redColorValue, greenColorValue, blueColorValue);
	}

	private int randomColorValue() {
		return randomNumberSource.nextInt(COLOR_RANGE);
	}
}