package org.Griffty.enums;

/**
 * This enum represents the different reasons why a user's input might be invalid.
 * It is used to provide feedback to the user when they make an invalid input.
 */
public enum InputErrorReason {
    /**
     * The column number entered by the user is out of bounds.
     */
    COL_OUT_OF_BOUNDS,

    /**
     * The column entered by the user is already full.
     */
    COLUMN_FULL,

    /**
     * The input entered by the user is not a number.
     */
    NOT_A_NUMBER,

    /**
     * The input entered by the user is not a confirmation (yes or no).
     */
    NOT_A_CONFIRMATION,

    /**
     * The server credentials entered by the user are incorrect.
     */
    WRONG_SERVER_CREDENTIALS,

    /**
     * The difficulty level entered by the user is out of bounds.
     */
    DIF_OUT_OF_BOUNDS,

    /**
     * The input entered by the user is not recognized.
     */
    WRONG_INPUT
}