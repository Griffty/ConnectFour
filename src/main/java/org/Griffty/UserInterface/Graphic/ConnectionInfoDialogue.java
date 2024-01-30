package org.Griffty.UserInterface.Graphic;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConnectionInfoDialogue extends JDialog{
    ConnectionInfoDialogue(ConnectFourGUI parent, List<String> ip) {
        super(parent, "Connection info", true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(this);
        setResizable(false);
        // Add the message to the dialog
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        add(messagePanel, BorderLayout.CENTER);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        JLabel infoLabel = new JLabel("To connect use one of these addresses:");
        infoLabel.setFont(font);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messagePanel.add(infoLabel);

        for (String address : ip) {
            JLabel addressLabel = new JLabel(address);
            addressLabel.setFont(font);
            addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            messagePanel.add(addressLabel);
        }

        JButton exitButton = new JButton("Exit");
        add(exitButton, BorderLayout.SOUTH);
        exitButton.addActionListener(e -> System.exit(0));
        pack();
    }
}
