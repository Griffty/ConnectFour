package org.Griffty.UserInterface.Graphic;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;

import static org.Griffty.enums.InputErrorReason.WRONG_SERVER_CREDENTIALS;

public class ConnectionDialogue extends JDialog {
    private JButton buttonOK;
    private JTextField serverAddress;
    private final ConnectFourGUI parent;
    public ConnectionDialogue(ConnectFourGUI parent) {
        super(parent, "Connect to server", true);
        this.parent = parent;
        getRootPane().setDefaultButton(buttonOK);
        initGUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter server address: ");
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);
        serverAddress = new JTextField(15);
        serverAddress.setAlignmentX(CENTER_ALIGNMENT);
        add(serverAddress);
        buttonOK = new JButton("OK");
        buttonOK.setAlignmentX(CENTER_ALIGNMENT);
        add(buttonOK);
        buttonOK.addActionListener(e ->{
            if (!getServerAddress().matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")){
                parent.wrongInput(WRONG_SERVER_CREDENTIALS);
                return;
            }
            dispose();
        });
    }

    public String getServerAddress() {
        return serverAddress.getText();
    }
}
