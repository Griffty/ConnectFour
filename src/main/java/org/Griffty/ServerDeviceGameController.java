package org.Griffty;

import org.Griffty.Network.WebSocketServerEndpoint;
import org.Griffty.Network.WebSocketServer;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerDeviceGameController extends AbstractGameController implements OnClientDisconnectedListener{
    private final WebSocketServer server;
    private WebSocketServerEndpoint connection;
    private String ip;
    public ServerDeviceGameController(InputType inputType) {
        super(inputType);
        server = WebSocketServer.getInstance();
        if (launchServer()){
            startGame();
        }else {
            throw new RuntimeException("Server failed to launch");
        }
    }

    private boolean launchServer() {
        server.start();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        UI.showConnectionInfo(ip, server.getPort());
        return true;
    }

    @Override
    protected void startGame() {
        server.waitForClient();
        connection = server.getActiveConnection();
        connection.setOnClientDisconnectedListener(this);
        UI.setOnlineMode(1);
        super.startGame();
    }

    @Override
    protected void makeTurn() {
        connection = server.getActiveConnection();
        if (connection == null){
            clientDisconnected();
        }
        if (currentTurn == 1) {
            serverTurn();
        }else {
            clientTurn();
        }
    }

    public void clientDisconnected() {
        UI.userDisconnected();
        stopGame();
        forceStop = false;
        UI.showConnectionInfo(ip, server.getPort());
        startGame();
    }

    private void serverTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
        }
    }
    private void clientTurn() {
        int col = connection.requestGameInput();
        while (!board.putToken(col, currentTurn)){
            connection.wrongInput(InputErrorReason.COLUMN_FULL);
            col = connection.requestGameInput();
        }
    }

    @Override
    public void updateUI(int[][] cells, int currentTurn) {
        super.updateUI(cells, currentTurn);
        connection.sendUI(cells, currentTurn);
    }

    @Override
    public void updateBoard(int[][] cells) {
        super.updateBoard(cells);
        connection.sendBoard(board);
    }

    @Override
    public boolean playAgain() {
        return super.playAgain() && connection.requestPlayAgain();
    }

    @Override
    public void announceWinner(int victoryStatus) {
        super.announceWinner(victoryStatus);
        connection.sendWinner(victoryStatus);
    }

    public static void main(String[] args) {
        new ServerDeviceGameController(InputType.CLI);
    }

    @Override
    public void onClientDisconnected() {
        clientDisconnected();
    }
}
