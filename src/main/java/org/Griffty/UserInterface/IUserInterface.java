package org.Griffty.UserInterface;

import org.Griffty.enums.InputErrorReason;

import java.util.List;

/**
 * This interface defines the methods that a User Interface class should implement.
 * It is used to interact with the user and update the game state.
 */
public interface IUserInterface {
    /**
     * This method gets the game input from the user.
     * @return the game input.
     */
    int getGameInput();

    /**
     * This method updates the User Interface with the current game state.
     * @param cells the current state of the game board.
     * @param currentTurn the current turn.
     */
    void updateUI(int[][] cells, int currentTurn);

    /**
     * This method updates the game board.
     * @param cells the current state of the game board.
     */
    void updateBoard(int[][] cells);

    /**
     * This method waits for the user to confirm an action.
     * @param message the message to display to the user.
     * @return true if the user confirms, false otherwise.
     */
    boolean waitForConfirmation(String message);

    /**
     * This method is called when the user input is wrong.
     * @param reason the reason why the input is wrong.
     */
    void wrongInput(InputErrorReason reason);

    /**
     * This method announces the winner of the game.
     * @param victoryStatus the status of the victory.
     */
    void announceWinner(int victoryStatus);

    /**
     * This method shows the connection information to the user.
     * @param ip the IP addresses.
     * @param port the port number.
     */
    void showConnectionInfo(List<String> ip, int port);

    /**
     * This method gets the server address from the user.
     * @return the server address.
     */
    String serverAddress();

    /**
     * This method sets the online mode of the game.
     * @param mode the online mode.
     */
    void setOnlineMode(int mode);

    /**
     * This method is called when the user is disconnected.
     */
    void userDisconnected();

    /**
     * This method is called when the server is not found.
     */
    void serverNotFound();

    /**
     * This method generates a win message based on the victory status and online mode.
     * @param victoryStatus the status of the victory.
     * @param onlineMode the online mode.
     * @return the win message.
     */
    static String getWinMessage(int victoryStatus, int onlineMode) {
        StringBuilder builder = new StringBuilder();
        if (victoryStatus > 0) {
            if (onlineMode == 0) {
                builder.append("It's a ");
                builder.append(victoryStatus == 1 ? "RED" : "YELLOW");
                builder.append(" victory. Well played guys!");
                return builder.toString();
            }
            if (onlineMode == victoryStatus) {
                builder.append("It's a victory. Well played!");
            } else {
                builder.append("You lost. Better luck next time!");
            }
        }else {
            builder.append("It's a tie. What a game!");
        }
        return builder.toString();
    }

    /**
     * This method gets the difficulty level from the user.
     * @return the difficulty level.
     */
    int getDifficulty();
}