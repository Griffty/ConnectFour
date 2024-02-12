package org.Griffty;

import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.Util.Dialogs.ChoiceDialog;
import org.Griffty.Util.Dialogs.DialogFactory;
import org.Griffty.enums.InputType;
import org.Griffty.Controllers.*;

import static org.Griffty.enums.InputType.CLI;
import static org.Griffty.enums.InputType.GUI;

/**
 * This is the main class for the Connect Four game.
 * It handles the game's launch options and starts the appropriate game controller based on the chosen option.
 */
public class Main {
    /**
     * The main method of the Connect Four game.
     * It determines the game type (CLI or GUI), parses the launch option, and starts the appropriate game controller.
     * @param args the command-line arguments.
     */
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
            ChoiceDialog gameModDialog = DialogFactory.getChoiceBuilder()
                    .setLabel("Choose game mode")
                    .addChoice("Solo", 0)
                    .addChoice("Host", 1)
                    .addChoice("Join", 2)
                    .addChoice("Bot", 3)
                    .build();
            launchOption = parseLaunchOption(gameModDialog.getChoice());
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

    /**
     * This method parses the launch option chosen by the user in the GUI.
     * @param join the choice made by the user in the GUI.
     * @return the corresponding command-line argument.
     */
    private static String parseLaunchOption(Integer join) { //todo: add AI
        return switch (join) {
            case 0 -> "-s";
            case 1 -> "-h";
            case 2 -> "-j";
            case 3 -> "-b";
            default -> null;
        };
    }

    /**
     * This method prints the help message for the command-line interface.
     */
    private static void printHelp(){ //todo: add AI
        System.out.println("""
                Usage: java -jar ConnectFour.jar <launch-option>
                To use this program with GUI just launch it with double click
                If you want to use it from terminal start it with one of the
                following launch option

                Available launch options:
                    <-s | --solo> start game on this computer
                    <-h | --host> host game for people in local network
                    <-j | --join> join other people in local network
                    <-b | --bot> play against computer""");
    }
}