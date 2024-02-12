package org.Griffty;

import java.util.Arrays;

/**
 * This class represents the game board for a Connect Four game.
 * It includes methods for manipulating the game board and checking for wins.
 */
public class Board {
    private final int[][] cells;
    /**
     * Constructor that initializes the game board with a given 2D array of cells.
     * @param cells the initial state of the game board.
     */
    public Board(int[][] cells) {
        this.cells = cells.clone();
        for (int i = 0; i < cells.length; i++) {
            this.cells[i] = cells[i].clone();
        }
    }
    /**
     * Default constructor that initializes an empty game board.
     */
    public Board() {
        cells = new int[6][7];
    }

    /**
     * Sets the value of a cell in the game board.
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @param side the value to set the cell to.
     * @throws IllegalArgumentException if the cell is already occupied.
     */
    private void setCell(int row, int col, int side) {
        if (cells[row][col] != 0){
            throw new IllegalArgumentException("Cell is already occupied");
        }
        cells[row][col] = side;
    }

    /**
     * Returns a clone of the game board.
     * @return a clone of the game board.
     */
    public int[][] getCells() {
        int[][] cells = new int[this.cells.length][this.cells[0].length];
        for (int i = 0; i < this.cells.length; i++) {
            cells[i] = this.cells[i].clone();
        }
        return cells;
    }

    /**
     * Attempts to put a token in a column on the game board.
     * @param col the column to put the token in.
     * @param side the side of the token.
     * @return true if the token was successfully placed, false otherwise.
     */
    public boolean putToken(int col, int side) {
        for (int i = cells.length - 1; i >= 0; i--) {
            int[] row = cells[i];
            if (row[col] == 0) {
                if (side == 3){
                    System.out.println("Side is 3");
                }
                setCell(i, col, side);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the top token from a column on the game board.
     * @param col the column to remove the token from.
     */
    public void removeToken(int col) {
        for (int[] row : cells) {
            if (row[col] != 0) {
                row[col] = 0;
                return;
            }
        }
        System.out.println("Column is empty");
    }

    /**
     * Checks the game board for a win.
     * @param replace if true, replaces the winning tokens with 3s.
     * @return the side of the winning tokens, or 0 if there is no win, or -1 if the board is full.
     */
    public int checkWin(boolean replace) {
        boolean hasEmpty = false;
        for (int[] row : cells) {
            for (int cell : row) {
                if (cell == 0) {
                    hasEmpty = true;
                    break;
                }
            }
        }
        if (!hasEmpty) {
            return -1;
        }
        // Check for horizontal wins
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length - 3; col++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row][col+1] && cell == cells[row][col+2] && cell == cells[row][col+3]) {
                    if (replace) {
                        cells[row][col] = 3;
                        cells[row][col + 1] = 3;
                        cells[row][col + 2] = 3;
                        cells[row][col + 3] = 3;
                    }
                    return cell;
                }
            }
        }

        // Check for vertical wins
        for (int col = 0; col < cells[0].length; col++) {
            for (int row = 0; row < cells.length - 3; row++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row+1][col] && cell == cells[row+2][col] && cell == cells[row+3][col]) {
                    if (replace) {
                        cells[row][col] = 3;
                        cells[row + 1][col] = 3;
                        cells[row + 2][col] = 3;
                        cells[row + 3][col] = 3;
                    }
                    return cell;
                }
            }
        }

        // Check for diagonal wins (down-right)
        for (int row = 0; row < cells.length - 3; row++) {
            for (int col = 0; col < cells[0].length - 3; col++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row+1][col+1] && cell == cells[row+2][col+2] && cell == cells[row+3][col+3]) {
                    if (replace) {
                        cells[row][col] = 3;
                        cells[row + 1][col + 1] = 3;
                        cells[row + 2][col + 2] = 3;
                        cells[row + 3][col + 3] = 3;
                    }
                    return cell;
                }
            }
        }

        // Check for diagonal wins (down-left)
        for (int row = 0; row < cells.length - 3; row++) {
            for (int col = 3; col < cells[0].length; col++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row+1][col-1] && cell == cells[row+2][col-2] && cell == cells[row+3][col-3]) {
                    if (replace) {
                        cells[row][col] = 3;
                        cells[row + 1][col - 1] = 3;
                        cells[row + 2][col - 2] = 3;
                        cells[row + 3][col - 3] = 3;
                    }
                    return cell;
                }
            }
        }

        return 0;
    }

    /**
     * Clears the game board.
     */
    public void clear() {
        for (int[] row : cells) {
            Arrays.fill(row, 0);
        }
    }

    /**
     * Converts a string representation of a game board to a 2D array.
     * @param cellsString the string representation of the game board.
     * @return the 2D array representation of the game board.
     */
    public static int[][] StringToCells(String cellsString){
        int[][] cells = new int[6][7];
        for (int i = 0; i < cellsString.length(); i++) {
            int row = i / 7;
            int col = i % 7;
            cells[row][col] = Integer.parseInt(String.valueOf(cellsString.charAt(i)));
        }
        return cells;
    }

    /**
     * Converts a 2D array representation of a game board to a string.
     * @param cells the 2D array representation of the game board.
     * @return the string representation of the game board.
     */
    public static String cellsToString(int[][] cells) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : cells) {
            for (int cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    /**
     * Returns the number of columns in the game board.
     * @return the number of columns in the game board.
     */
    public int getColumns() {
        return cells[0].length;
    }
}
