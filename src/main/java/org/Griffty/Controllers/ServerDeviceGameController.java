package org.Griffty.Controllers;

import org.Griffty.Network.WebSocketServerEndpoint;
import org.Griffty.Network.WebSocketServer;
import org.Griffty.Listeners.OnClientDisconnectedListener;
import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.util.List;

public class ServerDeviceGameController extends AbstractGameController implements OnClientDisconnectedListener {
    private final WebSocketServer server;
    private WebSocketServerEndpoint connection;
    private List<String> IPs;
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
        IPs = WebSocketServerEndpoint.getIPs();
        UI.showConnectionInfo(IPs, server.getPort());
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
        UI.showConnectionInfo(IPs, server.getPort());
        startGame();
    }

    private void serverTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
            StatisticsHandler.getInstance().addMove();
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
        boolean serverAgain = super.playAgain();
        if (!serverAgain)
        {
            connection.disconnect();
            return true;
        }
        if (connection.requestPlayAgain()){
            return true;
        }else {
            connection.disconnect();
            return false;
        }
    }

    @Override
    public void announceWinner(int victoryStatus) {
        StatisticsHandler.getInstance().addGame(victoryStatus);
        super.announceWinner(victoryStatus);
        connection.sendWinner(victoryStatus);
    }
    @Override
    public void onClientDisconnected() {
        clientDisconnected();
    }
}
