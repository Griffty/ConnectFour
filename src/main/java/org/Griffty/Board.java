package org.Griffty;

import java.util.Arrays;

public class Board {
    private final int[][] cells = new int[6][7];

    private void setCell(int row, int col, int side) {
        if (cells[row][col] != 0){
            throw new IllegalArgumentException("Cell is already occupied");
        }
        cells[row][col] = side;
    }

    public int[][] getCells() {
        return cells.clone();
    }

    public boolean putToken(int col, int side) {
        col--;
        for (int i = cells.length - 1; i >= 0; i--) {
            int[] row = cells[i];
            if (row[col] == 0) {
                setCell(i, col, side);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
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

    public int checkWin() {
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
                    cells[row][col] = 3;
                    cells[row][col+1] = 3;
                    cells[row][col+2] = 3;
                    cells[row][col+3] = 3;
                    return cell;
                }
            }
        }

        // Check for vertical wins
        for (int col = 0; col < cells[0].length; col++) {
            for (int row = 0; row < cells.length - 3; row++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row+1][col] && cell == cells[row+2][col] && cell == cells[row+3][col]) {
                    cells[row][col] = 3;
                    cells[row+1][col] = 3;
                    cells[row+2][col] = 3;
                    cells[row+3][col] = 3;
                    return cell;
                }
            }
        }

        // Check for diagonal wins (down-right)
        for (int row = 0; row < cells.length - 3; row++) {
            for (int col = 0; col < cells[0].length - 3; col++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row+1][col+1] && cell == cells[row+2][col+2] && cell == cells[row+3][col+3]) {
                    cells[row][col] = 3;
                    cells[row+1][col+1] = 3;
                    cells[row+2][col+2] = 3;
                    cells[row+3][col+3] = 3;
                    return cell;
                }
            }
        }

        // Check for diagonal wins (down-left)
        for (int row = 0; row < cells.length - 3; row++) {
            for (int col = 3; col < cells[0].length; col++) {
                int cell = cells[row][col];
                if (cell != 0 && cell == cells[row+1][col-1] && cell == cells[row+2][col-2] && cell == cells[row+3][col-3]) {
                    cells[row][col] = 3;
                    cells[row+1][col-1] = 3;
                    cells[row+2][col-2] = 3;
                    cells[row+3][col-3] = 3;
                    return cell;
                }
            }
        }

        return 0;
    }

    public void clear() {
        for (int[] row : cells) {
            Arrays.fill(row, 0);
        }
    }
}
