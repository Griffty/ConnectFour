package org.Griffty.Util.Dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * This abstract class represents a custom dialog box.
 * It includes methods for setting up the dialog's settings and graphical user interface.
 * It is intended to be extended by other classes that provide more specific functionality.
 */
public abstract class CustomDialog<B extends CustomDialog.Builder> extends JDialog{
    private boolean useOptimalSize;
    private boolean canBeClosed;
    private boolean exitOnClose;
    private boolean resizeable;
    private String title;
    private Dimension size;
    protected JPanel mainPanel;
    protected boolean customInterface;

    /**
     * The constructor for CustomDialog.
     * It initializes the dialog with the settings specified by the builder.
     * @param builder the builder that specifies the settings for the dialog.
     */
    protected CustomDialog(B builder){
        super();
        parseArgs(builder);
        initGUI();
        setTitle(title);
        setSize(size);
        setResizable(resizeable);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(canBeClosed ? DISPOSE_ON_CLOSE : DO_NOTHING_ON_CLOSE);
        if (useOptimalSize) {
            pack();
        }
        setVisible(true);
    }

    /**
     * This method parses the arguments from the builder and initializes the dialog's settings.
     * @param builder the builder that specifies the settings for the dialog.
     */
    protected void parseArgs(B builder) {
        useOptimalSize = builder.useOptimalSize;
        exitOnClose = builder.exitOnClose;
        canBeClosed = builder.canBeClosed;
        resizeable = builder.resizeable;
        mainPanel = builder.mainPanel;
        title = builder.title;
        size = builder.size;
        if (mainPanel != null) {
            customInterface = true;
        }
    }

    /**
     * This method initializes the dialog's graphical user interface.
     * It sets up the window listener and adds the main panel to the dialog.
     */
    protected void initGUI() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (exitOnClose) {
                    System.exit(0);
                }
            }
        });
        if (customInterface) {
            add(mainPanel);
        }else {
            mainPanel = new JPanel();
            add(mainPanel);
        }
    }

    /**
     * This abstract class is a builder for the CustomDialog class.
     * It allows for the creation of a CustomDialog with custom settings.
     */
    public static abstract class Builder<T extends Builder<T>> {
        protected Builder() {}
        protected boolean canBeClosed = true;
        protected boolean exitOnClose = false;
        protected boolean useOptimalSize = false;
        protected boolean resizeable = false;
        protected String title = "Connect Four";
        protected Dimension size = new Dimension(400, 120);
        protected JPanel mainPanel = null;

        /**
         * This method sets the main panel of the dialog.
         * @param mainPanel the main panel to set.
         * @return the builder.
         */
        public T setMainPanel(JPanel mainPanel) {
            this.mainPanel = mainPanel;
            return self();
        }

        /**
         * This method sets whether the dialog should exit on close.
         * @param exitOnClose whether the dialog should exit on close.
         * @return the builder.
         */
        public T setExitOnClose(boolean exitOnClose) {
            this.exitOnClose = exitOnClose;
            return self();
        }

        /**
         * This method sets whether the dialog can be closed.
         * @param canBeClosed whether the dialog can be closed.
         * @return the builder.
         */
        public T setCanBeClosed(boolean canBeClosed) {
            this.canBeClosed = canBeClosed;
            return self();
        }

        /**
         * This method sets whether the dialog should use the optimal size.
         * @param useOptimalSize whether the dialog should use the optimal size.
         * @return the builder.
         */
        public T setUseOptimalSize(boolean useOptimalSize) {
            this.useOptimalSize = useOptimalSize;
            return self();
        }

        /**
         * This method sets whether the dialog is resizable.
         * @param resizeable whether the dialog is resizable.
         * @return the builder.
         */
        public T setResizeable(boolean resizeable) {
            this.resizeable = resizeable;
            return self();
        }

        /**
         * This method sets the title of the dialog.
         * @param title the title to set.
         * @return the builder.
         */
        public T setTitle(String title) {
            this.title = title;
            return self();
        }

        /**
         * This method sets the size of the dialog.
         * @param size the size to set.
         * @return the builder.
         */
        public T setSize(Dimension size) {
            this.size = size;
            return self();
        }

        /**
         * This method returns the builder.
         * It is intended to be overridden by subclasses.
         * @return the builder.
         */
        protected abstract T self();

        /**
         * This method builds and returns a CustomDialog with the settings specified by the builder.
         * It is intended to be overridden by subclasses.
         * @return a CustomDialog with the settings specified by the builder.
         */
        public abstract CustomDialog build();
    }
}