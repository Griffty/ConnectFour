package org.Griffty.UserInterface.Graphic;

import org.Griffty.Statistics.PlayerStatistics;
import org.Griffty.Statistics.StatisticsHandler;

import javax.swing.*;
import java.awt.*;

public class StatisticsDialog extends JDialog {
    private final PlayerStatistics stats;
    public StatisticsDialog() {
        stats = StatisticsHandler.getInstance().getCurrentStats();
        setTitle("Statistics");
        setPreferredSize(new Dimension(300, 180));
        setResizable(false);
        InitGUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void InitGUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel topLabel = new JLabel("Multiplayer Statistics");
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        topLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(topLabel);
        JSeparator line = new JSeparator(SwingConstants.HORIZONTAL);
        line.setMaximumSize(new Dimension(200, 5));
        line.setForeground(Color.BLACK);
        line.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(line);
        JLabel totalGamesLabel = new JLabel("Total Games: " + stats.getGamesPlayed());
        totalGamesLabel.setFont(font);
        totalGamesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(totalGamesLabel);
        JLabel winsLabel = new JLabel("Wins: " + stats.getGamesWon());
        winsLabel.setFont(font);
        winsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(winsLabel);
        JLabel lossesLabel = new JLabel("Losses: " + stats.getGamesLost());
        lossesLabel.setFont(font);
        lossesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lossesLabel);
        JLabel drawsLabel = new JLabel("Draws: " + stats.getGamesDrawn());
        drawsLabel.setFont(font);
        drawsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(drawsLabel);
        JLabel movesLabel = new JLabel("Moves made: " + stats.getMovesMade());
        movesLabel.setFont(font);
        movesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(movesLabel);
    }
}