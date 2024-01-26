package org.Griffty;

import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.util.concurrent.CompletableFuture;

public interface IConnectFourInterface {
    int getGameInput(int currentTurn);
    boolean playAgain();
    void announceWinner(int victoryStatus);
    void updateBoard(Board board);
    void updateUI(Board board, int currentTurn);
    void wrongInput(InputErrorReason reason);

    CompletableFuture<String> setInputType(InputType inputType);
}
