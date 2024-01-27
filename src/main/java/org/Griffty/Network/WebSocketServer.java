package org.Griffty.Network;

import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

public class WebSocketServer {
    public static final int PORT = 1301;
    private static WebSocketServer instance;
    public static WebSocketServer getInstance() {
        if (instance == null){
            instance = new WebSocketServer();
        }
        return instance;
    }
    private WebSocketServerEndpoint activeConnection;
    private final Server server;
    WebSocketServer(){
        server = new Server("localhost",  PORT, "/connect-four", null, WebSocketServerEndpoint.class); //todo: add logging for each Configuration added
        try {
            server.start();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        }
    }
    public void start(){
        if (server != null){
            try {
                server.start();
            } catch (DeploymentException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void stop(){
        if (server != null){
            server.stop();
        }
    }

    public WebSocketServerEndpoint getActiveConnection() {
        return activeConnection;
    }

    public void setActiveConnection(WebSocketServerEndpoint activeConnection) {
        if (this.activeConnection != null){
            throw new RuntimeException("Connection is already occupied");
        }
        this.activeConnection = activeConnection;
    }
    public void clearActiveConnection(){
        activeConnection = null;
    }

    public int getPort() {
        return PORT;
    }

    public void waitForClient() {
        while (activeConnection == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
