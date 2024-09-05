package view.dialog;

import javax.swing.*;

import model.operations.Operation;
import model.rules.HtmlTransformationRule;
import model.rules.TargetConfig;
import view.dialog.callbacks.RuleChangeCallback;
import view.interfaces.OperationPanel;
import view.panels.DeleteOperationPanel;
import view.panels.ReplaceOperationPanel;

import java.awt.*;

import java.util.*;
import java.util.stream.Collectors;

public class TransformationRuleDialog extends JDialog {
    private static final long serialVersionUID = -8176858235148453139L;

    private JTextField searchPhraseField;
    private JComboBox<String> operationComboBox;
    private JPanel operationDetailsPanel;
    private JButton addButton, addAnotherButton, cancelButton;

    private JCheckBox textTargetCheckBox, attributesTargetCheckBox, tagsTargetCheckBox;
    private JTextField attributesSpecificField, tagsSpecificField; // No textSpecificField, since text can't have specifics

    private HtmlTransformationRule existingRule;
    private RuleChangeCallback callback;

    public TransformationRuleDialog(Frame owner, HtmlTransformationRule existingRule, RuleChangeCallback callback) {
        super(owner, "Add Transformation Rule", true);
        this.existingRule = existingRule;
        this.callback = callback;

        setSize(350, 400);
        setLayout(new BorderLayout());

        // Panel with padding for the input components
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the inputPanel

        searchPhraseField = new JTextField();
        operationComboBox = new JComboBox<>(new String[]{"Replace", "Delete"});
        operationDetailsPanel = new JPanel(new CardLayout());
        operationDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add vertical padding between components

        operationDetailsPanel.add(new ReplaceOperationPanel(), "Replace");
        operationDetailsPanel.add(new DeleteOperationPanel(), "Delete");

        JPanel gridPanel = new JPanel(new GridLayout(2, 1, 0, 10)); // Vertical padding between rows
        gridPanel.add(new JLabel("Search Phrase:"));
        gridPanel.add(searchPhraseField);
        gridPanel.add(new JLabel("Operation:"));
        gridPanel.add(operationComboBox);

        inputPanel.add(gridPanel, BorderLayout.NORTH);
        inputPanel.add(operationDetailsPanel, BorderLayout.CENTER);

        // Target Type Checkboxes and Specific Fields (without text input for textTargetCheckBox)
        JPanel targetConfigPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // Layout for checkboxes and specific fields
        targetConfigPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding around target config

        // Add checkboxes and specific fields for attributes and tags target types
        textTargetCheckBox = new JCheckBox("Target Text");
        attributesTargetCheckBox = new JCheckBox("Target Attributes");
        tagsTargetCheckBox = new JCheckBox("Target Tags");

        // Only attributes and tags will have input fields for specific targets
        attributesSpecificField = new JTextField();
        tagsSpecificField = new JTextField();

        // Add checkboxes for each target type
        targetConfigPanel.add(textTargetCheckBox); // No input for text target
        targetConfigPanel.add(new JLabel()); // Placeholder to align grid

        targetConfigPanel.add(attributesTargetCheckBox);
        targetConfigPanel.add(attributesSpecificField);

        targetConfigPanel.add(tagsTargetCheckBox);
        targetConfigPanel.add(tagsSpecificField);

        inputPanel.add(targetConfigPanel, BorderLayout.SOUTH);

        setupInputs();

        addButton = new JButton(existingRule == null ? "Add" : "Update");
        addAnotherButton = new JButton("Add Another");
        cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add vertical padding around the button panel
        buttonPanel.add(addButton);
        if (existingRule == null) {
            buttonPanel.add(addAnotherButton);
        }
        buttonPanel.add(cancelButton);

        getRootPane().setDefaultButton(addButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setupActions();
    }

    private void setupInputs() {
        if (existingRule == null) return;

        searchPhraseField.setText(existingRule.getSearchPhrase());
        operationComboBox.setSelectedItem(existingRule.getOperation().getClass().getSimpleName());
    }

    private void setupActions() {
        addButton.addActionListener(e -> {
            boolean complete = dialogComplete();
            if (!complete) return;
            dispose();
        });

        addAnotherButton.addActionListener(e -> {
            boolean complete = dialogComplete();
            if (!complete) return;
            searchPhraseField.setText("");
            operationComboBox.setSelectedIndex(0);
            updateOperationDetails();
        });

        cancelButton.addActionListener(e -> dispose());
        operationComboBox.addActionListener(e -> updateOperationDetails());
    }

    private Boolean dialogComplete() {
        String searchPhrase = searchPhraseField.getText();
        if (searchPhrase.isEmpty()) return false;

        // Get the selected operation from the operation panel
        OperationPanel currentPanel = (OperationPanel) operationDetailsPanel.getComponent(operationComboBox.getSelectedIndex());
        Operation<String> operation = currentPanel.getOperation(searchPhrase);
        if (operation == null) return false;

        // Build the TargetConfig based on checkboxes and specific inputs
        EnumSet<TargetConfig.TargetType> targetTypes = EnumSet.noneOf(TargetConfig.TargetType.class);
        Map<TargetConfig.TargetType, Set<String>> specificTargets = new HashMap<>();

        if (textTargetCheckBox.isSelected()) {
            targetTypes.add(TargetConfig.TargetType.TEXT);
        }
        if (attributesTargetCheckBox.isSelected()) {
            targetTypes.add(TargetConfig.TargetType.ATTRIBUTES);
            Set<String> specifics = parseSpecificInput(attributesSpecificField.getText());
            specificTargets.put(TargetConfig.TargetType.ATTRIBUTES, specifics);
        }
        if (tagsTargetCheckBox.isSelected()) {
            targetTypes.add(TargetConfig.TargetType.TAGS);
            Set<String> specifics = parseSpecificInput(tagsSpecificField.getText());
            specificTargets.put(TargetConfig.TargetType.TAGS, specifics);
        }

        TargetConfig targetConfig = new TargetConfig(targetTypes, specificTargets);

        // Create new HtmlTransformationRule
        HtmlTransformationRule newRule = new HtmlTransformationRule(searchPhrase, operation, targetConfig);

        if (existingRule != null) {
            callback.onCompleted(existingRule, newRule);
        } else {
            callback.onCompleted(null, newRule);
        }

        return true;
    }

    // Helper method to parse comma-separated input into a Set<String>
    private Set<String> parseSpecificInput(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim) // Trim whitespace
                .filter(s -> !s.isEmpty()) // Filter out empty strings
                .collect(Collectors.toSet());
    }

    private void updateOperationDetails() {
        CardLayout cl = (CardLayout) (operationDetailsPanel.getLayout());
        cl.show(operationDetailsPanel, (String) operationComboBox.getSelectedItem());
    }
}

