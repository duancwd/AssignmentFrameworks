package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author <YOUR NAME HERE>
 * @author <YOUR PARTNER'S NAME HERE
 *
 */
@SuppressWarnings("serial")
public class Album extends JPanel {
    public static final Color COLOR = Fridge.COLOR;
    public static final Color BORDER_COLOR = Color.WHITE;
    public static final int ALBUM_SIZE = 3; // number of snapshots
    public static final String SOUND_FILENAME = "sounds/shutter-click.au";

    private Fridge fridge;
    private JPanel buttonPanel;
    private JLabel[] snapshotHolders;
    private Border[] borders;

    /**
     * Constructs a new Album object.
     * @param fridge the fridge this album will be attached to
     */
    public Album(Fridge fridge) {
        this.fridge = fridge;

        JPanel snapshotsPanel = setupSnapshotsPanel();
        setupButtonPanel();

        this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        this.setLayout(new BorderLayout());
        this.add(snapshotsPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setBackground(COLOR);

    }

    /**
     * Sets up the JPanel to hold snapshots for this object
     * @return a JPanel to hold images
     */
    private JPanel setupSnapshotsPanel() {
        JPanel snapshotsPanel = new JPanel();
        snapshotsPanel.setLayout(new GridLayout(ALBUM_SIZE, 1));
        snapshotHolders = new JLabel[ALBUM_SIZE];
        borders = new Border[ALBUM_SIZE];
        for (int i = 0; i < ALBUM_SIZE; i++) {
            snapshotHolders[i] = new JLabel();
            borders[i] = BorderFactory.createLineBorder(BORDER_COLOR, 1);
            snapshotsPanel.add(snapshotHolders[i]);
        }
        snapshotsPanel.setBackground(COLOR);

        return snapshotsPanel;
    }

    /**
     * Sets up a JPanel to hold the button that takes
     * snapshots
     */
    private void setupButtonPanel() {
        JButton takeSnapshotButton = new JButton("take snapshot");
        takeSnapshotButton.addActionListener(new SnapshotButtonListener());
        takeSnapshotButton.setBackground(COLOR);

        buttonPanel = new JPanel();
        buttonPanel.add(takeSnapshotButton);
        buttonPanel.setBackground(COLOR);
    }

   
    private class SnapshotButtonListener implements ActionListener {
        private int snapshotIndex, lastSnapshotIndex;
        private int fridgeWidth, fridgeHeight;
        private int buttonPanelWidth, buttonPanelHeight;

        SnapshotButtonListener() {
            snapshotIndex = 0;
            lastSnapshotIndex = 0;
        }

        public void actionPerformed(ActionEvent actionEvent) {
            setupBounds();
            ImageIcon icon = takeSnapshotOfFridge();
            addSnapshot(icon);
        }

        private void setupBounds() {
            Rectangle fridgeBounds = fridge.getBounds();
            fridgeWidth = fridgeBounds.width;
            fridgeHeight = fridgeBounds.height;

            Rectangle buttonPanelBounds = buttonPanel.getBounds();
            buttonPanelWidth = buttonPanelBounds.width;
            buttonPanelHeight = buttonPanelBounds.height;
        }

        private ImageIcon takeSnapshotOfFridge() {
            BufferedImage image = createAnImageAsBigAsTheFridge();
            storePictureOfFridgeIn(image);
            Image smallerCopy = createASmallerCopyToFitInAlbum(image);
            ImageIcon icon = iconify(smallerCopy);
            return icon;
        }

        private BufferedImage createAnImageAsBigAsTheFridge() {
            return new BufferedImage(fridgeWidth, fridgeHeight,
                    BufferedImage.TYPE_INT_ARGB);
        }

        private void storePictureOfFridgeIn(BufferedImage image) {
            fridge.paint(image.getGraphics());
        }

        private Image createASmallerCopyToFitInAlbum(BufferedImage image) {
            int SNAPSHOT_BORDER_ADDITION_IN_PIXELS = 2;

            return image.getScaledInstance(buttonPanelWidth
                    - SNAPSHOT_BORDER_ADDITION_IN_PIXELS,
                    (fridgeHeight - buttonPanelHeight) / ALBUM_SIZE,
                    Image.SCALE_FAST);
        }

        private ImageIcon iconify(Image image) {
            return new ImageIcon(image);
        }

        private void addSnapshot(ImageIcon icon) {
            snapshotHolders[lastSnapshotIndex].setBorder(null);
            snapshotHolders[snapshotIndex].setBorder(borders[snapshotIndex]);
            snapshotHolders[snapshotIndex].setIcon(icon);
            lastSnapshotIndex = snapshotIndex;
            snapshotIndex = (snapshotIndex + 1) % ALBUM_SIZE;
        }
    }
}
