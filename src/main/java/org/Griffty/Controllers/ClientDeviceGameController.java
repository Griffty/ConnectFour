package org.Griffty.Controllers;

import org.Griffty.Network.WebSocketClientEndpoint;
import org.Griffty.Network.WebSocketServer;
import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

/**
 * This class is responsible for controlling the game when playing on a client device.
 * It extends the AbstractGameController class and implements the startGame and requestGameInput methods.
 */
public class ClientDeviceGameController extends AbstractGameController{
    private WebSocketClientEndpoint client;
    public CountDownLatch countdown;

    /**
     * Constructor for the ClientDeviceGameController class.
     * @param inputType The type of user interface to use for the game.
     */
    public ClientDeviceGameController(InputType inputType) {
        super(inputType);
        startGame();
    }

    /**
     * Starts the game.
     * It connects to the server and waits for the countdown to finish.
     * If the connection fails, it asks the user if they want to try another address.
     */
    @Override
    protected void startGame() {
        countdown = new CountDownLatch(1);
        String address = "ws://" + UI.serverAddress() + ":" + WebSocketServer.PORT + "/connect-four/server";
        URI uri = URI.create(address);
        try {
            client = WebSocketClientEndpoint.connectToServer(this, uri);
        } catch (Exception e) {
            UI.serverNotFound();
            if (UI.waitForConfirmation("Do you want to try another address?")) {
                startGame();
            }
            System.exit(0);
        }
        UI.setOnlineMode(2);
        try {
            countdown.await();
            UI.userDisconnected();
            if (UI.waitForConfirmation("Do you want to try another address?")) {
                startGame();
                return;
            }
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Requests game input from the user and sends it to the server.
     */
    public void requestGameInput() {
        String input = String.valueOf(UI.getGameInput());
        StatisticsHandler.getInstance().addMove();
        client.respond("gameInput:" + input);
    }

    /**
     * Asks the user if they want to play again and sends their decision to the server.
     * @return A boolean indicating whether the user wants to play again.
     */
    @Override
    public boolean playAgain() {
        boolean decision = UI.waitForConfirmation("Do you want to play again?");
        client.respond("playAgain:" + decision);
        return decision;
    }

    /**
     * Throws an UnsupportedOperationException because this method should not be called from the client side.
     */
    @Override
    protected void makeTurn() {
        throw new UnsupportedOperationException("Should not be called from client side");
    }

    /**
     * Notifies the user of an invalid input and removes the move from the statistics.
     * @param errorReason The reason why the input was invalid.
     */
    public void wrongInput(InputErrorReason errorReason) {
        UI.wrongInput(errorReason);
        StatisticsHandler.getInstance().removeMove();
    }
}