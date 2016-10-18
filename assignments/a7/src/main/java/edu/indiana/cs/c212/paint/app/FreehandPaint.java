package edu.indiana.cs.c212.paint.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import edu.indiana.cs.c212.paint.gui.Controls;
import edu.indiana.cs.c212.paint.gui.drawable.Easel;

/**
This class starts everything up for a simple paint tool. It creates
a panel of controls for the user to select paint style (freehand,
or any one of various shapes), the width of the pen to be used, the
color of the pen, the opacity of the ink, and the fill color (if a
shape is selected). Then it creates an easel, which needs a reference
to the controls. Then it puts both inside a holder panel, and adds
that to its own window.
 */
@SuppressWarnings("serial")
public class FreehandPaint extends JFrame {
    public static final Color BORDER_COLOR = Controls.BORDER_COLOR;
    public static final int THIN_BORDER  = 1; //pixels
    public static final int THICK_BORDER = 4; //pixels
    public static final int DEFAULT_SIZE = 500;

    private Controls controls;
    private JPanel controlsPanel;
    private JPanel easelPanel;
    private JPanel holderPanel;

    public static void main(String[] args){
        new FreehandPaint();
    }

    public FreehandPaint(){

        controlsPanel = makeControlsPanel();
        easelPanel = makeEaselPanel();
        holderPanel = makeHolderPanel(controlsPanel, easelPanel);
        add(holderPanel);

        setSize(new Dimension(DEFAULT_SIZE,DEFAULT_SIZE));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel makeControlsPanel()
    {
        controls = new Controls();
        JPanel panel = setupBottomTitledPanel("Palette", THIN_BORDER);
        panel.add(controls);

        return panel;
    }

    private JPanel makeEaselPanel()
    {
        Easel easel = new Easel(controls);
        JPanel panel = setupBottomTitledPanel("Easel", THICK_BORDER);
        panel.setLayout(new BorderLayout());
        panel.add(easel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel makeHolderPanel(JPanel controlsPanel, JPanel easelPanel)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(controlsPanel, BorderLayout.EAST);
        panel.add(easelPanel, BorderLayout.CENTER);

        Border border =
                BorderFactory.createLineBorder(BORDER_COLOR, THIN_BORDER);
        panel.setBorder(border);

        return panel;
    }

    private JPanel setupBottomTitledPanel(String panelTitle, int borderWidth)
    {
        JPanel panel = new JPanel();
        Border border =
                BorderFactory.createLineBorder(BORDER_COLOR, borderWidth);
        TitledBorder title =
                BorderFactory.createTitledBorder(border, panelTitle);
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.BELOW_BOTTOM);
        panel.setBorder(title);

        return panel;
    }
}
