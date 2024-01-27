package org.Griffty.UserInterface;

import org.Griffty.enums.InputErrorReason;

public interface IUserInterface {
    int getGameInput();
    void updateUI(int[][] cells, int currentTurn);
    void updateBoard(int[][] cells);
    boolean waitForConfirmation(String message);
    void wrongInput(InputErrorReason reason);
    void announceWinner(int victoryStatus);
    void showConnectionInfo(String ip, int port);
    String serverAddress();
    void setOnlineMode(int mode);
    void userDisconnected();
    void serverNotFound();
}
