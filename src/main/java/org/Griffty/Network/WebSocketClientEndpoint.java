package org.Griffty.Network;

import jakarta.websocket.*;
import org.Griffty.Board;
import org.Griffty.Controllers.ClientDeviceGameController;
import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.enums.InputErrorReason;

import java.io.IOException;
import java.net.URI;
/**
 * This class represents a WebSocket client endpoint.
 * It is used to communicate with the server and handle incoming messages.
 */
@ClientEndpoint
public class WebSocketClientEndpoint {
    private Session userSession = null;
    private ClientDeviceGameController controller;
    /**
     * This method is called when a message is received from the server.
     * It handles the message based on its type.
     * @param message The message received from the server.
     */
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
                StatisticsHandler.getInstance().addGame(victoryStatus);
                controller.announceWinner(victoryStatus);
            }
        }
    }
    /**
     * This method is called when the WebSocket session is closed.
     * It triggers the countdown in the controller.
     * @param session The WebSocket session that was closed.
     * @param closeReason The reason why the session was closed.
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        controller.countdown.countDown();
    }
    /**
     * This method is called when an error occurs in the WebSocket session.
     * It throws a RuntimeException with the error.
     * @param session The WebSocket session where the error occurred.
     * @param throwable The error that occurred.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new RuntimeException(throwable);
    }
    /**
     * This method is used to send a response to the server.
     * @param response The response to send to the server.
     */
    public void respond(String response) {
        userSession.getAsyncRemote().sendText(response);
    }
    /**
     * This method is used to connect to the server.
     * It creates a new WebSocketClientEndpoint, connects it to the server, and returns it.
     * @param controller The controller to use for the WebSocketClientEndpoint.
     * @param endpointURI The URI of the server to connect to.
     * @return The connected WebSocketClientEndpoint.
     */
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