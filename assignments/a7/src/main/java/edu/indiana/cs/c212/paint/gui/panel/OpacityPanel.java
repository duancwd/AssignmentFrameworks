package edu.indiana.cs.c212.paint.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.indiana.cs.c212.paint.gui.Controls;

import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class OpacityPanel extends JPanel {
    public static final Color BORDER_COLOR = Controls.BORDER_COLOR;
    public static final Dimension SWATCH_SIZE = Controls.SWATCH_SIZE;

    public static final int MIN_OPACITY_VALUE = 0; // out of 100
    public static final int MAX_OPACITY_VALUE = 100; // out of 100
    public static final int START_OPACITY_VALUE = 100; // out of 100
    public static final float DEFAULT_OPACITY = 1.0f; // completely opaque

    private Controls controls;
    private float opacity;
    private DecimalFormat twoDigitAccuracy = new DecimalFormat("0.00");

    public OpacityPanel(Controls controls) {
        this.controls = controls;
        opacity = DEFAULT_OPACITY;

        JPanel opacityBox = makeSwatchedLabeledSliderBox("Color Opacity");
        add(opacityBox);
    }

    public float getOpacity() {
        return opacity;
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
        OpacitySwatch opacitySwatch = new OpacitySwatch();
        opacitySwatch.setPreferredSize(SWATCH_SIZE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(opacitySwatch, BorderLayout.CENTER);
        Border border = BorderFactory.createLineBorder(BORDER_COLOR, 1);
        panel.setBorder(border);

        JPanel swatch = new JPanel();
        swatch.add(panel);

        return swatch;
    }

    private JSlider makeSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        slider.setMinimum(MIN_OPACITY_VALUE);
        slider.setMaximum(MAX_OPACITY_VALUE);
        slider.setValue(START_OPACITY_VALUE);

        return slider;
    }

    private JLabel makeLabel() {
        JLabel label = new JLabel("" + twoDigitAccuracy.format(opacity));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    private class OpacitySwatch extends JPanel {
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D tablet = (Graphics2D) graphics;

            tablet.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, opacity));

            tablet.setColor(controls.getPenColor());
            tablet.fillRect(0, 0, getWidth(), getHeight());
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
            opacity = (float) (slider.getValue() * 0.01);
            label.setText("" + twoDigitAccuracy.format(opacity));
            controls.repaint();
        }
    }
}
