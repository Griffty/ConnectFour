package org.Griffty.Util.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents a custom dialog box for displaying information.
 * It extends the CustomDialog class and includes additional features such as a label and text formatting options.
 */
public class InfoDialog extends CustomDialog<InfoDialog.Builder> {
    protected boolean splitOnNewLine;
    protected boolean centerText;
    protected String info;
    protected Font font;

    /**
     * The constructor for InfoDialog.
     * It initializes the dialog with the settings specified by the builder.
     * @param builder the builder that specifies the settings for the dialog.
     */
    protected InfoDialog(Builder builder) {
        super(builder);
    }

    /**
     * This method parses the arguments from the builder and initializes the dialog's settings.
     * @param builder the builder that specifies the settings for the dialog.
     */
    @Override
    protected void parseArgs(Builder builder) {
        super.parseArgs(builder);
        splitOnNewLine = builder.splitOnNewLine;
        centerText = builder.centerText;
        info = builder.info;
        font = builder.font;
    }

    /**
     * This method initializes the dialog's graphical user interface.
     * It sets up the layout, creates the label, and adds the label to the main panel.
     */
    @Override
    protected void initGUI() {
        super.initGUI();
        if (customInterface) {
            return;
        }
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        if (splitOnNewLine){
            String[] split = info.split("\n");
            for (String s : split) {
                JLabel label = new JLabel(s);
                label.setFont(font);
                if (centerText) {
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                }
                mainPanel.add(label);
            }
        }else {
            JLabel label = new JLabel(info);
            label.setFont(font);
            if (centerText) {
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
            }
            mainPanel.add(label);
        }
    }

    /**
     * This class is a builder for the InfoDialog class.
     * It allows for the creation of an InfoDialog with custom settings.
     */
    public static final class Builder extends CustomDialog.Builder<Builder>{
        Builder() {
            super();
        }
        private String info = "Look's like you've found a bug!\n Please report it to the developer.";
        private Font font = new Font("Arial", Font.PLAIN, 12);
        private boolean splitOnNewLine = false;
        private boolean centerText = true;

        /**
         * This method sets the information to be displayed in the dialog.
         * @param info the information to be displayed.
         * @return the builder.
         */
        public Builder setInfo(String info) {
            this.info = info;
            return this;
        }

        /**
         * This method sets the information to be displayed in the dialog.
         * The information is provided as a list of strings, which are concatenated with newline characters.
         * @param info the information to be displayed.
         * @return the builder.
         */
        public Builder setInfo(List<String> info) {
            StringBuilder builder = new StringBuilder();
            for(String s : info){
                builder.append(s).append("\n");
            }
            this.info = builder.toString();
            return this;
        }

        /**
         * This method sets the font of the text in the dialog.
         * @param font the font to be used.
         * @return the builder.
         */
        public Builder setFont(Font font) {
            this.font = font;
            return this;
        }

        /**
         * This method sets whether the text in the dialog should be split on newline characters.
         * @param splitOnNewLine whether the text should be split on newline characters.
         * @return the builder.
         */
        public Builder setSplitOnNewLine(boolean splitOnNewLine) {
            this.splitOnNewLine = splitOnNewLine;
            return this;
        }

        /**
         * This method sets whether the text in the dialog should be centered.
         * @param centerText whether the text should be centered.
         * @return the builder.
         */
        public Builder setCenterText(boolean centerText) {
            this.centerText = centerText;
            return this;
        }

        /**
         * This method builds and returns an InfoDialog with the settings specified by the builder.
         * @return an InfoDialog with the settings specified by the builder.
         */
        @Override
        public InfoDialog build() {
            return new InfoDialog(this);
        }

        /**
         * This method returns the builder.
         * @return the builder.
         */
        @Override
        protected Builder self() {
            return this;
        }
    }
}