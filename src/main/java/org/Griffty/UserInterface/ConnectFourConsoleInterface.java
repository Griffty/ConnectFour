package org.Griffty.UserInterface;

import org.Griffty.Board;
import org.Griffty.enums.InputErrorReason;

import java.util.Arrays;
import java.util.Scanner;

import static org.Griffty.enums.InputErrorReason.*;

public class ConnectFourConsoleInterface implements IUserInterface{
    private Scanner userInput = new Scanner(System.in);
    private int onlineMode = 0;

    @Override
    public int getGameInput() {
        userInput = new Scanner(System.in);
        try {
            int col = userInput.nextInt();
            if (col > 7 || col < 1) {
                wrongInput(OUT_OF_BOUNDS);
                return getGameInput();
            }
            return col;
        }catch (Exception e){ //todo: make if statement out of exceptions
            wrongInput(NOT_A_NUMBER);
            return getGameInput();
        }
    }

    @Override
    public void announceWinner(int victoryStatus) {
        StringBuilder builder = new StringBuilder();
        if (victoryStatus > 0) {
            if (onlineMode == 0) {
                builder.append("It's a ");
                builder.append(victoryStatus == 1 ? "RED" : "YELLOW");
                builder.append(" victory. Well played guys!");
                System.out.println(builder);
                return;
            }
            if (onlineMode == victoryStatus) {
                builder.append("It's a victory. Well played!");
            } else {
                builder.append("You lost. Better luck next time!");
            }
        }else {
            builder.append("It's a tie. What a game!");
        }
        System.out.println(builder);
    }

    @Override
    public void showConnectionInfo(String ip, int port) {
        System.out.println("For other users to connect to you, use this address: " + ip);
    }

    @Override
    public String serverAddress() {
        userInput = new Scanner(System.in);
        System.out.println("Enter server address: ");
        String input = userInput.next();
        while (!input.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")){
            wrongInput(WRONG_SERVER_CREDENTIALS);
            input = userInput.next();
        }
        return input;
    }

    @Override
    public void setOnlineMode(int mode) {
        onlineMode = mode;
    }

    @Override
    public void userDisconnected() {
        System.out.println("Your opponent has disconnected");
    }

    @Override
    public void serverNotFound() {
        System.out.println("Cannot find server on this address. Try again with different one");
    }


    @Override
    public void updateBoard(int[][] cells) {
        System.out.println("\n\nHere is current board:");
        System.out.println(parseBoard(cells));
    }

    @Override
    public boolean waitForConfirmation(String message) {
        userInput = new Scanner(System.in);
        System.out.println(message + " (y/n)");
        String input = userInput.next();
        while (!input.equals("y") && !input.equals("n")){
            wrongInput(NOT_A_CONFIRMATION);
            input = userInput.next();
        }
        return input.equals("y");
    }

    @Override
    public void updateUI(int[][] cells, int currentTurn) {
        updateBoard(cells);
        if (onlineMode == 0) {
            System.out.println("It's " + (currentTurn == 1 ? "red" : "yellow") + " turn");
            System.out.println("To make a move, type colum you want to put your token in: ");
        return;
        }
        if (onlineMode == currentTurn){
            System.out.println("It's your turn");
            System.out.println("To make a move, type colum you want to put your token in: ");
        }else {
            System.out.println("It's your opponent's turn");
        }
    }

    @Override
    public void wrongInput(InputErrorReason reason) {
        switch (reason){
            case OUT_OF_BOUNDS -> System.out.println("You can only put token in columns from 1 to 7");
            case COLUMN_FULL -> System.out.println("This column is already full");
            case NOT_A_NUMBER -> System.out.println("You can input only numbers");
            case NOT_A_CONFIRMATION -> System.out.println("You can only input y or n");
            case WRONG_SERVER_CREDENTIALS -> System.out.println("Wrong server credentials. Address should look like 123.456.78.90 or similar. Try again");
            default ->
                System.out.println("Wrong input");
        }
    }

    private String parseBoard(int[][] cells){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("-".repeat(13));
        sb.append("\n");
        for (int[] row : cells) {
            for (int i = 0; i < row.length; i++) {
                int cell = row[i];
                sb.append(cell == 0 ? " " : cell == 3 ? "@" : cell == 1 ? "X" : "O");
                if (i != row.length - 1) {
                    sb.append("|");
                }
            }
            sb.append("\n");
            sb.append("-".repeat(13));
            sb.append("\n");
        }
        return sb.toString();
    }
}

