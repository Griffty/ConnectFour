package org.Griffty.enums;

/**
 * This enum represents the different types of user interfaces that can be used for the game.
 * It is used to determine how the game should interact with the user.
 */
public enum InputType {
    /**
     * No user interface is used.
     */
    None,

    /**
     * The game interacts with the user through the command line interface.
     */
    CLI,

    /**
     * The game interacts with the user through a graphical user interface.
     */
    GUI,
}