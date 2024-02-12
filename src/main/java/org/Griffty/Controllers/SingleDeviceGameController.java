package org.Griffty.Controllers;

import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

/**
 * This class is responsible for controlling the game when playing on a single device.
 * It extends the AbstractGameController class and implements the makeTurn method.
 */
public class SingleDeviceGameController extends AbstractGameController{

    /**
     * Constructor for the SingleDeviceGameController class.
     * @param inputType The type of user interface to use for the game.
     */
    public SingleDeviceGameController(InputType inputType) {
        super(inputType);
        startGame();
    }

    /**
     * Makes a turn in the game.
     * It gets the player's input and tries to make a move.
     * If the move is not valid, it asks for the player's input again.
     */
    @Override
    protected void makeTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
        }
    }
}