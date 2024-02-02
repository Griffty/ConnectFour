package org.Griffty;

import org.Griffty.Controllers.AIGameController;
import org.Griffty.Controllers.ClientDeviceGameController;
import org.Griffty.Controllers.ServerDeviceGameController;
import org.Griffty.Controllers.SingleDeviceGameController;
import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.enums.InputType;

import java.util.concurrent.CompletableFuture;

import static org.Griffty.enums.InputType.CLI;
import static org.Griffty.enums.InputType.GUI;

public class Main {
    public static void main(String[] args) {
        InputType gameType = GUI;
        if (System.console() != null){
            gameType = CLI;
        }
        String launchOption;
        if(gameType == CLI){
            try {
                launchOption = args[0];
            }catch (Exception exception){
                 printHelp();
                 return;
            }
        }else {
            CompletableFuture<Integer> intLaunchOption = new CompletableFuture<>();
            new GameModeDialog(intLaunchOption);
            launchOption = parseLaunchOption(intLaunchOption.join());
        }
        switch (launchOption){
            case "--solo":
            case "-s":
                new SingleDeviceGameController(gameType);
                break;
            case "--host":
            case "-h":
                new ServerDeviceGameController(gameType);
                break;
            case "--join":
            case "-j":
                new ClientDeviceGameController(gameType);
                break;
            case "--bot":
            case "-b":
                new AIGameController(gameType);
                break;
            case "--help":
                printHelp();
                break;
            case "--clear-stats":
                StatisticsHandler.getInstance().clearStats();
                break;

            default:
                printHelp();
                System.exit(1);
        }
    }

    private static String parseLaunchOption(Integer join) { //todo: add AI
        return switch (join) {
            case 0 -> "-s";
            case 1 -> "-h";
            case 2 -> "-j";
            case 3 -> "-b";
            default -> null;
        };
    }

    private static void printHelp(){ //todo: add AI
        System.out.println("""
                Usage: java -jar ConnectFour.jar <launch-option>
                To use this program with GUI just launch it with double click
                If you want to use it from terminal start it with one of the
                following launch option
                
                Available launch options:
                    <-s | --solo> start game on this computer
                    <-h | --host> host game for people in local network
                    <-j | --join> join other people in local network""");
    }
}
