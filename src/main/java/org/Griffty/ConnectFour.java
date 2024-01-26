package org.Griffty;

import org.Griffty.enums.InputType;

import java.util.Random;

import static org.Griffty.enums.InputErrorReason.*;
import static org.Griffty.enums.InputType.Basic;
public class ConnectFour {
    private static final Random random = new Random();
    private final IConnectFourInterface gI;
    private int currentTurn = 1; // 1 - red; 2 - yellow || 1 - you; 2 - enemy
    private final Board board;
    private ConnectFour(IConnectFourInterface gameInterface){
        this.gI = gameInterface;
        board = new Board();
        gameCycle();
    }

    private void gameCycle(){
        board.clear();
        currentTurn = random.nextInt(2) + 1;
        int victoryStatus;
        while ((victoryStatus = board.checkWin()) == 0) {
            gI.updateUI(board, currentTurn); //todo: add wait for finish

            int col = gI.getGameInput(currentTurn);
            while (!board.putToken(col, currentTurn)){
                gI.wrongInput(COLUMN_FULL);
                col = gI.getGameInput(currentTurn);
            }
            nextTurn();
        }
        gI.updateBoard(board);
        gI.announceWinner(victoryStatus);
        playAgain();
    }

    private void playAgain() {
        if (gI.playAgain()){
            gameCycle();
        }
        System.exit(0);
    }

    private void nextTurn() {
        currentTurn = (currentTurn == 1 ? 2 : 1);
    }

    private static final InputType type = Basic;
    private static final IConnectFourInterface gInterface = new ConnectFourConsoleInterface();

    public static void main(String[] args) {
        gInterface.setInputType(type).thenApply((response) ->{
            if (response.equals("1")) {
                new ConnectFour(gInterface);
            }else {
                System.out.println("Error during setting input type: \n" + response);
            }
            return null;
        });
    }
}
