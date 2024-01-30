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

@ServerEndpoint("/server")
public class WebSocketServerEndpoint {
    private OnClientDisconnectedListener listener;

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

    public void setOnClientDisconnectedListener(OnClientDisconnectedListener listener) {
        this.listener = listener;
    }
    private Session serverSession;
    private CompletableFuture<Integer> gameInput;
    private CompletableFuture<Boolean> playAgain;
    @OnMessage
    public void OnMessage(String message){
        String[] split = message.split(":");
        if (split[0].equals("gameInput")){
            gameInput.complete(Integer.parseInt(split[1]));
        }else if (split[0].equals("playAgain")) {
            playAgain.complete(Boolean.parseBoolean(split[1]));
        }
    }
    @OnOpen
    public void OnOpen(Session session){
        WebSocketServer.getInstance().setActiveConnection(this);
        this.serverSession = session;
    }
    @OnClose
    public void OnClose(Session session, CloseReason closeReason){
        WebSocketServer.getInstance().clearActiveConnection();
        listener.onClientDisconnected();
    }
    @OnError
    public void OnError(Session session, Throwable thr){
        throw new RuntimeException(thr);
    }

    public int requestGameInput() {
        serverSession.getAsyncRemote().sendText("requestGameInput");
        gameInput = new CompletableFuture<>();
        return gameInput.join();
    }

    public boolean requestPlayAgain() {
        serverSession.getAsyncRemote().sendText("requestPlayAgain");
        playAgain = new CompletableFuture<>();
        return playAgain.join();
    }

    public void wrongInput(InputErrorReason inputErrorReason) {
        serverSession.getAsyncRemote().sendText("wrongInput:" + inputErrorReason);
    }

    public void sendUI(int[][] cells, int currentTurn) {
        serverSession.getAsyncRemote().sendText("updateUI:" + currentTurn + ":" + cellsToString(cells));
    }


    public void sendBoard(Board board) {
        serverSession.getAsyncRemote().sendText("updateBoard:" + cellsToString(board.getCells()));
    }


    public void sendWinner(int victoryStatus) {
        serverSession.getAsyncRemote().sendText("announceWinner:" + victoryStatus);
    }

    public void disconnect() {
        try {
            serverSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game ended"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
