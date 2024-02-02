package org.Griffty.Controllers;

import org.Griffty.AI.AIPredictor;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

public class AIGameController extends AbstractGameController {
    private final AIPredictor ai;
    public AIGameController(InputType inputType) {
        super(inputType);
        int depth = UI.getDifficulty();
        depth = depth == 4 ? 5 : depth;
        ai = new AIPredictor(depth * 2); //todo: Use GPGPU computing for better performance || no need... it's already destroying me
        startGame();
    }

    @Override
    protected void makeTurn() {
        if (currentTurn == 1) {
            playerTurn();
        } else {
            AITurn();
        }
    }

    private void AITurn() {
        int col = ai.makeMove(board);
        board.putToken(col, currentTurn);
    }

    private void playerTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
        }
    }
}
