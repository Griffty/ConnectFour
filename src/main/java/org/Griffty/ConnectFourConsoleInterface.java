package org.Griffty;

import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.util.concurrent.CompletableFuture;

import static org.Griffty.enums.InputErrorReason.*;

public class ConnectFourConsoleInterface implements IConnectFourInterface{
    private IUserInputController userInput;
    private InputType inputType = InputType.Basic;

    @Override
    public int getGameInput(int currentTurn) {
        if (inputType == InputType.None){
            throw new IllegalStateException("Input type is not set");
        }
        try {
            int col = userInput.waitForNextInt(currentTurn);
            if (col > 7 || col < 1) {
                wrongInput(OUT_OF_BOUNDS);
                return getGameInput(currentTurn);
            }
            return col;
        }catch (Exception e){ //todo: make if statement out of exceptions
            wrongInput(NOT_A_NUMBER);
            return getGameInput(currentTurn);
        }
    }

    @Override
    public boolean playAgain() {
        System.out.println("Do you want to play again?");
        return userInput.waitForConfirmation();
    }

    @Override
    public void announceWinner(int victoryStatus) {
        StringBuilder builder = new StringBuilder("It's a ");
        if (victoryStatus > 0) {
            builder.append(victoryStatus == 1 ? "RED" : "YELLOW");
            builder.append(" victory. Well played guys!");
        }else {
            builder.append("It's a tie. What a game!");
        }
        System.out.println(builder);
    }

    @Override
    public void updateBoard(Board board) {
        System.out.println("\n\nHere is current board:");
        System.out.println(board);
    }

    @Override
    public void updateUI(Board board, int currentTurn) {
        updateBoard(board);
        System.out.println("It's " + (currentTurn == 1 ? "red" : "yellow") + " turn");
        System.out.println("To make a move, type colum you want to put your token in: ");
    }

    @Override
    public void wrongInput(InputErrorReason reason) {
        switch (reason){
            case OUT_OF_BOUNDS -> System.out.println("You can only put token in columns from 1 to 7");
            case COLUMN_FULL -> System.out.println("This column is already full");
            case NOT_A_NUMBER -> System.out.println("You can input only numbers");
            default ->
                System.out.println("Wrong input");
        }
    }

    @Override
    public CompletableFuture<String> setInputType(InputType inputType) {
        this.inputType = inputType;
        if (inputType == InputType.Basic){
            userInput = ConsoleUserInputController.getInstance();
        }
        if (inputType == InputType.Network){
            userInput = NetworkUserInputController.getInstance();
        }
        return CompletableFuture.completedFuture("1");
    }
}

