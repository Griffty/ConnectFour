package org.Griffty.UserInterface.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

public class GamePanel extends JPanel {
    private int[][] currentCells;
    private final JLabel currentTurn;
    private CompletableFuture<Integer> input;
    private int onlineMode;
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
                        input.complete(col);
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
    public void getNextInput(CompletableFuture<Integer> input) {
        if (this.input != null){
            throw new IllegalStateException("Input already requested");
        }
        this.input = input;
    }
    public void clearInput(){
        input = null;
    }
    public void setOnlineMode(int mode){
        onlineMode = mode;
    }
    public void updateUI(int[][] cells, int currentTurn) {
        currentCells = cells;
        updateCurrentTurn(currentTurn);
        repaint();
    }
    public void updateBoard(int[][] cells) {
        currentCells = cells;
        repaint();
    }
}
