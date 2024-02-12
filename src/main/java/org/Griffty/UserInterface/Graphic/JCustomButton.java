package org.Griffty.UserInterface.Graphic;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This class extends JButton to create a custom button with specific colors and behaviors.
 * The button changes color and border when hovered over, pressed, and released.
 */
public class JCustomButton extends JButton {
    // Define colors for different button states
    Color normalColor = new Color(112, 128, 144); // Slate Gray
    Color pressedColor = new Color(70, 130, 180); // Steel Blue
    Color hoverColor = new Color(100, 149, 237); // Cornflower Blue

    // Define borders for different button states
    Border normalBorder = new LineBorder(normalColor, 2, true);
    Border pressedBorder = new MatteBorder(2, 2, 2, 2, pressedColor);
    Border hoverBorder = new LineBorder(hoverColor, 2, true);

    /**
     * Constructor for JCustomButton.
     * Initializes the button with an icon and sets up its appearance and behavior.
     * @param icon the icon to display on the button.
     */
    JCustomButton(ImageIcon icon) {
        super(icon);
        setContentAreaFilled(false);
        setOpaque(true);
        setFocusPainted(false);
        setBackground(normalColor);
        setBorder(normalBorder);
        setForeground(Color.WHITE); // White text color
        setFont(new Font("Arial", Font.BOLD, 14));

        // Add a mouse listener to change the button's appearance based on mouse events
        addMouseListener(new java.awt.event.MouseAdapter() {
            /**
             * Changes the button's appearance when the mouse enters its area.
             * @param evt the mouse event.
             */
            public void mouseEntered(MouseEvent evt) {
                setBorder(hoverBorder);
                setBackground(hoverColor);
            }

            /**
             * Restores the button's normal appearance when the mouse exits its area.
             * @param evt the mouse event.
             */
            public void mouseExited(MouseEvent evt) {
                setBorder(normalBorder);
                setBackground(normalColor);
            }

            /**
             * Changes the button's appearance when it is pressed.
             * @param e the mouse event.
             */
            public void mousePressed(MouseEvent e) {
                setBorder(pressedBorder);
                setBackground(pressedColor);
                setFont(new Font("Arial", Font.BOLD, 13)); // Slightly smaller font on press
            }

            /**
             * Restores the button's hover appearance when it is released.
             * @param e the mouse event.
             */
            public void mouseReleased(MouseEvent e) {
                setBorder(hoverBorder);
                setBackground(hoverColor);
                setFont(new Font("Arial", Font.BOLD, 14)); // Restore original font size
            }
        });
    }
}