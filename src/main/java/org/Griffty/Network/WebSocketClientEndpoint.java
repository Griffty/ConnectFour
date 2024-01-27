package org.Griffty.Network;

import jakarta.websocket.*;
import org.Griffty.Board;
import org.Griffty.ClientDeviceGameController;
import org.Griffty.enums.InputErrorReason;

import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class WebSocketClientEndpoint {
    private Session userSession = null;
    private ClientDeviceGameController controller;
    @OnMessage
    public void onMessage(String message) {
        String[] split = message.split(":");
        switch (split[0]) {
            case "requestGameInput" -> controller.requestGameInput();
            case "requestPlayAgain" -> controller.playAgain();
            case "wrongInput" -> {
                InputErrorReason errorReason = InputErrorReason.valueOf(message.split(":")[1]);
                controller.wrongInput(errorReason);
            }
            case "updateUI" -> {
                int currentTurn = Integer.parseInt(split[1]);
                int[][] cells = Board.StringToCells(split[2]);
                controller.updateUI(cells, currentTurn);
            }
            case "updateBoard" -> {
                int[][] cells = Board.StringToCells(split[1]);
                controller.updateBoard(cells);
            }
            case "announceWinner" -> {
                int victoryStatus = Integer.parseInt(split[1]);
                controller.announceWinner(victoryStatus);
            }
        }
    }
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Connection closed, reason: " + closeReason.getReasonPhrase()); //todo: inform user
        controller.countdown.countDown();
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
        throw new RuntimeException(throwable);
    }
    public void respond(String response) {
        userSession.getAsyncRemote().sendText(response);
    }
    public static WebSocketClientEndpoint connectToServer(ClientDeviceGameController controller, URI endpointURI) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            WebSocketClientEndpoint client = new WebSocketClientEndpoint();
            client.userSession = container.connectToServer(client, endpointURI);
            client.controller = controller;
            return client;
        } catch (DeploymentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}