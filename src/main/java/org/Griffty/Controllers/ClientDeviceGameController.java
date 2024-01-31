package org.Griffty.Controllers;

import org.Griffty.Network.WebSocketClientEndpoint;
import org.Griffty.Network.WebSocketServer;
import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

public class ClientDeviceGameController extends AbstractGameController{
    private WebSocketClientEndpoint client;
    public ClientDeviceGameController(InputType inputType) {
        super(inputType);
        startGame();
    }
    public CountDownLatch countdown;
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
    public void requestGameInput() {
        String input = String.valueOf(UI.getGameInput());
        StatisticsHandler.getInstance().addMove();
        client.respond("gameInput:" + input);
    }

    @Override
    public boolean playAgain() {
        boolean decision = UI.waitForConfirmation("Do you want to play again?");
        client.respond("playAgain:" + decision);
        return decision;
    }

    @Override
    protected void makeTurn() {
        throw new UnsupportedOperationException("Should not be called from client side");
    }
    public void wrongInput(InputErrorReason errorReason) {
        UI.wrongInput(errorReason);
        StatisticsHandler.getInstance().removeMove();
    }
}
