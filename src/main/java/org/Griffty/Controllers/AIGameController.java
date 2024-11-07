package org.Griffty.Controllers;

import org.Griffty.AI.AIPredictor;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

/**
 * This class is responsible for controlling the game when playing against the AI.
 * It extends the AbstractGameController class and implements the makeTurn method.
 */
public class AIGameController extends AbstractGameController {
    private final AIPredictor ai;

    /**
     * Constructor for the AIGameController class.
     * @param inputType The type of user interface to use for the game.
     */
    public AIGameController(InputType inputType) {
        super(inputType);
        int depth = UI.getDifficulty();
        ai = new AIPredictor(depth * 2); //todo: Use GPGPU computing for better performance || no need... it's already destroying me
        startGame();
    }

    /**
     * Makes a turn in the game.
     * If it's the player's turn, it calls the playerTurn method.
     * If it's the AI's turn, it calls the AITurn method.
     */
    @Override
    protected void makeTurn() {
        if (currentTurn == 1) {
            playerTurn();
        } else {
            AITurn();
        }
    }

    /**
     * Makes a turn for the AI.
     * It uses the AIPredictor to decide the best move and then makes that move.
     */
    private void AITurn() {
        int col = ai.makeMove(board);
        board.putToken(col, currentTurn);
    }

    /**
     * Makes a turn for the player.
     * It gets the player's input and tries to make a move.
     * If the move is not valid, it asks for the player's input again.
     */
    private void playerTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
        }
    }
}