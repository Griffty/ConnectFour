package org.Griffty.Controllers;

import org.Griffty.Board;
import org.Griffty.UserInterface.ConnectFourConsoleInterface;
import org.Griffty.UserInterface.Graphic.ConnectFourGUI;
import org.Griffty.UserInterface.IUserInterface;
import org.Griffty.enums.InputType;

import java.util.Random;

/**
 * This abstract class is responsible for controlling the game.
 * It provides the basic structure and methods for the game controller.
 */
public abstract class AbstractGameController {
    public static volatile boolean isGameRunning = false;
    private static final Random random = new Random();
    protected IUserInterface UI;
    protected final Board board;
    protected int currentTurn = 1; // 1 - red; 2 - yellow || 1 - you; 2 - enemy
    protected boolean forceStop = false;

    /**
     * Constructor for the AbstractGameController class.
     * @param inputType The type of user interface to use for the game.
     */
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

    /**
     * Starts the game.
     */
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

    /**
     * Stops the game.
     */
    protected void stopGame() {
        forceStop = true;
    }

    /**
     * Abstract method for making a turn in the game.
     */
    protected abstract void makeTurn();

    /**
     * Asks the user if they want to play again.
     * @return A boolean indicating whether the user wants to play again.
     */
    public boolean playAgain() {
        return UI.waitForConfirmation("Do you want to play again?");
    }

    /**
     * Announces the winner of the game.
     * @param victoryStatus The status of the victory.
     */
    public void announceWinner(int victoryStatus) {
        UI.announceWinner(victoryStatus);
    }

    /**
     * Updates the user interface.
     * @param cells The current state of the game board.
     * @param currentTurn The current turn in the game.
     */
    public void updateUI(int[][] cells, int currentTurn) {
        UI.updateUI(cells, currentTurn);
    }

    /**
     * Updates the game board.
     * @param cells The current state of the game board.
     */
    public void updateBoard(int[][] cells) {
        UI.updateBoard(cells);
    }
}