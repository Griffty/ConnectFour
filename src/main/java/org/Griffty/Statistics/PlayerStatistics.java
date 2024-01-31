package org.Griffty.Statistics;

public class PlayerStatistics {
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDrawn;
    private int movesMade;


    public PlayerStatistics() {
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        gamesDrawn = 0;
        movesMade = 0;
    }
    public void addGame(int victoryState){
        gamesPlayed++;
        switch (victoryState){
            case 0:
                gamesWon++;
                break;
            case 1:
                gamesLost++;
                break;
            case 2:
                gamesDrawn++;
                break;
        }
    }
    public void addMove(){
        movesMade ++;
    }
    public void removeMove(){
        movesMade--;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGamesDrawn() {
        return gamesDrawn;
    }

    public int getMovesMade() {
        return movesMade;
    }
}
