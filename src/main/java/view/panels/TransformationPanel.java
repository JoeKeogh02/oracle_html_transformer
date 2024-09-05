package view.panels;

import javax.swing.*;

import model.rules.HtmlTransformationRule;

import java.awt.*;
import java.util.List;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.beans.PropertyChangeSupport;

public class TransformationPanel extends JPanel {
    private static final long serialVersionUID = -1923681188864656366L;
    private JPanel rulesContainer;
    private PropertyChangeSupport support;

    public TransformationPanel() {
        super(new BorderLayout());
        
        // Create a title label with some padding
        JLabel titleLabel = new JLabel("Transformation Rules");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0)); // Add padding to the left
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f)); // Set font size and bold style
        
        // Add the title label to the top of the panel
        add(titleLabel, BorderLayout.NORTH);

        rulesContainer = new JPanel();
        rulesContainer.setLayout(new BoxLayout(rulesContainer, BoxLayout.Y_AXIS));
        rulesContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // No extra border or padding

        JScrollPane scrollPane = new JScrollPane(rulesContainer);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling
        add(scrollPane, BorderLayout.CENTER);
        
        support = new PropertyChangeSupport(this);
    }

    public void updateRules(List<HtmlTransformationRule> rules) {
        rulesContainer.removeAll();
        rules.forEach(rule -> rulesContainer.add(createRulePanel(rule)));
        rulesContainer.revalidate();
        rulesContainer.repaint();
    }

    public void addPropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    private JPanel createRulePanel(HtmlTransformationRule rule) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 2, 2, 0));

        // Phrase and Operation labels with dynamic sizing
        JLabel phraseLabel = new JLabel("Phrase: " + rule.getSearchPhrase());
        phraseLabel.setOpaque(true);
        phraseLabel.setBackground(new Color(60, 63, 65)); // Dark Grey
        phraseLabel.setForeground(Color.WHITE);

        JLabel operationLabel = new JLabel("Operation: " + rule.getOperation().getName());
        operationLabel.setOpaque(true);
        operationLabel.setBackground(new Color(75, 75, 78)); // Slightly lighter Dark Grey
        operationLabel.setForeground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(phraseLabel);
        contentPanel.add(operationLabel);

        // Button panel on the right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        // Fix panel size to ensure no gaps
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                truncateLabelWidth(panel, phraseLabel, operationLabel, buttonPanel);
            }
        });


        editButton.addActionListener(e -> onEdit(rule));
        deleteButton.addActionListener(e -> onDelete(rule));

        return panel;
    }

    private void truncateLabelWidth(JPanel container, JLabel phraseLabel, JLabel operationLabel, JPanel buttonPanel) {
        int containerWidth = container.getWidth();
        int buttonPanelWidth = buttonPanel.getPreferredSize().width;
        int availableWidth = containerWidth - buttonPanelWidth - 30; // Subtracting some padding

        adjustLabelTextToFit(phraseLabel, availableWidth);
        adjustLabelTextToFit(operationLabel, availableWidth);
    }

    private void adjustLabelTextToFit(JLabel label, int availableWidth) {
        FontMetrics fm = label.getFontMetrics(label.getFont());
        String text = label.getText();
        if (fm.stringWidth(text) > availableWidth) {
            StringBuilder truncatedText = new StringBuilder();
            int i = 0;
            while (i < text.length() && fm.stringWidth(truncatedText.toString() + text.charAt(i) + "...") < availableWidth) {
                truncatedText.append(text.charAt(i));
                i++;
            }
            label.setText(truncatedText.toString() + "...");
        }
    }

    private void onEdit(HtmlTransformationRule rule) {
        support.firePropertyChange("editPopUpRule", null, rule);
    }

    private void onDelete(HtmlTransformationRule rule) {
        support.firePropertyChange("deleteRule", null, rule);
    }
}

