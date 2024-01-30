package org.Griffty.UserInterface.Graphic;


import org.Griffty.Listeners.BackButtonPressedListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.black;
import static java.awt.Color.darkGray;

public class TitleLabel extends JPanel {
    List<BackButtonPressedListener> backButtonPressedListeners = new ArrayList<>();
    public void addBackButtonPressedListener(BackButtonPressedListener listener){
        if (backButtonPressedListeners.contains(listener)){
            throw new IllegalArgumentException("Listener already added");
        }
        backButtonPressedListeners.add(listener);
    }
    public TitleLabel(String title, String hint) {
        // Set GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row

        BufferedImage leaveImage = GetImage("leave.png");
        BufferedImage hintImage = GetImage("hint.png");
        // Create backButton
        assert leaveImage != null;
        assert hintImage != null;
        JButton leaveButton = new JCustomButton(new ImageIcon(leaveImage));
        leaveButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        gbc.weightx = 0.1; // Weight determines how to distribute space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill space horizontally
        add(leaveButton, gbc);

        // Create titleLabel
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        gbc.gridx = 1; // Second column
        gbc.weightx = 1.0; // Give more space to the label
        add(titleLabel, gbc);

        // Create hintButton

        JButton hintButton = new JCustomButton(new ImageIcon(hintImage));
        hintButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        gbc.gridx = 2; // Third column
        gbc.weightx = 0.1; // Same as first button
        add(hintButton, gbc);

        //action listeners
        leaveButton.addActionListener(e -> OnBackButtonPressed());
        hintButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.setTitle("Hint");
            dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            String[] split = hint.split("\n");
            for (String s : split) {
                JLabel label = new JLabel(s);
                label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
                label.setForeground(black);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                dialog.add(label);
            }
            dialog.pack();
            dialog.setVisible(true);
        });
    }

    private void OnBackButtonPressed() {
        for (BackButtonPressedListener listener : backButtonPressedListeners) {
            listener.backButtonPressed();
        }
    }

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
