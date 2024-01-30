package org.Griffty.UserInterface.Graphic;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

public class JCustomButton extends JButton {
    Color normalColor = new Color(112, 128, 144); // Slate Gray
    Color pressedColor = new Color(70, 130, 180); // Steel Blue
    Color hoverColor = new Color(100, 149, 237); // Cornflower Blue
    Border normalBorder = new LineBorder(normalColor, 2, true);
    Border pressedBorder = new MatteBorder(2, 2, 2, 2, pressedColor);
    Border hoverBorder = new LineBorder(hoverColor, 2, true);

    JCustomButton(ImageIcon icon) {
        super(icon);
        setContentAreaFilled(false);
        setOpaque(true);
        setFocusPainted(false);
        setBackground(normalColor);
        setBorder(normalBorder);
        setForeground(Color.WHITE); // White text color
        setFont(new Font("Arial", Font.BOLD, 14));

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setBorder(hoverBorder);
                setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent evt) {
                setBorder(normalBorder);
                setBackground(normalColor);
            }

            public void mousePressed(MouseEvent e) {
                setBorder(pressedBorder);
                setBackground(pressedColor);
                setFont(new Font("Arial", Font.BOLD, 13)); // Slightly smaller font on press
            }

            public void mouseReleased(MouseEvent e) {
                setBorder(hoverBorder);
                setBackground(hoverColor);
                setFont(new Font("Arial", Font.BOLD, 14)); // Restore original font size
            }
        });
    }
}
