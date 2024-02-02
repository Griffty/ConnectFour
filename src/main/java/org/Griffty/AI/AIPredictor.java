package org.Griffty.AI;

import org.Griffty.Board;

public class AIPredictor {
    private final int depth;
    public AIPredictor(int depth) {
        this.depth = depth;
    }
    public int makeMove(Board board) {
        Board tempBoard = new Board(board.getCells());
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;

        for (int col = 0; col < tempBoard.getColumns(); col++) {
            if (tempBoard.putToken(col, 2)) {
                int moveValue = minimax(tempBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                tempBoard.removeToken(col);

                if (moveValue > bestValue) {
                    bestValue = moveValue;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || board.checkWin(false) > 0) {
            return evaluate(board);
        }
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < board.getColumns(); col++) {
                if (board.putToken(col, 2)) {
                    int eval = minimax(board, depth - 1, alpha, beta, false);
                    board.removeToken(col);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < board.getColumns(); col++) {
                if (board.putToken(col, 1)) {
                    int eval = minimax(board, depth - 1, alpha, beta, true);
                    board.removeToken(col);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }

    public static int evaluate(Board board) {
        int[][] cells = board.getCells();
        int score = 0;

        // Higher score for central pieces
        for (int row = 0; row < 6; row++) {
            if (cells[row][3] == 2) {
                score += 3;
            }
            if (cells[row][4] == 2 || cells[row][6] == 2) {
                score += 2;
            }
        }

        // Check horizontal lines
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(cells[row][col], cells[row][col+1], cells[row][col+2], cells[row][col+3]);
            }
        }

        // Check vertical lines
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                score += evaluateLine(cells[row][col], cells[row+1][col], cells[row+2][col], cells[row+3][col]);
            }
        }

        // Check diagonal
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(cells[row][col], cells[row+1][col+1], cells[row+2][col+2], cells[row+3][col+3]);
            }
        }

        // Check diagonal
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(cells[row][col], cells[row-1][col+1], cells[row-2][col+2], cells[row-3][col+3]);
            }
        }

        return score;
    }
    private static int evaluateLine(int a, int b, int c, int d) {
        int score = 0;

        int botPieces = 0;
        int opponentPieces = 0;
        int empty = 0;
        if (a == 2) botPieces++;
        if (b == 2) botPieces++;
        if (c == 2) botPieces++;
        if (d == 2) botPieces++;
        if (a == 1) opponentPieces++;
        if (b == 1) opponentPieces++;
        if (c == 1) opponentPieces++;
        if (d == 1) opponentPieces++;
        if (a == 0) empty++;
        if (b == 0) empty++;
        if (c == 0) empty++;
        if (d == 0) empty++;

        // Check for bots rows of tokens
        if (botPieces == 4) {
            score += 1000;
        } else if (botPieces == 3 && empty == 1) {
            score += 50;
        } else if (botPieces == 2 && empty == 2) {
            score += 10;
        }

        // Check for opponents rows of tokens
        if (opponentPieces == 3 && empty == 1) {
            score -= 500;
        } else if (opponentPieces == 2 && empty == 2) {
            score -= 10;
        }
        return score;
    }
}