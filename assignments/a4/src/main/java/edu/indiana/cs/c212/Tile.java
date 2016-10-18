package edu.indiana.cs.c212;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

public class Tile {
	private static final int TILE_SIZE = Grid.TILE_SIZE;
	private static final int INNER_SQUARE_SIZE = Grid.TILE_INNER_SQUARE_SIZE;
	private static final int OUTLINE_THICKNESS = Grid.TILE_OUTLINE_THICKNESS;
	private static final Stroke OUTLINE_BRUSH = new BasicStroke(
			OUTLINE_THICKNESS);

	private Rectangle outlineSquare, innerSquare;
	private Color outlineColor, innerColor;

	public Tile(int xOrigin, int yOrigin) {
		outlineSquare = new Rectangle(xOrigin, yOrigin, TILE_SIZE
				- OUTLINE_THICKNESS, TILE_SIZE - OUTLINE_THICKNESS);

		innerSquare = new Rectangle(xOrigin, yOrigin, INNER_SQUARE_SIZE
				+ OUTLINE_THICKNESS, INNER_SQUARE_SIZE + OUTLINE_THICKNESS);
	}

	public void setOutlineColor(Color color) {
		outlineColor = color;
	}

	public void setInnerColor(Color color) {
		innerColor = color;
	}

	public void draw(Graphics2D tablet) {
		tablet.setColor(innerColor);
		tablet.fill(innerSquare);

		Stroke originalBrush = tablet.getStroke();

		tablet.setStroke(OUTLINE_BRUSH);
		tablet.setColor(outlineColor);
		tablet.draw(outlineSquare);

		tablet.setStroke(originalBrush);
	}
}
