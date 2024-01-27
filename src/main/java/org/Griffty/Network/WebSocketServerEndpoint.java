package org.Griffty.Network;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.Griffty.Board;
import org.Griffty.OnClientDisconnectedListener;
import org.Griffty.enums.InputErrorReason;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static org.Griffty.Board.cellsToString;

@ServerEndpoint("/server")
public class WebSocketServerEndpoint {
    private OnClientDisconnectedListener listener;

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
        System.out.println("Session opened, id: " + session.getId());
    }
    @OnClose
    public void OnClose(Session session, CloseReason closeReason){
        WebSocketServer.getInstance().clearActiveConnection();
        listener.onClientDisconnected();
        System.out.println("Session closed, reason: " + closeReason.getReasonPhrase() + ", id: " + session.getId());
    }
    @OnError
    public void OnError(Session session, Throwable thr){
        System.out.println("Error in session " + session.getId());
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
}
