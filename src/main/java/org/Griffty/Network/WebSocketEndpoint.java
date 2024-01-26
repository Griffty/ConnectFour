package org.Griffty.Network;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/server")
public class WebSocketEndpoint {
    @OnMessage
    public void OnMessage(String message){
        System.out.println("Echo: " + message);
    }
    @OnOpen
    public void OnOpen(Session session){
        System.out.println("Session opened, id: " + session.getId());
    }
    @OnClose
    public void OnClose(Session session, CloseReason closeReason){
        System.out.println("Session closed, reason: " + closeReason.getReasonPhrase() + ", id: " + session.getId());
    }
    @OnError
    public void OnError(Session session, Throwable thr){
        System.out.println("Error in session " + session.getId());
        throw new RuntimeException(thr);
    }
}
