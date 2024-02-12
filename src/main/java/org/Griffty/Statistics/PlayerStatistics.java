package org.Griffty.Statistics;

/**
 * This class represents the statistics of a player.
 * It keeps track of the number of games played, won, lost, drawn, and the number of moves made by the player.
 */
public class PlayerStatistics {
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDrawn;
    private int movesMade;

    /**
     * This is the constructor of the PlayerStatistics.
     * It initializes all the statistics to 0.
     */
    public PlayerStatistics() {
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        gamesDrawn = 0;
        movesMade = 0;
    }

    /**
     * This method is used to add a game to the statistics.
     * It increments the number of games played and updates the number of games won, lost, or drawn based on the victory state.
     * @param victoryState The state of the victory (0 for won, 1 for lost, 2 for drawn).
     */
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

    /**
     * This method is used to add a move to the statistics.
     * It increments the number of moves made.
     */
    public void addMove(){
        movesMade ++;
    }

    /**
     * This method is used to remove a move from the statistics.
     * It decrements the number of moves made.
     */
    public void removeMove(){
        movesMade--;
    }

    /**
     * This method is used to get the number of games played.
     * @return The number of games played.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * This method is used to get the number of games won.
     * @return The number of games won.
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * This method is used to get the number of games lost.
     * @return The number of games lost.
     */
    public int getGamesLost() {
        return gamesLost;
    }

    /**
     * This method is used to get the number of games drawn.
     * @return The number of games drawn.
     */
    public int getGamesDrawn() {
        return gamesDrawn;
    }

    /**
     * This method is used to get the number of moves made.
     * @return The number of moves made.
     */
    public int getMovesMade() {
        return movesMade;
    }
}