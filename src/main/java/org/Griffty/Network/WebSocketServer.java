package org.Griffty.Network;

import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

/**
 * This class represents a WebSocket server.
 * It is used to manage the WebSocket connections and handle the server-side logic.
 */
public class WebSocketServer {
    public static final int PORT = 1301;
    private static WebSocketServer instance;

    /**
     * This method is used to get the instance of the WebSocketServer.
     * If the instance does not exist, it creates a new one.
     * @return The instance of the WebSocketServer.
     */
    public static WebSocketServer getInstance() {
        if (instance == null){
            instance = new WebSocketServer();
        }
        return instance;
    }

    private WebSocketServerEndpoint activeConnection;
    private final Server server;

    /**
     * This is the constructor of the WebSocketServer.
     * It creates a new Server and starts it.
     */
    private WebSocketServer(){
        server = new Server("localhost",  PORT, "/connect-four", null, WebSocketServerEndpoint.class);
        try {
            server.start();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to start the server.
     * If the server is not null, it tries to start it.
     */
    public void start(){
        if (server != null){
            try {
                server.start();
            } catch (DeploymentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method is used to get the active connection of the server.
     * @return The active connection of the server.
     */
    public WebSocketServerEndpoint getActiveConnection() {
        return activeConnection;
    }

    /**
     * This method is used to set the active connection of the server.
     * If the active connection is not null, it throws a RuntimeException.
     * @param activeConnection The active connection to set.
     */
    public synchronized void setActiveConnection(WebSocketServerEndpoint activeConnection) {
        if (this.activeConnection != null){
            throw new RuntimeException("Connection is already occupied");
        }
        this.activeConnection = activeConnection;
        notifyAll();
    }

    /**
     * This method is used to clear the active connection of the server.
     */
    public void clearActiveConnection(){
        activeConnection = null;
    }

    /**
     * This method is used to get the port of the server.
     * @return The port of the server.
     */
    public int getPort() {
        return PORT;
    }

    /**
     * This method is used to wait for a client to connect.
     * If the active connection is null, it waits.
     */
    public synchronized void waitForClient() {
        while (activeConnection == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}