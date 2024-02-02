package org.Griffty.UserInterface.Graphic;

import org.Griffty.AI.DifficultyDialog;
import org.Griffty.Listeners.BackButtonPressedListener;
import org.Griffty.UserInterface.IUserInterface;
import org.Griffty.enums.InputErrorReason;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConnectFourGUI extends JFrame implements IUserInterface, BackButtonPressedListener {
    private static final String hintMessage = "The purpose of this game is to get four tokens of your color in a row, either horizontally, vertically or diagonally.\nEvery player makes turn by putting token in one of the columns.\nToken will fall to the bottom of the column.\nIf the column is full, you cannot put token there.\nTo put token in column, simple press on the column.";
    private final Dimension size = new Dimension(800, 800);
    private GamePanel gamePanel;
    private int onlineMode;
    private JPanel mainPanel;
    public ConnectFourGUI() {
        initGUI();
        setTitle("Ultimate Game Hub");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        TitleLabel topPanel = new TitleLabel("Connect Four", hintMessage);
        topPanel.addBackButtonPressedListener(this);
        mainPanel.add(topPanel);
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(size);
        mainPanel.add(gamePanel);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        mainPanel.add(bottomPanel);
    }


    @Override
    public int getGameInput() {
        CompletableFuture<Integer> input = new CompletableFuture<>();
        gamePanel.getNextInput(input);
        int col = input.join();
        gamePanel.clearInput();
        return col;
    }

    @Override
    public void updateUI(int[][] cells, int currentTurn) {
        gamePanel.updateUI(cells, currentTurn);
    }

    @Override
    public void updateBoard(int[][] cells) {
        gamePanel.updateBoard(cells);
    }

    @Override
    public boolean waitForConfirmation(String message) {
        int response = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void wrongInput(InputErrorReason reason) { //todo: replace with static in interface
        String message;
        switch (reason){
            case COL_OUT_OF_BOUNDS -> message = "You can only put token in columns from 1 to 7";
            case COLUMN_FULL -> message = "This column is already full";
            case NOT_A_NUMBER -> message = "You can input only numbers";
            case NOT_A_CONFIRMATION -> message = "You can only input y or n";
            case WRONG_SERVER_CREDENTIALS -> message = "Wrong server credentials. Address should look like 123.456.78.90 or similar. Try again";
            case DIF_OUT_OF_BOUNDS -> message = "You can only input numbers from 1 to 4";
            default ->
                    message = "Wrong input";
        }
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void announceWinner(int victoryStatus) {
        String message = IUserInterface.getWinMessage(victoryStatus, onlineMode);
        JOptionPane.showMessageDialog(this, message, "Game over", JOptionPane.INFORMATION_MESSAGE);
    }

    private ConnectionInfoDialogue connectionInfoDialogue;
    @Override
    public void showConnectionInfo(List<String> ip, int port) {
        connectionInfoDialogue = new ConnectionInfoDialogue(this, ip);
        new Thread(() -> connectionInfoDialogue.setVisible(true)).start();
    }
    public void closeConnectionInfoDialogue(){
        connectionInfoDialogue.dispose();
    }

    @Override
    public String serverAddress() {
        ConnectionDialogue dialogue = new ConnectionDialogue(this);
        while (dialogue.isShowing()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return dialogue.getServerAddress();
    }

    @Override
    public void setOnlineMode(int mode) {
        onlineMode = mode;
        gamePanel.setOnlineMode(mode);
        closeConnectionInfoDialogue();
    }

    @Override
    public void userDisconnected() {
        JOptionPane.showMessageDialog(this, "Your opponent has disconnected", "Game over", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void serverNotFound() {
        JOptionPane.showMessageDialog(this, "Cannot find server on this address. Try again with different one", "Game over", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public int getDifficulty() {
        CompletableFuture<Integer> difficulty = new CompletableFuture<>();
        new DifficultyDialog(this, difficulty);
        return difficulty.join();
    }

    @Override
    public void backButtonPressed() {
        new StatisticsDialog();
    }
}