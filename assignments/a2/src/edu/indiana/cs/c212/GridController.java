package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GridController{
	
	public GridController(int width, int height){
	
		gui = new JPanel();
		gui.setPreferredSize(new Dimension(width, height));
		int tileSize = Tile.DEFAULT_SIZE;
		
		if(width < height) {
			height = width;
		} else {
			width = height;
		}
		
		Grid grid = new GridImpl(new Dimension(width / tileSize,
				height/ tileSize));
		GridLayout layout = new GridLayout(width / tileSize,
				height / tileSize);
			layout.setHgap(4);
		layout.setVgap(4);
			
		gui.setLayout(layout);
		Iterator<Tile> tiles = grid.getTiles();
		while(tiles.hasNext()){
			Tile tile = tiles.next();
			gui.add(tile);
		}
		
		gui.setVisible(true);
		gui.setOpaque(true);
		gui.setBackground(Color.CYAN);
		
		this.grid = grid;
		this.rowCount = grid.getRows();
		this.columnCount = grid.getCols();
		
		appear();
	}
	
	public void repaint(){
		gui.repaint();
	}
	
	public void vanish() {
		Iterator<Tile> tiles = grid.getTiles();
		while(tiles.hasNext()){ 
			Tile tile = tiles.next();
			turnOffTile(tile);
		}
	}

	public void appear() {
		Iterator<Tile> tiles = grid.getTiles();
		while(tiles.hasNext()){ 
			Tile tile = tiles.next();
			turnOnTile(tile);
		}
	}

	public void changeColors() {
		Iterator<Tile> tiles = grid.getTiles();
		while(tiles.hasNext()){ 
			Tile tile = tiles.next();
			turnOnTile(tile);
		}
	}
	
	public void drawNose() {
		int noseRow = centerRow;
		int noseColumn = centerColumn;
		turnOn(noseRow,noseColumn);
	}
	
	public void drawEyes(){

		int eyesRow = centerRow - (radius / 2);
		int leftEyeColumn = centerColumn - (radius / 2);
		int rightEyeColumn = centerColumn + (radius / 2);
		turnOn(eyesRow,leftEyeColumn);
		turnOn(eyesRow,rightEyeColumn);

	}
	
	public void populateGridGui() {
		Iterator<Tile> iter = grid.getTiles();
		while (iter.hasNext()) {
			Tile tile = iter.next();
			tile.setColor(Color.RED);
			tile.setBorder(BorderFactory.createLineBorder(Color.black));
			gui.add(tile);
		}
	}
	
	public void drawSmileyFace() {
		computeOuterCircleCenterAndRadius();

		drawNose();
		drawEyes();		
		// draw the face and its smile:

		// first: find the bounds of the inscribed square
		int rowBegin, columnBegin, rowEnd, columnEnd;
		if (gridIsWiderThanItIsTall()) {
			rowBegin = 0;
			columnBegin = centerColumn - radius;
		} else {
			rowBegin = centerRow - radius;
			columnBegin = 0;
		}
		rowEnd = rowBegin + (radius * 2) + 1;
		columnEnd = columnBegin + (radius * 2) + 1;

		// second: test every tile in the inscribed square
		int rowSquared, columnSquared, sum;
		int lo1 = (radius * radius) - radius + 1;
		int hi1 = (radius * radius) + radius;
		int innerRadius = (radius * 2) / 3;
		int lo2 = (innerRadius * innerRadius) - innerRadius + 1;
		int hi2 = (innerRadius * innerRadius) + innerRadius;
		for (int row = rowBegin; row < rowEnd; row++) {
			rowSquared = (row - centerRow) * (row - centerRow);
			for (int column = columnBegin; column < columnEnd; column++) {
				columnSquared = (column - centerColumn)
						* (column - centerColumn);
				sum = rowSquared + columnSquared;

				if (tileBelongsToFace(sum, lo1, hi1))
					turnOn(row,column);

				if (tileBelongsToSmile(sum, lo2, hi2, row, rowBegin, column,
						columnBegin, radius))
					turnOn(row, column);
			}
		} 
	}

	public boolean tileBelongsToFace(int sum, int lo1, int hi1) {
		boolean tileIsOnRimOfOuterCircle = ((sum >= lo1) && (sum <= hi1));

		return tileIsOnRimOfOuterCircle;
	}

	private boolean tileBelongsToSmile(int sum, int lo2, int hi2, int row,
			int rowBegin, int column, int columnBegin, int radius) {
		boolean tileIsOnRimOfInnerCircle = ((sum >= lo2) && (sum <= hi2));

		return tileIsOnRimOfInnerCircle
				&& tileIsInLowerQuadrant(row, rowBegin, column, columnBegin,
						radius);
	}

	private boolean tileIsInLowerQuadrant(int row, int rowBegin, int column,
			int columnBegin, int radius) {
		// to make the assignment: replace the following 5 lines with 'return
		// true;'
		int shiftedRow = row - rowBegin;
		int shiftedColumn = column - columnBegin;

		return ((shiftedRow >= shiftedColumn) && (shiftedRow + shiftedColumn >= 2 * radius));
	}
	
	private void computeOuterCircleCenterAndRadius() {
		// ensure that the face is balanced by only using odd numbers:
		// if rowCount is even, ignore the bottom row
		// if columnCount is even, ignore the rightmost column
		int numberOfRowsToDrawFaceWithin = rowCount;
		int numberOfColumnsToDrawFaceWithin = columnCount;
		if (isEven(rowCount))
			numberOfRowsToDrawFaceWithin = rowCount - 1;
		if (isEven(columnCount))
			numberOfColumnsToDrawFaceWithin = columnCount - 1;

		// find the inscribed circle's center and radius
		centerRow = numberOfRowsToDrawFaceWithin / 2;
		centerColumn = numberOfColumnsToDrawFaceWithin / 2;

		if (gridIsWiderThanItIsTall())
			radius = centerRow;
		else
			radius = centerColumn;
	}
	
	private void turnOn(int xPos, int yPos) {
		Tile tile = grid.getTile(xPos, yPos);
		turnOnTile(tile);
	}
	
	private void turnOnTile(Tile tile) {
		tile.setColor(randomColor());
		tile.setBorder(BorderFactory.createLineBorder(Color.black));
		tile.setVisible(true);
		tile.setOpaque(true);
	}
	
	private void turnOffTile(Tile tile) {
		tile.setVisible(false);	
	}

	public void turnOnAll() {
		Iterator<Tile> tiles = grid.getTiles();
	 while(tiles.hasNext()) {
			Tile tile = tiles.next();
			turnOnTile(tile);
		}
	}
	private boolean gridIsWiderThanItIsTall() {
		return (centerRow < centerColumn);
	}
	
	public JComponent getGui(){
		return gui;
	}
	//Util Functions
	
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

	private boolean isEven(int number) {
		return ((number % 2) == 0);
	}
	//Instance Variables

	private Random randomNumberSource = new Random();
	private final JComponent gui;
	
	private int rowCount, columnCount;
	private Grid grid;
	private int centerRow, centerColumn, radius;
	
	//Static Variables:
	
	public static final int COLOR_RANGE = 256;
}
