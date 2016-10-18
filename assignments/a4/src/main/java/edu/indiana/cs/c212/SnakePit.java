package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

/**
 * Please edit the commented methods in the "Methods for Snake" section.
 */
@SuppressWarnings("serial")
public class SnakePit extends JPanel{
	private Grid grid;
	private Snake snake;
	private FruitList fruitList;
	private boolean gameStarted;

	public SnakePit(Grid grid) {
		this.grid = grid;
		fruitList = new FruitList();
		snake = new Snake(this, grid);
		gameStarted = false;		
	}

	// ///////////////////////////////////////////////////////////////
	// Methods for Slytherin:
	// ///////////////////////////////////////////////////////////////

	public void changeState() {
		if (snake.isAlive()) {
			snake.changeState();
		}
	}

	public void takeActionAt(Point point) {
		gameStarted = true;

		if (snake.isAlive()) {
			considerAddingAFruitAt(point);
		} else {
			restart();
		}
	}

	@Override
	public void paintComponent(Graphics tab) {
		grid.turnOff();
		Graphics2D tablet = (Graphics2D) tab;
		fruitList.draw();
		snake.draw();
		grid.draw(tablet);

		if (!gameStarted) {
			String text = "click to feed the snake";
			drawTextCentered(text, tablet);			
		}

		if (!snake.isAlive()) {
			String text = "click to restart";
			drawTextCentered(text, tablet);
		}
	}

	// ///////////////////////////////////////////////////////////////
	// Methods for Snake:
	// //FIXME
	// These are methods you will be implementing.
	// ///////////////////////////////////////////////////////////////

	/**
	 * This method returns the state of the fruitList for the snakePit to use in other areas.
	 * @return whether the fruitList has a fruit or not.
	 */
	public boolean hasFruit() {
		//FIXME
		return true;
	}

	/**
	 * This methods returns the state of the given point in the fruitList, whether it is full of fruit, or not.
	 * @param point on the grid
	 * @return a boolean stating whether the fruitList has a fruit at the given point
	 */
	public boolean hasFruitAt(Point point) {
		//FIXME
		return false;
	}

	/**
	 * Given a point in the list, this method removes a fruit from fruitList
	 * @param point on the grid
	 */
	public void removeFruitAt(Point point) {
		//FIXME
	}
	
	public List<Fruit> getFruits() {
		return fruitList.getFruits();
	}
	
	// ///////////////////////////////////////////////////////////////

	public void considerAddingAFruitAt(Point point) {
		boolean isOnGrid = grid.contains(point);
		
		boolean isOccupiedBySnake = false;
		if (snake != null)
			isOccupiedBySnake = snake.occupies(point);
		
		//FIXME uncomment when you add the hasAFruit method
		/*boolean isOccupiedByFruit = fruitList.hasAFruitAt(point);
		boolean isUnoccupied = !(isOccupiedBySnake || isOccupiedByFruit);

		if (isOnGrid && isUnoccupied) {
			fruitList.add(new Fruit(grid, point));
		}*/
	}

	public void restart() {
		grid.turnOff();
		fruitList.clear();
		snake = new Snake(this, grid);
	}

	private void drawTextCentered(String text, Graphics2D tablet) {
		int FONT_SIZE = 30;
		Font font = new Font("Times Roman", Font.PLAIN, FONT_SIZE);
		tablet.setFont(font);
		FontMetrics fontMetrics = tablet.getFontMetrics();
		int fontDescent = fontMetrics.getDescent();
		Rectangle2D textBounds = fontMetrics.getStringBounds(text, tablet);
		int textWidth = (int) textBounds.getWidth();
		int textHeight = (int) textBounds.getHeight();

		int centeredWidth = (grid.getWidth() - textWidth) / 2;
		int centeredHeight = (grid.getHeight() - textHeight) / 2;
		int xOrigin = centeredWidth;
		int yOrigin = centeredHeight - textHeight + fontDescent;

		Rectangle textBox = new Rectangle(xOrigin, yOrigin, textWidth,
				textHeight);

		tablet.setColor(Color.WHITE);
		tablet.fill(textBox);
		tablet.setColor(Color.BLACK);
		tablet.drawString(text, centeredWidth, centeredHeight);
	}
}
