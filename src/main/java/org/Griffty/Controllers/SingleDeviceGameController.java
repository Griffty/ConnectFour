package org.Griffty.Controllers;

import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

public class SingleDeviceGameController extends AbstractGameController{
    public SingleDeviceGameController(InputType inputType) {
        super(inputType);
        startGame();
    }

    @Override
    protected void makeTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
        }
    }
}
