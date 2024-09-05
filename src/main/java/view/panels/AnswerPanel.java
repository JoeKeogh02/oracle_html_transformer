package view.panels;

import javax.swing.*;

import model.answers.states.Answer;

import java.awt.*;
import java.util.List;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.jsoup.nodes.Document;

public class AnswerPanel extends JPanel {
    private static final long serialVersionUID = 4671537484607662346L;
    private static final int COMPONENT_HEIGHT = 50; // Fixed height for each component

    public AnswerPanel(List<Answer> answers) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for the entire panel
        setBackground(Color.DARK_GRAY); // Set background color for the entire panel

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.DARK_GRAY);

        for (Answer answer : answers) {
            contentPanel.add(createAnswerComponent(answer));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Enable vertical scrolling as needed

        add(scrollPane, BorderLayout.CENTER);
    }

    private JComponent createAnswerComponent(Answer answer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, COMPONENT_HEIGHT)); // Extend across available width
        panel.setBackground(Color.DARK_GRAY); // Background color for each answer component

        // Create ID label panel (left)
        JPanel idPanel = new JPanel();
        idPanel.setPreferredSize(new Dimension(150, COMPONENT_HEIGHT)); // Set fixed height
        idPanel.setBackground(Color.DARK_GRAY); // Matches the panel's background
        idPanel.setLayout(new BorderLayout()); // Use BorderLayout to center the label
        JLabel idLabel = new JLabel(String.valueOf(answer.getId()), SwingConstants.CENTER);
        idLabel.setForeground(Color.WHITE); // Set id text color for better visibility
        idPanel.add(idLabel, BorderLayout.CENTER);

        // Create state panel (right)
        JPanel statePanel = new JPanel();
        statePanel.setPreferredSize(new Dimension(150, COMPONENT_HEIGHT)); // Set fixed height
        statePanel.setBackground(answer.getState().getColour()); // State's background color

        // Set tooltip text to display the state description
        statePanel.setToolTipText(answer.getState().getDescription());

        statePanel.setLayout(new BorderLayout()); // Use BorderLayout to center the label
        JLabel stateLabel = new JLabel(answer.getState().getName(), SwingConstants.CENTER);
        stateLabel.setForeground(Color.DARK_GRAY.darker()); // Dark color for text (grey black)
        statePanel.add(stateLabel, BorderLayout.CENTER);

        // Add click listener to the statePanel
        statePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openStateDocumentDialog(answer.getState().getDocument());
            }
        });

        // Add id and state panels to the main panel
        panel.add(idPanel, BorderLayout.WEST);
        panel.add(statePanel, BorderLayout.EAST);

        return panel;
    }

    // Method to open a dialog displaying the state's document content or an empty string if null
    private void openStateDocumentDialog(Document doc) {
        String content = (doc != null) ? doc.toString() : "";
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false); // Make the text area non editable
        JScrollPane scrollPane = new JScrollPane(textArea); // Add scrollable functionality

        // Show the dialog
        JOptionPane.showMessageDialog(this, scrollPane, "State Document", JOptionPane.INFORMATION_MESSAGE);
    }
}






