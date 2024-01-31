package org.Griffty.Statistics;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;

public class StatisticsHandler {
    private static StatisticsHandler instance;
    public static StatisticsHandler getInstance() {
        if (instance == null){
            instance = new StatisticsHandler();
        }
        return instance;
    }
    private static final String savePath = getSystemSpecificProgramFolder() + File.separator + "statistics.json";
    private PlayerStatistics currentStats = new PlayerStatistics();
    private StatisticsHandler() {
        currentStats = readStat();
        Runtime.getRuntime().addShutdownHook(new Thread(this::writeStat));
    }

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
    private void writeStat() {
        Gson gson = new Gson();
        File file = new File(savePath);
        try {
            Files.write(file.toPath(), gson.toJson(currentStats).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getSystemSpecificProgramFolder() {
        String homePath = System.getProperty("user.home");
        String folderPath = homePath + File.separator + "GrifftyConnectFour";
        File folder = new File(folderPath);
        if (!folder.exists()){
            folder.mkdirs();
        }
        return folderPath;
    }

    public void addGame(int victoryState){
        currentStats.addGame(victoryState);
    }
    public void addMove(){
        currentStats.addMove();
    }

    public void removeMove() {
        currentStats.removeMove();
    }

    public PlayerStatistics getCurrentStats() {
        return currentStats;
    }

    public void clearStats() {
        currentStats = new PlayerStatistics();
        writeStat();
    }
}
