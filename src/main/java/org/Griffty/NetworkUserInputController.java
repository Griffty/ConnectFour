package org.Griffty;

import org.Griffty.Network.WebSocketServer;

public class NetworkUserInputController implements IUserInputController{
    private static NetworkUserInputController instance;
    public static NetworkUserInputController getInstance() {
        if (instance == null){
            instance = new NetworkUserInputController();
        }
        return instance;
    }

    private NetworkUserInputController(){
        System.out.println("Main menu:\n" +
                "1. Create new game\n" +
                "2. Join existing game\n" +
                "3. Exit");
        int choice = ConsoleUserInputController.getInstance().waitForNextInt();
        switch (choice){
            case 1 -> launchServer();
            case 2 -> joinServer();
            case 3 -> System.exit(0);
        }
    }
    private void launchServer() {
        WebSocketServer.getInstance();
    }
    private void joinServer() {
        System.out.println("Enter server IP address");
        String ip = ConsoleUserInputController.getInstance().waitForNextLine();
    }


    @Override
    public int waitForNextInt(int currentTurn) {
        return 0;
    }

    @Override
    public boolean waitForConfirmation() {
        return true;
    }
}
