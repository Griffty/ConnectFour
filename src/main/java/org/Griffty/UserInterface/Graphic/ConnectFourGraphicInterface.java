package org.Griffty.UserInterface.Graphic;

import org.Griffty.Board;
import org.Griffty.UserInterface.IUserInterface;
import org.Griffty.enums.InputErrorReason;

public class ConnectFourGraphicInterface implements IUserInterface { //todo: make Swing GUI
    public ConnectFourGraphicInterface() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    @Override
    public int getGameInput() {
        return 0;
    }

    @Override
    public void updateUI(int[][] cells, int currentTurn) {

    }

    @Override
    public void updateBoard(int[][] cells) {

    }

    @Override
    public boolean waitForConfirmation(String message) {
        return false;
    }

    @Override
    public void wrongInput(InputErrorReason reason) {

    }

    @Override
    public void announceWinner(int victoryStatus) {

    }

    @Override
    public void showConnectionInfo(String ip, int port) {

    }

    @Override
    public String serverAddress() {
        return null;
    }

    @Override
    public void setOnlineMode(int mode) {

    }

    @Override
    public void userDisconnected() {

    }

    @Override
    public void serverNotFound() {

    }
}
