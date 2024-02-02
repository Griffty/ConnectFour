package org.Griffty.Controllers;

import org.Griffty.Board;
import org.Griffty.UserInterface.ConnectFourConsoleInterface;
import org.Griffty.UserInterface.Graphic.ConnectFourGUI;
import org.Griffty.UserInterface.IUserInterface;
import org.Griffty.enums.InputType;

import java.util.Random;

public abstract class AbstractGameController { // convert to Singleton
    public static volatile boolean isGameRunning = false;
    private static final Random random = new Random();
    protected IUserInterface UI;
    protected final Board board;
    protected int currentTurn = 1; // 1 - red; 2 - yellow || 1 - you; 2 - enemy
    protected boolean forceStop = false;
    public AbstractGameController(InputType inputType) {
        board = new Board();
        switch (inputType){
            case CLI:
                UI = new ConnectFourConsoleInterface();
                break;
            case GUI:
                UI = new ConnectFourGUI();
                break;
            case None:
                throw new IllegalArgumentException("Input type can't be None");
        }
    }
    protected void startGame(){
        board.clear();
        currentTurn = random.nextInt(2) + 1;
        int victoryStatus;
        while ((victoryStatus = board.checkWin(true)) == 0) {
            currentTurn = 3 - currentTurn;
            if (forceStop) {
                forceStop = false;
                return;
            }
            updateUI(board.getCells(), currentTurn);
            makeTurn();
        }
        isGameRunning = false;
        updateBoard(board.getCells());
        announceWinner(victoryStatus);
        if (playAgain()){
            startGame();
            return;
        }
        System.exit(0);
    }
    protected void stopGame() {
        forceStop = true;
    }
    protected abstract void makeTurn();
    public boolean playAgain() {
        return UI.waitForConfirmation("Do you want to play again?");
    }
    public void announceWinner(int victoryStatus) {
        UI.announceWinner(victoryStatus);
    }
    public void updateUI(int[][] cells, int currentTurn) {
        UI.updateUI(cells, currentTurn);
    }
    public void updateBoard(int[][] cells) {
        UI.updateBoard(cells);
    }
}