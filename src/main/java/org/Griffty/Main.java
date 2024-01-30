package org.Griffty;

import org.Griffty.Controllers.ClientDeviceGameController;
import org.Griffty.Controllers.ServerDeviceGameController;
import org.Griffty.Controllers.SingleDeviceGameController;

import static org.Griffty.enums.InputType.*;

public class Main {
    // todo: replace java scanner with something better
    public static void main(String[] args) {
        if (args.length !=1){
            printHelp();
            System.exit(1);
        }
        String launchOption = args[0];
        switch (launchOption){
            case "--solo":
            case "-s":
                new SingleDeviceGameController(GUI);
                break;
            case "--host":
            case "-h":
                new ServerDeviceGameController(GUI);
                break;
            case "--join":
            case "-j":
                new ClientDeviceGameController(GUI);
                break;
            default:
                printHelp();
                System.exit(1);
        }
    }
    private static void printHelp(){
        System.out.println("""
                Usage: java -jar ConnectFour.jar <launch-option>
                Available launch options:
                    <-s | --solo> start game on this computer
                    <-h | --host> host game for people in local network
                    <-j | --join> join other people in local network""");
    }
}
