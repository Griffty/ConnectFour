package org.Griffty.UserInterface.Graphic;

import org.Griffty.Listeners.BackButtonPressedListener;
import org.Griffty.UserInterface.IUserInterface;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.Util.Dialogs.*;
import org.Griffty.Statistics.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class implements the IUserInterface for a GUI-based Connect Four game.
 * It handles user input and output through a graphical user interface.
 */
public class ConnectFourGUI extends JFrame implements IUserInterface, BackButtonPressedListener {
    private static final String hintMessage = "The purpose of this game is to get four tokens of your color in a row, either horizontally, vertically or diagonally.\nEvery player makes turn by putting token in one of the columns.\nToken will fall to the bottom of the column.\nIf the column is full, you cannot put token there.\nTo put token in column, simple press on the column.";
    private final Dimension size = new Dimension(800, 800);
    private GamePanel gamePanel;
    private int onlineMode;

    /**
     * The constructor initializes the GUI and sets the title, size, and other properties of the window.
     */
    public ConnectFourGUI() {
        initGUI();
        setTitle("Ultimate Game Hub");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * This method initializes the GUI components.
     */
    private void initGUI() {
        JPanel mainPanel = new JPanel();
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

    /**
     * This method gets the game input from the user.
     * It waits for the user to click on a column in the game panel.
     * @return the column number where the user wants to put their token.
     */
    @Override
    public int getGameInput() {
        CompletableFuture<Integer> input = new CompletableFuture<>();
        gamePanel.getNextInput(input);
        int col = input.join();
        gamePanel.clearInput();
        return col;
    }

    /**
     * This method updates the User Interface with the current game state.
     * @param cells the current state of the game board.
     * @param currentTurn the current turn.
     */
    @Override
    public void updateUI(int[][] cells, int currentTurn) {
        gamePanel.updateUI(cells, currentTurn);
    }

    /**
     * This method updates the game board.
     * @param cells the current state of the game board.
     */
    @Override
    public void updateBoard(int[][] cells) {
        gamePanel.updateBoard(cells);
    }

    /**
     * This method waits for the user to confirm an action.
     * It displays a confirmation dialog and returns the user's choice.
     * @param message the message to display to the user.
     * @return true if the user confirms, false otherwise.
     */
    @Override
    public boolean waitForConfirmation(String message) {
        int response = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    /**
     * This method is called when the user input is wrong.
     * It displays an error message based on the reason for the wrong input.
     * @param reason the reason why the input is wrong.
     */
    @Override
    public void wrongInput(InputErrorReason reason) {
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

    /**
     * This method announces the winner of the game.
     * It displays a dialog with the win message.
     * @param victoryStatus the status of the victory.
     */
    @Override
    public void announceWinner(int victoryStatus) {
        String message = IUserInterface.getWinMessage(victoryStatus, onlineMode);
        JOptionPane.showMessageDialog(this, message, "Game over", JOptionPane.INFORMATION_MESSAGE);
    }

    private InfoDialog connectInfo;
    /**
     * This method shows the connection information to the user.
     * It displays a dialog with the IP addresses and port number.
     * @param ip the IP addresses.
     * @param port the port number.
     */
    @Override
    public void showConnectionInfo(List<String> ip, int port) {
        ip.add(0, "To Connect Use One Of these IP:");
        InfoDialog.Builder infoDialog = DialogFactory.getInfoBuilder()
                .setTitle("Connection Info:")
                .setInfo(ip)
                .setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18))
                .setSplitOnNewLine(true)
                .setUseOptimalSize(true)
                .setResizeable(false)
                .setCanBeClosed(false);
        new Thread(() -> connectInfo = infoDialog.build()).start();

    }
    public void closeConnectionInfoDialogue(){
        connectInfo.dispose();
    }

    /**
     * This method gets the server address from the user.
     * It displays an input dialog and validates the input.
     * @return the server address.
     */
    @Override
    public String serverAddress() {
        String ip = "";
        while (!ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            InputDialog serverIP = DialogFactory.getInputBuilder()
                    .addInputField("Server address")
                    .setCenterText(true)
                    .setCanBeClosed(false)
                    .build();
            ip = serverIP.getInputs().get("Server address");
        }
        return ip;
    }

    /**
     * This method sets the online mode of the game.
     * @param mode the online mode.
     */
    @Override
    public void setOnlineMode(int mode) {
        onlineMode = mode;
        gamePanel.setOnlineMode(mode);
        closeConnectionInfoDialogue();
    }

    /**
     * This method is called when the user is disconnected.
     * It displays a dialog informing the user that their opponent has disconnected.
     */
    @Override
    public void userDisconnected() {
        JOptionPane.showMessageDialog(this, "Your opponent has disconnected", "Game over", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called when the server is not found.
     * It displays a dialog informing the user that the server was not found.
     */
    @Override
    public void serverNotFound() {
        JOptionPane.showMessageDialog(this, "Cannot find server on this address. Try again with different one", "Game over", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method gets the difficulty level from the user.
     * It displays a choice dialog and returns the user's choice.
     * @return the difficulty level.
     */
    @Override
    public int getDifficulty() {
        ChoiceDialog difficulty = DialogFactory.getChoiceBuilder()
                .setCenterText(true)
                .setLabel("Choose the difficulty")
                .addChoice("Easy", 0)
                .addChoice("Medium", 1)
                .addChoice("Hard", 2)
                .addChoice("Demonic", 3)
                .setCanBeClosed(false)
                .build();
        return difficulty.getChoice();
    }

    /**
     * This method is called when the back button is pressed.
     * It displays the player's statistics in a dialog.
     */
    @Override
    public void backButtonPressed() {
        PlayerStatistics stats = StatisticsHandler.getInstance().getCurrentStats();
        DialogFactory.getInfoBuilder()
                .setTitle("Statistics")
                .setInfo("Multiplayer Statistics:\n" + "Total Games: " + stats.getGamesPlayed() + "\n" + "Wins: " + stats.getGamesWon() + "\n" + "Losses: " + stats.getGamesLost() + "\n" + "Draws: " + stats.getGamesDrawn() + "\n" + "Moves made: " + stats.getMovesMade())
                .setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20))
                .setSplitOnNewLine(true)
                .setSize(new Dimension(400, 200))
                .setResizeable(false)
                .setCenterText(true)
                .setCanBeClosed(true)
                .build();
    }
}