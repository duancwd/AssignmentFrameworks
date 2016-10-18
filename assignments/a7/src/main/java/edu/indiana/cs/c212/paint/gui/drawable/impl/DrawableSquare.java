package edu.indiana.cs.c212.paint.gui.drawable.impl;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.indiana.cs.c212.paint.gui.drawable.DrawableShape;

public class DrawableSquare extends DrawableShape {
    private Rectangle2D square;
    private double sideLength;

    public DrawableSquare() {
        super();

        sideLength = 0;
        square = null;
    }

    public DrawableSquare(Point2D from, Point2D to) {
        resetBounds(from, to);
    }

    public DrawableSquare(Point2D center, double sideLength) {
        Point2D upperLeft = new Point2D.Double(
                (center.getX() - (sideLength / 2)),
                (center.getY() - (sideLength / 2)));
        Point2D lowerRight = new Point2D.Double(
                (center.getX() + (sideLength / 2)),
                (center.getY() + (sideLength / 2)));
        resetBounds(upperLeft, lowerRight);
    }

    public final void resetBounds(Point2D from, Point2D to) {
        Point2D newTo = findAPointMaintainingSquareness(from, to);

        super.resetBounds(from, newTo);

        double x = getUpperLeft().getX();
        double y = getUpperLeft().getY();

        square = new Rectangle2D.Double(x, y, sideLength, sideLength);
    }

    private Point2D findAPointMaintainingSquareness(Point2D from, Point2D to) {
        double width = to.getX() - from.getX();
        double height = to.getY() - from.getY();
        double min = Math.min(width, height);
        sideLength = Math.abs(min);

        double newToX = from.getX() + min;
        double newToY = from.getY() + min;
        if (isAbove(to, from) && !isBefore(to, from))
            newToX = from.getX() + sideLength;
        if (!isAbove(to, from) && isBefore(to, from))
            newToY = from.getY() + sideLength;

        return new Point2D.Double(newToX, newToY);
    }

    private boolean isAbove(Point2D first, Point2D second) {
        return (first.getY() < second.getY());
    }

    private boolean isBefore(Point2D first, Point2D second) {
        return (first.getX() < second.getX());
    }

    public void draw(Graphics2D tablet) {
        tablet = setOpacity(tablet);
        tablet.setStroke(getStroke());
        tablet.setColor(getFillColor());
        tablet.fill(square);
        tablet.setColor(getPenColor());
        tablet.draw(square);
    }

    public String toString() {
        return super.toString() + "sideLength = " + sideLength + "\n";
    }
}
