package edu.indiana.cs.c212.paint.gui.drawable;

import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public abstract class DrawableShape {
    private Point2D upperLeft, lowerRight;
    private int penWidth;
    private Color penColor, fillColor;
    private float opacity;

    public DrawableShape() {
        upperLeft = null;
        lowerRight = null;
        penWidth = 0;
        penColor = null;
        fillColor = null;
        opacity = 0;
    }

    /**
     * Sets the penWidth
     * 
     * @param int representing the new penWidth.
     */
    public void setPenWidth(int penWidth) {
        this.penWidth = penWidth;
    }

    /**
     * Sets the pen Color
     * 
     * @param penColor
     *            a Color object representing the new penColor.
     */
    public void setPenColor(Color penColor) {
        this.penColor = penColor;
    }

    /**
     * Sets the fill color.
     * 
     * @param fillColor
     *            the Color object representing the
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Changes the opacity of the shape to the given value.
     * 
     * @param opacity
     *            a float representing the opacity
     */
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    /**
     * Draws the shape to the given tablet.
     * <p>
     * DO NOT IMPLEMENT THIS METHOD.
     * 
     * @param tablet
     *            the table to draw to.
     */
    public abstract void draw(Graphics2D tablet);

    /**
     * Resets the bounds of the shape from their current values to the passed
     * values.
     * 
     * @param from
     *            the new upper left point.
     * @param to
     *            the new lower right point.
     */
    protected void resetBounds(Point2D from, Point2D to) {
        assert ((from != null) && (to != null));

        double x, y;

        x = Math.min(from.getX(), to.getX());
        y = Math.min(from.getY(), to.getY());
        upperLeft = new Point2D.Double(x, y);

        x = Math.max(from.getX(), to.getX());
        y = Math.max(from.getY(), to.getY());
        lowerRight = new Point2D.Double(x, y);
    }

    /**
     * The upper left point
     * 
     * @return
     */
    protected Point2D getUpperLeft() {
        return upperLeft;
    }

    /**
     * Gets the lower right point of the shapes inscribing rectangle
     * 
     * @return the Point representing the lower right bound of the rectangle
     */
    protected Point2D getLowerRight() {
        return lowerRight;
    }

    /**
     * Returns a BasicStroke object using the current pen specifications.
     * <p>
     * Will use the current pen settings make a BasicStroke that will be used to
     * add the pen lines for the shape being drawn. See the BasicStroke
     * documentation for further details
     * 
     * @return
     */
    protected BasicStroke getStroke() {
        return new BasicStroke(penWidth);
    }

    /**
     * Sets the opacity of the given tablet, to the opacity of the shape.
     * <p>
     * The desired behavior for this paint program is for all shapes to be
     * overlayed atop all the other shapes that were placed on the easel before
     * it. This means setting the composite setting for the tablet.
     * <p>
     * 
     * @see <a href
     *      ="http://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html">Graphics2D</a>
     *      <a href =
     *      "http://docs.oracle.com/javase/7/docs/api/java/awt/AlphaComposite.html"
     *      >AlphaComposite</a>
     * 
     * @param tablet
     *            The tablet whose opacity is going to be set.
     * @return the tablet after it's settings have been changed
     */
    protected Graphics2D setOpacity(Graphics2D tablet) {
        tablet.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                opacity));

        return tablet;
    }

    /**
     * Returns the current color of the pen for drawing shapes.
     * 
     * @return the Color
     */
    protected Color getPenColor() {
        return penColor;
    }

    /**
     * Returns the current color being used to fill the shape when drawing.
     * 
     * @return the color object being used to fill the shape during drawing
     */
    protected Color getFillColor() {
        return fillColor;
    }

    /**
     * Gives a string version of the object that can be used in print
     * statements.
     * 
     * <p>
     * This method makes a string of details about the class which will print out
     * exactly like the following:
     * </p>
     * <p>
     * edu.indiana.cs.c212.paint.gui.drawable.impl.DrawableCircle<br>
     * Upper Left = (144.5857864376269,334.5857864376269)<br>
     * Lower Right = (147.4142135623731,337.4142135623731)<br>
     * Pen width: 0<br>
     * PenColor: null<br>
     * FillColor: null<br>
     * center = (146.0,336.0) //This is from the circle part and is not made in this class<br>
     * radius = 1.4142135623730951 //Same here.
     * </p>
     * <p>
     * This is an example of the function being called when making a circle so the last two parts are
     * defined in DrawableCircle's toString().<br>    
     * Each part of the String specifies the property that you would expect. Coordinates are typical
     * Cartesian (x,y).<br>
     * 
     * @return A string format of the given shape.
     */
    @Override
    public String toString() {
        String string, pen, bounds;

        pen = String.format("Pen width: %d\n"+
                "PenColor: %s\n"+
                "FillColor: %s\n", penWidth, penColor, fillColor);
        if (upperLeft != null) {
            bounds =String.format("Upper Left = (%s,%s)\n"+
                    "Lower Right = (%s,%s)\n",
                    upperLeft.getX(),upperLeft.getY(),lowerRight.getX(),lowerRight.getY());
            string = bounds + pen;
        } else {
            string = "No bounds set " + pen;
        }

        return getClass().getName() + "\n" + string;
    }
}
