package org.Griffty.Listeners;

/**
 * This interface defines a listener for the client disconnected event.
 * It is used to provide a way for other classes to react when a client disconnects from the server.
 */
public interface OnClientDisconnectedListener {
    /**
     * This method is called when a client disconnects from the server.
     * The classes that implement this interface should define what should happen when a client disconnects.
     */
    void onClientDisconnected();
}