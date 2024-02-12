package org.Griffty.Network;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.Griffty.Board;
import org.Griffty.Listeners.OnClientDisconnectedListener;
import org.Griffty.enums.InputErrorReason;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.Griffty.Board.cellsToString;

/**
 * This class represents a WebSocket server endpoint.
 * It is used to manage the WebSocket connections and handle the server-side logic.
 */
@ServerEndpoint("/server")
public class WebSocketServerEndpoint {
    private OnClientDisconnectedListener listener;

    /**
     * This method is used to get the IP addresses of the network interfaces.
     * @return A list of IP addresses.
     */
    public static List<String> getIPs() {
        List<String> IPs = new ArrayList<>();
        try {
            NetworkInterface.getNetworkInterfaces().asIterator().forEachRemaining(networkInterface -> {
                List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                interfaceAddresses.forEach(interfaceAddress -> {
                    InetAddress address = interfaceAddress.getAddress();
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()){
                        IPs.add(address.getHostAddress());
                    }
                });
            });
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return IPs;
    }

    /**
     * This method is used to set the listener for the client disconnected event.
     * @param listener The listener to set.
     */
    public void setOnClientDisconnectedListener(OnClientDisconnectedListener listener) {
        this.listener = listener;
    }
    private Session serverSession;
    private CompletableFuture<Integer> gameInput;
    private CompletableFuture<Boolean> playAgain;

    /**
     * This method is called when a message is received from the client.
     * It handles the message based on its type.
     * @param message The message received from the client.
     */
    @OnMessage
    public void OnMessage(String message){
        String[] split = message.split(":");
        if (split[0].equals("gameInput")){
            gameInput.complete(Integer.parseInt(split[1]));
        }else if (split[0].equals("playAgain")) {
            playAgain.complete(Boolean.parseBoolean(split[1]));
        }
    }

    /**
     * This method is called when the WebSocket session is opened.
     * It sets the active connection of the server and the server session.
     * @param session The WebSocket session that was opened.
     */
    @OnOpen
    public void OnOpen(Session session){
        WebSocketServer.getInstance().setActiveConnection(this);
        this.serverSession = session;
    }

    /**
     * This method is called when the WebSocket session is closed.
     * It clears the active connection of the server and calls the client disconnected listener.
     * @param session The WebSocket session that was closed.
     * @param closeReason The reason why the session was closed.
     */
    @OnClose
    public void OnClose(Session session, CloseReason closeReason){
        WebSocketServer.getInstance().clearActiveConnection();
        listener.onClientDisconnected();
    }

    /**
     * This method is called when an error occurs in the WebSocket session.
     * It throws a RuntimeException with the error.
     * @param session The WebSocket session where the error occurred.
     * @param thr The error that occurred.
     */
    @OnError
    public void OnError(Session session, Throwable thr){
        throw new RuntimeException(thr);
    }

    /**
     * This method is used to request game input from the client.
     * @return The game input received from the client.
     */
    public int requestGameInput() {
        serverSession.getAsyncRemote().sendText("requestGameInput");
        gameInput = new CompletableFuture<>();
        return gameInput.join();
    }

    /**
     * This method is used to request the client to play again.
     * @return The response from the client.
     */
    public boolean requestPlayAgain() {
        serverSession.getAsyncRemote().sendText("requestPlayAgain");
        playAgain = new CompletableFuture<>();
        return playAgain.join();
    }

    /**
     * This method is used to send an error message to the client when the input is wrong.
     * @param inputErrorReason The reason why the input is wrong.
     */
    public void wrongInput(InputErrorReason inputErrorReason) {
        serverSession.getAsyncRemote().sendText("wrongInput:" + inputErrorReason);
    }

    /**
     * This method is used to send the UI to the client.
     * @param cells The cells of the game.
     * @param currentTurn The current turn of the game.
     */
    public void sendUI(int[][] cells, int currentTurn) {
        serverSession.getAsyncRemote().sendText("updateUI:" + currentTurn + ":" + cellsToString(cells));
    }

    /**
     * This method is used to send the board to the client.
     * @param board The board of the game.
     */
    public void sendBoard(Board board) {
        serverSession.getAsyncRemote().sendText("updateBoard:" + cellsToString(board.getCells()));
    }

    /**
     * This method is used to send the winner of the game to the client.
     * @param victoryStatus The status of the victory.
     */
    public void sendWinner(int victoryStatus) {
        serverSession.getAsyncRemote().sendText("announceWinner:" + victoryStatus);
    }

    /**
     * This method is used to disconnect the server session.
     */
    public void disconnect() {
        try {
            serverSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game ended"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
