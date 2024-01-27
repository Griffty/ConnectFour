package org.Griffty;

import org.Griffty.Network.WebSocketClientEndpoint;
import org.Griffty.Network.WebSocketServer;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

public class ClientDeviceGameController extends AbstractGameController{ //dont extend abstractGameController
    private WebSocketClientEndpoint client;
    private String address;
    public ClientDeviceGameController(InputType inputType) {
        super(inputType);
        startGame();
    }
    public CountDownLatch countdown;
    @Override
    protected void startGame() {
        countdown = new CountDownLatch(1);
        address = "ws://" + UI.serverAddress() + ":" + WebSocketServer.PORT + "/connect-four/server";
        URI uri = URI.create(address);
        try {
            client = WebSocketClientEndpoint.connectToServer(this, uri);
        } catch (Exception e) {
            UI.serverNotFound();
            if (UI.waitForConfirmation("Do you want to try another address?")) {
                startGame();
            }
            return;
        }
        UI.setOnlineMode(2);
        try {
            countdown.await();
            UI.userDisconnected();
            if (UI.waitForConfirmation("Do you want to try another address?")) {
                startGame();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void requestGameInput() {
        String input = String.valueOf(UI.getGameInput());
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
        throw new UnsupportedOperationException("Should not be called in client mode");
    }
    public static void main(String[] args) {
        new ClientDeviceGameController(InputType.CLI);
    }

    public void wrongInput(InputErrorReason errorReason) {
        UI.wrongInput(errorReason);
    }
}
