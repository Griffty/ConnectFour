package org.Griffty.Listeners;

/**
 * This interface defines a listener for the back button press event.
 * It is used to provide a way for other classes to react when the back button is pressed.
 */
public interface BackButtonPressedListener {
    /**
     * This method is called when the back button is pressed.
     * The classes that implement this interface should define what should happen when the back button is pressed.
     */
    void backButtonPressed();
}