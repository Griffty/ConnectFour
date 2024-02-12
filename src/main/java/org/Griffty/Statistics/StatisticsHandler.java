package org.Griffty.Statistics;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;

/**
 * This class is responsible for handling player statistics.
 * It uses the Singleton design pattern to ensure only one instance of this class is created.
 */
public class StatisticsHandler {
    private static StatisticsHandler instance;

    /**
     * This method returns the single instance of the StatisticsHandler class.
     * If the instance is null, it creates a new instance.
     * @return the single instance of the StatisticsHandler class.
     */
    public static StatisticsHandler getInstance() {
        if (instance == null){
            instance = new StatisticsHandler();
        }
        return instance;
    }

    private static final String savePath = getSystemSpecificProgramFolder() + File.separator + "statistics.json";
    private PlayerStatistics currentStats = new PlayerStatistics();

    /**
     * The constructor is private to prevent creating multiple instances.
     * It reads the current statistics from a file and adds a shutdown hook to write the statistics to the file.
     */
    private StatisticsHandler() {
        currentStats = readStat();
        Runtime.getRuntime().addShutdownHook(new Thread(this::writeStat));
    }

    /**
     * This method reads the player statistics from a file.
     * If the file does not exist, it returns a new PlayerStatistics object.
     * @return the player statistics.
     */
    private PlayerStatistics readStat() {
        Gson gson = new Gson();
        File file = new File(savePath);
        if (!file.exists()){
            return new PlayerStatistics();
        }
        try {
            return gson.fromJson(new String(Files.readAllBytes(file.toPath())), PlayerStatistics.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new PlayerStatistics();
        }
    }

    /**
     * This method writes the current player statistics to a file.
     */
    private void writeStat() {
        Gson gson = new Gson();
        File file = new File(savePath);
        try {
            Files.write(file.toPath(), gson.toJson(currentStats).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the system-specific program folder.
     * If the folder does not exist, it creates the folder.
     * @return the system-specific program folder.
     */
    private static String getSystemSpecificProgramFolder() {
        String homePath = System.getProperty("user.home");
        String folderPath = homePath + File.separator + "GrifftyConnectFour";
        File folder = new File(folderPath);
        if (!folder.exists()){
            folder.mkdirs();
        }
        return folderPath;
    }

    /**
     * This method adds a game to the player statistics.
     * @param victoryState the state of the game.
     */
    public void addGame(int victoryState){
        currentStats.addGame(victoryState);
    }

    /**
     * This method adds a move to the player statistics.
     */
    public void addMove(){
        currentStats.addMove();
    }

    /**
     * This method removes a move from the player statistics.
     */
    public void removeMove() {
        currentStats.removeMove();
    }

    /**
     * This method returns the current player statistics.
     * @return the current player statistics.
     */
    public PlayerStatistics getCurrentStats() {
        return currentStats;
    }

    /**
     * This method clears the player statistics and writes the empty statistics to the file.
     */
    public void clearStats() {
        currentStats = new PlayerStatistics();
        writeStat();
    }
}