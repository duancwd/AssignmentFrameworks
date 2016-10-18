package edu.indiana.cs.c212.paint.gui.panel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.indiana.cs.c212.paint.gui.Controls;

@SuppressWarnings("serial")
public class PenWidthPanel extends JPanel {
    public static final Color BORDER_COLOR = Controls.BORDER_COLOR;
    public static final Dimension SWATCH_SIZE = Controls.SWATCH_SIZE;

    public static final int MIN_PEN_WIDTH = 1; // pixels
    public static final int MAX_PEN_WIDTH = 50; // pixels

    private Controls controls;
    private int penWidth;

    public PenWidthPanel(Controls controls) {
        this.controls = controls;
        penWidth = MIN_PEN_WIDTH;

        JPanel penWidthBox = makeSwatchedLabeledSliderBox("Pen Width");
        add(penWidthBox);
    }

    public int getPenWidth() {
        return penWidth;
    }

    private JPanel makeSwatchedLabeledSliderBox(String string) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(border, string);
        title.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(title);

        JPanel swatch = makeSwatch();
        JSlider slider = makeSlider();
        JLabel label = makeLabel();
        slider.addChangeListener(new WatchSlider(slider, label));

        panel.add(swatch);
        panel.add(slider);
        panel.add(label);

        return panel;
    }

    private JPanel makeSwatch() {
        PenWidthSwatch penWidthSwatch = new PenWidthSwatch();
        penWidthSwatch.setPreferredSize(SWATCH_SIZE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(penWidthSwatch, BorderLayout.CENTER);
        Border border = BorderFactory.createLineBorder(BORDER_COLOR, 1);
        panel.setBorder(border);

        JPanel swatch = new JPanel();
        swatch.add(panel);

        return swatch;
    }

    private JSlider makeSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        slider.setMinimum(MIN_PEN_WIDTH);
        slider.setMaximum(MAX_PEN_WIDTH);
        slider.setValue(MIN_PEN_WIDTH);

        return slider;
    }

    private JLabel makeLabel() {
        JLabel label = new JLabel("" + penWidth);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    private class PenWidthSwatch extends JPanel {
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D tablet = (Graphics2D) graphics;

            int x = (getWidth() - penWidth) / 2;
            int y = (getHeight() - penWidth) / 2;

            tablet.setColor(BORDER_COLOR);
            tablet.drawRect(x - 1, y - 1, penWidth + 1, penWidth + 1);

            float opacity = controls.getOpacity();
            tablet.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, opacity));

            tablet.setColor(controls.getPenColor());
            tablet.fillRect(x, y, penWidth, penWidth);
        }
    }

    private class WatchSlider implements ChangeListener {
        private JSlider slider;
        private JLabel label;

        public WatchSlider(JSlider slider, JLabel label) {
            this.slider = slider;
            this.label = label;
        }

        public void stateChanged(ChangeEvent changeEvent) {
            penWidth = slider.getValue();
            label.setText("" + penWidth);
            controls.repaint();
        }
    }
}
