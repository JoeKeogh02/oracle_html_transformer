package controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import model.answers.states.Answer;
import utils.AnswerManager;
import view.panels.AnswerPanel;
import view.panels.TabbedAnswerPanel;

/**
 * The TabbedAnswerController class is responsible for managing the tabbed answer panel
 * and coordinating the display of answers based on their state. It interacts with the
 * TabbedAnswerPanel to update the UI and the AnswerManager to retrieve and set answers.
 */
public class TabbedAnswerController {
    private TabbedAnswerPanel tabbedAnswerPanel;
    private AnswerManager answerManager;

    /**
     * Constructs a new TabbedAnswerController with the TabbedAnswerPanel and AnswerManager.
     *
     * @param tabbedAnswerPanel the panel that displays answers in tabbed format.
     * @param answerManager  the manager responsible for storing and managing answers.
     */
    public TabbedAnswerController(TabbedAnswerPanel tabbedAnswerPanel, AnswerManager answerManager) {
        this.tabbedAnswerPanel = tabbedAnswerPanel;
        this.answerManager = answerManager;
    }

    /**
     * Updates the content of each tab in the tabbed answer panel based on the current state of the answers.
     * Answers are grouped by their state (e.g., "Finished", "Updating", "Waiting", "Error"), and the corresponding
     * tab is updated with the relevant answers.
     */
    public void updateTabs() {
        List<Answer> answers = answerManager.getAnswers();
        Map<String, List<Answer>> groupedAnswers = answers.stream()
            .collect(Collectors.groupingBy(answer -> answer.getState().getName()));

        // Update the tabbed UI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            tabbedAnswerPanel.updateTabContent("All", new AnswerPanel(answers));
            tabbedAnswerPanel.updateTabContent("Finished", new AnswerPanel(groupedAnswers.getOrDefault("Finished", List.of())));
            tabbedAnswerPanel.updateTabContent("Updating", new AnswerPanel(groupedAnswers.getOrDefault("Updating", List.of())));
            tabbedAnswerPanel.updateTabContent("Waiting", new AnswerPanel(groupedAnswers.getOrDefault("Waiting", List.of())));
            tabbedAnswerPanel.updateTabContent("Error", new AnswerPanel(groupedAnswers.getOrDefault("Error", List.of())));
        });
    }

    /**
     * Sets the list of answers in the AnswerManager and refreshes the tab content.
     *
     * @param answers the list of answers to be set and displayed.
     */
    public void setAnswers(List<Answer> answers) {
        answerManager.setAnswers(answers);
        updateTabs();
    }
}

