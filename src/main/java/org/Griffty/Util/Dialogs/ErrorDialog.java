package org.Griffty.Util.Dialogs;

/**
 * This class represents a custom dialog box for displaying error messages.
 * It extends the CustomDialog class and throws an UnsupportedOperationException when instantiated,
 * as it is not yet implemented.
 */
public class ErrorDialog extends CustomDialog<ErrorDialog.Builder> {

    /**
     * The constructor for ErrorDialog.
     * It initializes the dialog with the settings specified by the builder and throws an UnsupportedOperationException.
     * @param builder the builder that specifies the settings for the dialog.
     * @throws UnsupportedOperationException when an attempt is made to instantiate this class.
     */
    protected ErrorDialog(Builder builder) {
        super(builder);
        throw new UnsupportedOperationException("ErrorDialog is not yet implemented");
    }

    /**
     * This class is a builder for the ErrorDialog class.
     * It allows for the creation of an ErrorDialog with custom settings.
     */
    public static final class Builder extends CustomDialog.Builder<Builder>{

        /**
         * The constructor for Builder.
         * It initializes the builder with the default settings.
         */
        Builder() {
            super();
        }

        /**
         * This method returns the builder.
         * @return the builder.
         */
        @Override
        protected Builder self() {
            return this;
        }

        /**
         * This method builds and returns an ErrorDialog with the settings specified by the builder.
         * Currently, it returns null as the ErrorDialog class is not yet implemented.
         * @return null.
         */
        @Override
        public ErrorDialog build() {
            return null;
        }
    }
}