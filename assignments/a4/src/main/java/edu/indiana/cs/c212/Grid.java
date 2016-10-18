package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Grid {
	public static final int TILE_INNER_SQUARE_SIZE = 25; // pixels
	public static final int TILE_OUTLINE_THICKNESS = 1; // pixels
	public static final int TILE_SIZE = TILE_INNER_SQUARE_SIZE
			+ TILE_OUTLINE_THICKNESS * 2;
	public static final Color TILE_OUTLINE_COLOR = Color.BLACK;

	private Tile[][] grid;
	private int rowCount, columnCount;
	private Point center;

	private Color backgroundColor;

	public Grid(int xOrigin, int yOrigin, int rowCount, int columnCount,
			Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		center = new Point(rowCount / 2, columnCount / 2);

		grid = new Tile[rowCount][columnCount];
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				grid[row][column] = new Tile(xOrigin + column * TILE_SIZE,
						yOrigin + row * TILE_SIZE);				
			}			
		}
		turnOff();
	}

	public void draw(Graphics2D tablet) {
		for (Tile[] rowOfTiles : grid) {
			for (Tile tile : rowOfTiles) {
				tile.draw(tablet);
			}
		}
	}

	public void turnOff() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				turnOff(row, column);
			}
		}
	}

	public void turnOff(Point point) {
		if(contains(point))
			turnOff(getRow(point), getColumn(point));
	}

	public void turnOn(Point point, Color color) {
		if(contains(point))
			turnOn(getRow(point), getColumn(point), color);
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public Point getCenter() {
		return center;
	}

	public int getWidth() {
		return columnCount * TILE_SIZE;
	}

	public int getHeight() {
		return rowCount * TILE_SIZE;
	}

	public boolean contains(Point point) {
		boolean isInRowRange = getRow(point) < rowCount;
		boolean isInColumnRange = getColumn(point) < columnCount;

		return (isInRowRange && isInColumnRange);
	}

	private void turnOn(int row, int column, Color color) {
		grid[row][column].setOutlineColor(TILE_OUTLINE_COLOR);
		grid[row][column].setInnerColor(color);
	}

	private void turnOff(int row, int column) {
		grid[row][column].setOutlineColor(backgroundColor);
		grid[row][column].setInnerColor(backgroundColor);
	}

	private int getRow(Point point) {
		return point.x;
	}

	private int getColumn(Point point) {
		return point.y;
	}

	
}
