package org.Griffty.Util.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a custom dialog box that allows the user to make a choice from a list of options.
 * It extends the CustomDialog class and includes additional features such as a label and multiple buttons.
 */
public class ChoiceDialog extends CustomDialog<ChoiceDialog.Builder> {
    protected CompletableFuture<Integer> choice;
    protected boolean centerText;
    protected List<ChoiceOption> choiceOptions;
    protected Font buttonFont;
    protected String labelText;
    protected Font labelFont;

    /**
     * The constructor for ChoiceDialog.
     * It initializes the dialog with the settings specified by the builder.
     * @param builder the builder that specifies the settings for the dialog.
     */
    protected ChoiceDialog(Builder builder) {
        super(builder);
    }

    /**
     * This method parses the arguments from the builder and initializes the dialog's settings.
     * @param builder the builder that specifies the settings for the dialog.
     */
    @Override
    protected void parseArgs(Builder builder) {
        super.parseArgs(builder);
        choice = new CompletableFuture<>();
        centerText = builder.centerText;
        choiceOptions = builder.choiceOptions;
        buttonFont = builder.buttonFont;
        labelFont = builder.labelFont;
        labelText = builder.label;
    }

    /**
     * This method initializes the dialog's graphical user interface.
     * It sets up the layout, creates the label and buttons, and adds action listeners to the buttons.
     */
    @Override
    protected void initGUI() {
        super.initGUI();
        if (customInterface) {
            return;
        }
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        if (centerText) {
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        mainPanel.add(label);
        JSeparator line = new JSeparator(SwingConstants.HORIZONTAL);
        line.setMaximumSize(new Dimension(200, 12));
        line.setForeground(Color.BLACK);
        line.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(line);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        mainPanel.add(bottomPanel);
        for (ChoiceOption c : choiceOptions) {
            JButton button = new JButton(c.name());
            button.setFont(buttonFont);
            button.setAlignmentY(Component.CENTER_ALIGNMENT);
            button.addActionListener(e -> {
                choice.complete(c.value());
                dispose();
            });
            bottomPanel.add(button);
        }
    }

    /**
     * This method returns the choice made by the user.
     * @return the choice made by the user.
     */
    public int getChoice() {
        return choice.join();
    }

    /**
     * This class is a builder for the ChoiceDialog class.
     * It allows for the creation of a ChoiceDialog with custom settings.
     */
    public static final class Builder extends CustomDialog.Builder<Builder>{
        Builder() {
            super();
        }
        private final List<ChoiceOption> choiceOptions = new ArrayList<>();
        private Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        private Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        private String label = "Please select an option";
        private boolean centerText = true;

        /**
         * This method sets the label text for the dialog.
         * @param label the label text.
         * @return the builder.
         */
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        /**
         * This method sets the font for the label in the dialog.
         * @param labelFont the font for the label.
         * @return the builder.
         */
        public Builder setLabelFont(Font labelFont) {
            this.labelFont = labelFont;
            return this;
        }

        /**
         * This method sets the font for the buttons in the dialog.
         * @param buttonFont the font for the buttons.
         * @return the builder.
         */
        public Builder setButtonFont(Font buttonFont) {
            this.buttonFont = buttonFont;
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
         * This method adds a choice option to the dialog.
         * @param name the name of the choice option.
         * @param value the value of the choice option.
         * @return the builder.
         */
        public Builder addChoice(String name, int value) {
            choiceOptions.add(new ChoiceOption(name, value));
            return this;
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
         * This method builds and returns a ChoiceDialog with the settings specified by the builder.
         * @return a ChoiceDialog with the settings specified by the builder.
         */
        @Override
        public ChoiceDialog build() {
            return new ChoiceDialog(this);
        }
    }
}

/**
 * This record represents an option in the ChoiceDialog.
 * It includes a name and a value.
 */
record ChoiceOption(String name, int value){}