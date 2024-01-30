package org.Griffty.UserInterface;

import org.Griffty.enums.InputErrorReason;

import java.util.List;

public interface IUserInterface {
    int getGameInput();
    void updateUI(int[][] cells, int currentTurn);
    void updateBoard(int[][] cells);
    boolean waitForConfirmation(String message);
    void wrongInput(InputErrorReason reason);
    void announceWinner(int victoryStatus);
    void showConnectionInfo(List<String> ip, int port);
    String serverAddress();
    void setOnlineMode(int mode);
    void userDisconnected();
    void serverNotFound();
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
}
