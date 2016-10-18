package edu.indiana.cs.c212.paint.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.indiana.cs.c212.paint.gui.drawable.DrawableShape;
import edu.indiana.cs.c212.paint.gui.panel.ColorPanel;
import edu.indiana.cs.c212.paint.gui.panel.OpacityPanel;
import edu.indiana.cs.c212.paint.gui.panel.PenWidthPanel;
import edu.indiana.cs.c212.paint.gui.panel.ShapePanel;

/**
 * This class aggregates all of the controller panels.
 * 
 * @author jrread
 * 
 */
@SuppressWarnings("serial")
public class Controls extends JPanel {
    public static final int MAGIC_NUMBER_OF_PIXELS = 220;
    // this is a hack! its used to force the Panel to be just wide enough
    // so that Swing won't shift its layout if 'More Controls' is clicked
    // would be nice to fix...

    public static final Color DEFAULT_PEN_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.WHITE;
    public static final Color BORDER_COLOR = DEFAULT_PEN_COLOR;

    public static final int SWATCH_INSET = 3; // pixels
    public static final int SWATCH_WIDTH = PenWidthPanel.MAX_PEN_WIDTH
            + SWATCH_INSET * 2;
    public static final Dimension SWATCH_SIZE = new Dimension(SWATCH_WIDTH,
            SWATCH_WIDTH);

    private ShapePanel shapePanel;
    private ColorPanel colorPanel;
    private PenWidthPanel penWidthPanel;
    private OpacityPanel opacityPanel;

    public Controls() {
        JPanel controlsBox = makeControlsBox();
        add(controlsBox);
    }

    public boolean isFreehand() {
        return shapePanel.isFreehand();
    }

    public DrawableShape getBrushShape(Point point) {
        DrawableShape shape = shapePanel.getBrushShape(point);

        shape = setShape(shape);

        return shape;
    }

    public DrawableShape getShape(Point from, Point to) {
        DrawableShape shape = shapePanel.getShape(from, to);

        shape = setShape(shape);

        return shape;
    }

    public DrawableShape setShape(DrawableShape shape) {
        shape.setPenColor(getPenColor());
        shape.setFillColor(getFillColor());
        shape.setPenWidth(getPenWidth());
        shape.setOpacity(getOpacity());

        return shape;
    }

    public Color getPenColor() {
        return colorPanel.getPenColor();
    }

    public Color getFillColor() {
        return colorPanel.getFillColor();
    }

    public int getPenWidth() {
        return penWidthPanel.getPenWidth();
    }

    public float getOpacity() {
        return opacityPanel.getOpacity();
    }

    private JPanel makeControlsBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        shapePanel = new ShapePanel(this);

        panel.add(shapePanel);
        JButton button = makeControlsButton();
        panel.add(button);

        JPanel holder = new JPanel();
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
        colorPanel = new ColorPanel(this);
        penWidthPanel = new PenWidthPanel(this);
        opacityPanel = new OpacityPanel(this);
        holder.add(colorPanel);
        holder.add(penWidthPanel);
        holder.add(opacityPanel);
        makeSubControlsAppear(false);

        int justWideEnough = MAGIC_NUMBER_OF_PIXELS; // a hack!
        holder.add(Box.createRigidArea(new Dimension(justWideEnough, 0)));

        panel.add(holder);

        return panel;
    }

    private JButton makeControlsButton() {
        JButton button = new JButton("More Controls...");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JButton button = (JButton) actionEvent.getSource();
                String buttonText = actionEvent.getActionCommand();
                if (buttonText.contains("More")) {
                    button.setText("Less Controls...");
                    makeSubControlsAppear(true);
                }
                if (buttonText.contains("Less")) {
                    button.setText("More Controls...");
                    makeSubControlsAppear(false);
                }
                validate();
            }
        });

        return button;
    }

    private void makeSubControlsAppear(boolean condition) {
        colorPanel.setVisible(condition);
        penWidthPanel.setVisible(condition);
        opacityPanel.setVisible(condition);
    }
}
