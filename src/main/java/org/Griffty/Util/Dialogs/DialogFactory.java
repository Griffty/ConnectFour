package org.Griffty.Util.Dialogs;

/**
 * This class serves as a factory for creating different types of dialog boxes.
 * It provides static methods for creating builders for each type of dialog box.
 */
public class DialogFactory {
    /**
     * Returns a new builder for creating an InfoDialog.
     * @return a new InfoDialog.Builder.
     */
    public static InfoDialog.Builder getInfoBuilder() {
        return new InfoDialog.Builder();
    }

    /**
     * Returns a new builder for creating a ChoiceDialog.
     * @return a new ChoiceDialog.Builder.
     */
    public static ChoiceDialog.Builder getChoiceBuilder() {
        return new ChoiceDialog.Builder();
    }

    /**
     * Returns a new builder for creating an InputDialog.
     * @return a new InputDialog.Builder.
     */
    public static InputDialog.Builder getInputBuilder() {
        return new InputDialog.Builder();
    }

    /**
     * Returns a new builder for creating an ErrorDialog.
     * @return a new ErrorDialog.Builder.
     */
    public static ErrorDialog.Builder getErrorBuilder() {
        return new ErrorDialog.Builder();
    }
}