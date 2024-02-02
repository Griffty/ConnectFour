package org.Griffty.AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;

public class DifficultyDialog extends JDialog {
    private final CompletableFuture<Integer> difficulty;
    public DifficultyDialog(Frame parent, CompletableFuture<Integer> difficulty) {
        super(parent, true);
        this.difficulty = difficulty;
        InitGUI();
        setTitle("Connect Four");
        setSize(400, 120);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void InitGUI() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        JLabel topPanel = new JLabel("Choose Difficulty");
        topPanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(topPanel);
        JSeparator line = new JSeparator(SwingConstants.HORIZONTAL);
        line.setMaximumSize(new Dimension(200, 12));
        line.setForeground(Color.BLACK);
        line.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(line);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        mainPanel.add(bottomPanel);
        JButton soloButton = new JButton("Easy");
        soloButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        soloButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        soloButton.addActionListener(e -> {
            difficulty.complete(0);
            dispose();
        });
        bottomPanel.add(soloButton);
        JButton hostButton = new JButton("Medium");
        hostButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        hostButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        hostButton.addActionListener(e -> {
            difficulty.complete(1);
            dispose();
        });
        bottomPanel.add(hostButton);
        JButton joinButton = new JButton("Hard");
        joinButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        joinButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        joinButton.addActionListener(e -> {
            difficulty.complete(2);
            dispose();
        });
        bottomPanel.add(joinButton);
        JButton botButton = new JButton("Demonic");
        botButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        botButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        botButton.addActionListener(e -> {
            difficulty.complete(3);
            dispose();
        });
        bottomPanel.add(botButton);
    }
}

