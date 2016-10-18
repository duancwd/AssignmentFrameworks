package edu.indiana.cs.c212.paint.gui.drawable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.indiana.cs.c212.paint.gui.Controls;

@SuppressWarnings("serial")
public class Easel extends JPanel {
    public static final Color BACKGROUND_COLOR = Controls.DEFAULT_FILL_COLOR;

    public static final Dimension PREFERRED_SIZE = new Dimension(400, 400);

    private Controls controls;
    private List<DrawableShape> savedShapes;
    private DrawableShape sketchShape;
    private Point sketchStart, sketchEnd;

    public Easel(Controls controls) {
        this.controls = controls;
        savedShapes = new ArrayList<DrawableShape>();
        sketchShape = null;

        addMouseListener(new ActWhenMouseChangesState());
        addMouseMotionListener(new ActWhenMouseIsDragged());

        setBackground(BACKGROUND_COLOR);
    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D tablet = (Graphics2D) graphics;
        tablet = setupToTryToSmoothJaggies(tablet);

        for (DrawableShape savedShape : savedShapes) {
            savedShape.draw(tablet);
        }

        if (isSketchingANewShape()) {
            sketchShape.draw(tablet);
        }
    }

    private Graphics2D setupToTryToSmoothJaggies(Graphics2D tablet) {
        tablet.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        tablet.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        return tablet;
    }

    private void paintWithBrushAt(Point point) {
        savedShapes.add(controls.getBrushShape(point));
    }

    private void beginSketchingNewShapeAt(Point point) {
        sketchStart = point;
        sketchEnd = point;
        sketchShape = controls.getShape(sketchStart, sketchEnd);
    }

    private void continueSketchingNewShapeAt(Point point) {
        sketchEnd = point;
        sketchShape.resetBounds(sketchStart, sketchEnd);
    }

    private void endSketchingNewShapeAt(Point point) {
        sketchEnd = point;
        sketchShape.resetBounds(sketchStart, sketchEnd);
        savedShapes.add(sketchShape);
        sketchShape = null;
    }

    private boolean isSketchingANewShape() {
        return (sketchShape != null);
    }

    private class ActWhenMouseChangesState extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            Point mousePosition = mouseEvent.getPoint();
            if (controls.isFreehand()) {
                paintWithBrushAt(mousePosition);
            } else {
                beginSketchingNewShapeAt(mousePosition);
            }
            repaint();
        }

        public void mouseReleased(MouseEvent mouseEvent) {
            Point mousePosition = mouseEvent.getPoint();
            if (controls.isFreehand()) {
                paintWithBrushAt(mousePosition);
            } else {
                endSketchingNewShapeAt(mousePosition);
            }
            repaint();
        }
    }

    private class ActWhenMouseIsDragged extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent mouseEvent) {
            Point mousePosition = mouseEvent.getPoint();
            if (controls.isFreehand()) {
                paintWithBrushAt(mousePosition);
            } else {
                continueSketchingNewShapeAt(mousePosition);
            }
            repaint();
        }
    }
}
