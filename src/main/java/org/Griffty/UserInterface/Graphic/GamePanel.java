package org.Griffty.UserInterface.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents the game panel for the Connect Four game.
 * It extends JPanel and handles the game's graphical interface, including the game board and user interactions.
 */
public class GamePanel extends JPanel {
    private int[][] currentCells;
    private final JLabel currentTurn;
    private CompletableFuture<Integer> input;
    private int onlineMode;
    /**
     * The constructor initializes the game panel, sets up the game board, and adds a mouse listener for user interactions.
     */
    public GamePanel() {
        currentCells = new int[6][7];
        currentTurn = new JLabel();
        currentTurn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        add(currentTurn);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1){
                    int col = getColFromInput(e.getX(), e.getY());
                    if (input != null ) {

                        if (col == -1){
                            return;
                        }
                        input.complete(col - 1);
                    }
                }
            }

            private int getColFromInput(int x, int y) {
                if (y < YOffset || y > YOffset + cellSize * 6) {
                    return -1;
                }
                if (x < XOffset || x > XOffset + cellSize * 7) {
                    return -1;
                }
                int col = (x - XOffset) / cellSize;
                if (col < 0 || col > 6) {
                    return -1;
                }
                return col+1;
            }
        });
    }

    // Color constants for the game board and tokens
    private static final Color backgroundColor = Color.lightGray;
    private static final Color boardColor = Color.black;

    private static final Color redTokenColor = new Color(255, 0, 0);
    private static final Color yellowTokenColor = new Color(255, 255, 0);
    private static final Color redTokenBorderColor = new Color(90, 0, 0);
    private static final Color yellowTokenBorderColor = new Color(160, 70, 0);
    private static final Color emptyTokenColor = Color.gray;

    private int YOffset;
    private int XOffset;
    private int cellSize;

    /**
     * This method is responsible for drawing the game board and the tokens.
     * It is called whenever the JPanel needs to be repainted.
     * @param g the Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(backgroundColor);
        g.fillRect(0,0,getWidth(), getHeight());
        g.setColor(boardColor);
        int sidesOffsetX = 100;
        int fieldSizeX = getWidth()-sidesOffsetX*2;
        int fieldSizeY = fieldSizeX*6/7;
        int sidesOffsetY = (getHeight()-fieldSizeY)/2;
        g.fillRect(sidesOffsetX, sidesOffsetY, fieldSizeX, fieldSizeY);

        float fieldOffsetX = 20;
        float fieldOffsetY = 20;

        float boardSizeX = fieldSizeX - fieldOffsetX * 2;
        float boardSizeY = fieldSizeY - fieldOffsetY * 2;

        float cellSize = boardSizeX/8f;
        float cellOffsetX = (boardSizeX - (int) (cellSize * 7)) / 7f;
        float cellOffsetY = (boardSizeY - (int) (cellSize * 6)) / 6f;

        this.cellSize = (int) boardSizeX/7;
        this.YOffset = (int) (sidesOffsetY + fieldOffsetX);
        this.XOffset = (int) (sidesOffsetX + fieldOffsetY);

        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 6; y++){
                if (currentCells[y][x] == 0){
                    g.setColor(emptyTokenColor);
                }else if(currentCells[y][x] == 1) {
                    g.setColor(redTokenColor);
                }else if (currentCells[y][x] == 2){
                    g.setColor(yellowTokenColor);
                }else{
                    g.setColor(Color.blue);
                }
                float cellPositionY = sidesOffsetY + fieldOffsetY + y * cellSize + cellOffsetY * y + cellOffsetY / 2;
                float cellPositionX = sidesOffsetX + fieldOffsetX + x * cellSize + cellOffsetX * x + cellOffsetX / 2;
                g.fillOval((int) cellPositionX, (int) cellPositionY, (int) cellSize, (int) cellSize);
                if (currentCells[y][x] == 0) {
                    continue;
                }
                g.setColor(currentCells[y][x] == 1 ? redTokenBorderColor : currentCells[y][x] == 2 ? yellowTokenBorderColor : Color.blue);
                g.fillOval((int) (cellPositionX + cellSize / 8f), (int) (cellPositionY + cellSize / 8f), (int)(cellSize - cellSize/4f)+2, (int)(cellSize- cellSize/4f)+2);
            }
        }
    }

    /**
     * This method updates the label that displays the current turn.
     * @param turn the current turn.
     */
    private void updateCurrentTurn(int turn){
        if (onlineMode == 0) {
            currentTurn.setText("It's " + (turn == 1 ? "red" : "yellow") + " turn");
            return;
        }
        if (onlineMode == turn){
            currentTurn.setText("It's your turn");
        }else {
            currentTurn.setText("It's your opponent's turn");
        }
    }

    /**
     * This method is used to get the next user input.
     * It sets up a CompletableFuture that will be completed when the user makes a move.
     * @param input the CompletableFuture to complete when the user makes a move.
     */
    public void getNextInput(CompletableFuture<Integer> input) {
        if (this.input != null){
            throw new IllegalStateException("Input already requested");
        }
        this.input = input;
    }

    /**
     * This method clears the current user input.
     */
    public void clearInput(){
        input = null;
    }

    /**
     * This method sets the online mode of the game.
     * @param mode the online mode.
     */
    public void setOnlineMode(int mode){
        onlineMode = mode;
    }

    /**
     * This method updates the User Interface with the current game state.
     * @param cells the current state of the game board.
     * @param currentTurn the current turn.
     */
    public void updateUI(int[][] cells, int currentTurn) {
        currentCells = cells;
        updateCurrentTurn(currentTurn);
        repaint();
    }

    /**
     * This method updates the game board.
     * @param cells the current state of the game board.
     */
    public void updateBoard(int[][] cells) {
        currentCells = cells;
        repaint();
    }
}
