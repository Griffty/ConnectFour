package org.Griffty.Controllers;

import org.Griffty.Network.WebSocketServerEndpoint;
import org.Griffty.Network.WebSocketServer;
import org.Griffty.Listeners.OnClientDisconnectedListener;
import org.Griffty.Statistics.StatisticsHandler;
import org.Griffty.enums.InputErrorReason;
import org.Griffty.enums.InputType;

import java.util.List;

/**
 * This class is responsible for controlling the game when playing on a server device.
 * It extends the AbstractGameController class and implements the OnClientDisconnectedListener interface.
 */
public class ServerDeviceGameController extends AbstractGameController implements OnClientDisconnectedListener {
    private final WebSocketServer server;
    private WebSocketServerEndpoint connection;
    private List<String> IPs;

    /**
     * Constructor for the ServerDeviceGameController class.
     * @param inputType The type of user interface to use for the game.
     */
    public ServerDeviceGameController(InputType inputType) {
        super(inputType);
        server = WebSocketServer.getInstance();
        if (launchServer()){
            startGame();
        }else {
            throw new RuntimeException("Server failed to launch");
        }
    }

    /**
     * Launches the server and shows the connection info to the user.
     * @return A boolean indicating whether the server was launched successfully.
     */
    private boolean launchServer() {
        server.start();
        IPs = WebSocketServerEndpoint.getIPs();
        UI.showConnectionInfo(IPs, server.getPort());
        return true;
    }

    /**
     * Starts the game.
     * It waits for a client to connect and then starts the game.
     */
    @Override
    protected void startGame() {
        server.waitForClient();
        connection = server.getActiveConnection();
        connection.setOnClientDisconnectedListener(this);
        UI.setOnlineMode(1);
        super.startGame();
    }

    /**
     * Makes a turn in the game.
     * If it's the server's turn, it calls the serverTurn method.
     * If it's the client's turn, it calls the clientTurn method.
     */
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

    /**
     * Handles the event of a client disconnecting.
     * It stops the game and then starts it again.
     */
    public void clientDisconnected() {
        UI.userDisconnected();
        stopGame();
        forceStop = false;
        UI.showConnectionInfo(IPs, server.getPort());
        startGame();
    }

    /**
     * Makes a turn for the server.
     * It gets the server's input and tries to make a move.
     * If the move is not valid, it asks for the server's input again.
     */
    private void serverTurn() {
        int col = UI.getGameInput();
        while (!board.putToken(col, currentTurn)){
            UI.wrongInput(InputErrorReason.COLUMN_FULL);
            col = UI.getGameInput();
            StatisticsHandler.getInstance().addMove();
        }
    }

    /**
     * Makes a turn for the client.
     * It requests the client's input and tries to make a move.
     * If the move is not valid, it asks for the client's input again.
     */
    private void clientTurn() {
        int col = connection.requestGameInput();
        while (!board.putToken(col, currentTurn)){
            connection.wrongInput(InputErrorReason.COLUMN_FULL);
            col = connection.requestGameInput();
        }
    }

    /**
     * Updates the user interface and sends the updated interface to the client.
     * @param cells The current state of the game board.
     * @param currentTurn The current turn in the game.
     */
    @Override
    public void updateUI(int[][] cells, int currentTurn) {
        super.updateUI(cells, currentTurn);
        connection.sendUI(cells, currentTurn);
    }

    /**
     * Updates the game board and sends the updated board to the client.
     * @param cells The current state of the game board.
     */
    @Override
    public void updateBoard(int[][] cells) {
        super.updateBoard(cells);
        connection.sendBoard(board);
    }

    /**
     * Asks the server if they want to play again and sends their decision to the client.
     * @return A boolean indicating whether the server wants to play again.
     */
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

    /**
     * Announces the winner of the game and sends the winner to the client.
     * @param victoryStatus The status of the victory.
     */
    @Override
    public void announceWinner(int victoryStatus) {
        StatisticsHandler.getInstance().addGame(victoryStatus);
        super.announceWinner(victoryStatus);
        connection.sendWinner(victoryStatus);
    }

    /**
     * Handles the event of a client disconnecting.
     */
    @Override
    public void onClientDisconnected() {
        clientDisconnected();
    }
}