package org.Griffty.Util.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a custom dialog box for user input.
 * It extends the CustomDialog class and includes additional features such as multiple input fields and a submit button.
 */
public class InputDialog extends CustomDialog<InputDialog.Builder> {
    protected boolean centerText;
    protected Font labelFont;
    protected List<InputField> inputFields;
    protected HashMap<String, JTextField> inputFieldMap;
    protected CompletableFuture<HashMap<String, String>> input;

    /**
     * The constructor for InputDialog.
     * It initializes the dialog with the settings specified by the builder.
     * @param builder the builder that specifies the settings for the dialog.
     */
    protected InputDialog(Builder builder) {
        super(builder);
    }

    /**
     * This method parses the arguments from the builder and initializes the dialog's settings.
     * @param builder the builder that specifies the settings for the dialog.
     */
    @Override
    protected void parseArgs(Builder builder) {
        super.parseArgs(builder);
        centerText = builder.centerText;
        labelFont = builder.labelFont;
        inputFields = builder.inputFields;
        input = new CompletableFuture<>();
        inputFieldMap = new HashMap<>();
    }

    /**
     * This method initializes the dialog's graphical user interface.
     * It sets up the layout, creates the input fields and the submit button, and adds them to the main panel.
     */
    @Override
    protected void initGUI() {
        super.initGUI();
        if (customInterface) {
            return;
        }
        for (InputField i : inputFields) {
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            JLabel label = new JLabel(i.label());
            label.setFont(labelFont);
            if (centerText) {
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
            }
            mainPanel.add(label);
            JTextField inputField = new JTextField();
            if (i.initialValue() != null) {
                inputField.setText(i.initialValue());
            }
            inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
            inputFieldMap.put(i.label(), inputField);
            mainPanel.add(inputField);
        }
        JButton button = new JButton("OK");
        if (centerText) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        button.addActionListener(e -> {
            input.complete(getInputFieldValues());
            dispose();
        });
        mainPanel.add(button);
    }

    /**
     * This method retrieves the values from the input fields.
     * @return a HashMap containing the values from the input fields.
     */
    private HashMap<String, String> getInputFieldValues() {
        HashMap<String, String> map = new HashMap<>();
        for (Map.Entry<String, JTextField> entry : inputFieldMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getText());
        }
        return map;
    }

    /**
     * This method returns the inputs made by the user.
     * @return the inputs made by the user.
     */
    public HashMap<String, String> getInputs() {
        return input.join();
    }

    /**
     * This class is a builder for the InputDialog class.
     * It allows for the creation of an InputDialog with custom settings.
     */
    public static final class Builder extends CustomDialog.Builder<Builder> {
        Builder() {
            super();
        }
        private boolean centerText = true;
        private Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        private final List<InputField> inputFields = new ArrayList<>();

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
         * This method sets the font of the labels in the dialog.
         * @param labelFont the font to be used.
         * @return the builder.
         */
        public Builder setLabelFont(Font labelFont) {
            this.labelFont = labelFont;
            return this;
        }

        /**
         * This method adds an input field to the dialog with the specified label.
         * The initial value of the input field is null.
         * @param label the label of the input field.
         * @return the builder.
         */
        public Builder addInputField(String label) {
            inputFields.add(new InputField(label, null));
            return this;
        }

        /**
         * This method adds an input field to the dialog with the specified label and initial value.
         * @param label the label of the input field.
         * @param initialValue the initial value of the input field.
         * @return the builder.
         */
        public Builder addInputField(String label, String initialValue) {
            inputFields.add(new InputField(label, initialValue));
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
         * This method builds and returns an InputDialog with the settings specified by the builder.
         * @return an InputDialog with the settings specified by the builder.
         */
        @Override
        public InputDialog build() {
            return new InputDialog(this);
        }
    }
}

/**
 * This record represents an input field in the InputDialog.
 * It includes a label and an initial value.
 */
record InputField(String label, String initialValue){}