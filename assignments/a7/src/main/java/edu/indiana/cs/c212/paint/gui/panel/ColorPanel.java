package edu.indiana.cs.c212.paint.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import edu.indiana.cs.c212.paint.gui.Controls;

@SuppressWarnings("serial")
/**
 * This class handles buttons and graphics for color selection.
 * @author jrread
 *
 */
public class ColorPanel extends JPanel {
    public static final Color BORDER_COLOR = Controls.BORDER_COLOR;

    private Controls controls;
    private Color penColor, fillColor;
    private Map<String, JLabel> map;

    public ColorPanel(Controls controls) {
        this.controls = controls;
        penColor = Controls.DEFAULT_PEN_COLOR;
        fillColor = Controls.DEFAULT_FILL_COLOR;

        JPanel colorBox = makeColorBox("Colors");
        add(colorBox);
    }

    /**
     * Returns the current color of the pen.
     * 
     * @return Color object representing the pen's current color.
     */
    public Color getPenColor() {
        return penColor;
    }

    /**
     * Returns the current fill color.
     * 
     * @return The Color object representing the current fill color.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Makes menu for color selection.
     * 
     * @param string
     *            The name of the color.
     * @return A JPanel with the color selection button.
     */
    private JPanel makeColorBox(String string) {
        JPanel colorBox = new JPanel();
        colorBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorBox.setLayout(new BoxLayout(colorBox, BoxLayout.Y_AXIS));

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(border, string);
        title.setTitleJustification(TitledBorder.CENTER);
        colorBox.setBorder(title);

        String EMPTY = "   ";
        border = BorderFactory.createLineBorder(BORDER_COLOR, 1);
        String[] buttonText = { "Pen Color", "Fill Color" };
        Color[] labelColor = { penColor, fillColor };
        map = new HashMap<String, JLabel>();
        WatchColorButtons watcher = new WatchColorButtons();

        for (int i = 0; i < buttonText.length; i++) {
            JButton button = new JButton(buttonText[i]);
            JLabel colorSwatch = new JLabel(EMPTY);
            button.addActionListener(watcher);
            colorSwatch.setBackground(labelColor[i]);
            colorSwatch.setOpaque(true);
            colorSwatch.setBorder(border);
            JPanel panel = new JPanel();
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(button);
            panel.add(colorSwatch);
            colorBox.add(panel);
            map.put(buttonText[i], colorSwatch);
        }

        return colorBox;
    }

    private class WatchColorButtons implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            Color color = JColorChooser.showDialog(null, null, BORDER_COLOR);
            String buttonText = actionEvent.getActionCommand();
            if (color != null) {
                if (buttonText.contains("Pen")) {
                    penColor = color;
                    (map.get(buttonText)).setBackground(penColor);
                }
                if (buttonText.contains("Fill")) {
                    fillColor = color;
                    (map.get(buttonText)).setBackground(fillColor);
                }
                controls.repaint();
            }
        }
    }
}
