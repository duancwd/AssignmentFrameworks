package edu.indiana.cs.c212.paint.gui.drawable.impl;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.indiana.cs.c212.paint.gui.drawable.DrawableShape;

public class DrawableRectangle extends DrawableShape {
    private Rectangle2D rectangle;
    private double width, height;

    public DrawableRectangle() {
        super();

        width = 0;
        height = 0;
        rectangle = null;
    }

    public DrawableRectangle(Point2D from, Point2D to) {
        resetBounds(from, to);
    }

    public final void resetBounds(Point2D from, Point2D to) {
        super.resetBounds(from, to);

        double x = getUpperLeft().getX();
        double y = getUpperLeft().getY();
        width = getLowerRight().getX() - getUpperLeft().getX();
        height = getLowerRight().getY() - getUpperLeft().getY();

        rectangle = new Rectangle2D.Double(x, y, width, height);
    }

    public void draw(Graphics2D tablet) {
        tablet = setOpacity(tablet);
        tablet.setStroke(getStroke());
        tablet.setColor(getFillColor());
        tablet.fill(rectangle);
        tablet.setColor(getPenColor());
        tablet.draw(rectangle);
    }

    public String toString() {
        return super.toString() + "width = " + width + "\n" + "height = "
                + height + "\n";
    }
}
