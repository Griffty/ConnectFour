package org.Griffty.UserInterface.Graphic;


import org.Griffty.Listeners.BackButtonPressedListener;
import org.Griffty.Util.Dialogs.DialogFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a custom JPanel that displays a title and hint for a game.
 * It also includes a back button and a hint button.
 */
public class TitleLabel extends JPanel {
    List<BackButtonPressedListener> backButtonPressedListeners = new ArrayList<>();

    /**
     * This method adds a BackButtonPressedListener to the list of listeners.
     * @param listener the listener to add.
     * @throws IllegalArgumentException if the listener has already been added.
     */
    public void addBackButtonPressedListener(BackButtonPressedListener listener){
        if (backButtonPressedListeners.contains(listener)){
            throw new IllegalArgumentException("Listener already added");
        }
        backButtonPressedListeners.add(listener);
    }

    /**
     * The constructor for TitleLabel.
     * It sets up the layout, creates the buttons and label, and adds action listeners to the buttons.
     * @param title the title to display.
     * @param hint the hint to display when the hint button is pressed.
     */
    public TitleLabel(String title, String hint) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        BufferedImage leaveImage = GetImage("stats.png");
        BufferedImage hintImage = GetImage("hint.png");
        assert leaveImage != null;
        assert hintImage != null;
        JButton leaveButton = new JCustomButton(new ImageIcon(leaveImage));
        leaveButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(leaveButton, gbc);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(titleLabel, gbc);

        JButton hintButton = new JCustomButton(new ImageIcon(hintImage));
        hintButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        add(hintButton, gbc);

        //action listeners
        leaveButton.addActionListener(e -> OnBackButtonPressed());
        hintButton.addActionListener(e -> DialogFactory.getInfoBuilder()
                .setTitle("Hint")
                .setInfo(hint)
                .setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18))
                .setSplitOnNewLine(true)
                .setUseOptimalSize(true)
                .build());
    }

    /**
     * This method is called when the back button is pressed.
     * It notifies all BackButtonPressedListeners.
     */
    private void OnBackButtonPressed() {
        for (BackButtonPressedListener listener : backButtonPressedListeners) {
            listener.backButtonPressed();
        }
    }

    /**
     * This method loads an image from the resources.
     * @param imageName the name of the image file.
     * @return the loaded image, or null if the image cannot be loaded.
     */
    private BufferedImage GetImage(String imageName) {
        try {
            InputStream imageStream = getClass().getResourceAsStream("/" + imageName);
            assert imageStream != null;
            return ImageIO.read(imageStream);
        } catch (Exception e) {
            System.out.println("Error loading image: " + imageName);
            return null;
        }
    }
}
