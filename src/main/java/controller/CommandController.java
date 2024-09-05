package controller;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mashape.unirest.http.exceptions.UnirestException;

import data.repositories.IOracleRepository;
import model.answers.states.Answer;
import model.answers.states.CompleteAnswerState;
import model.answers.states.ErrorAnswerState;
import model.answers.states.LoadingAnswerState;
import model.answers.states.UpdatingAnswerState;
import model.network.OracleResponse;
import model.rules.HtmlTransformationRule;
import utils.AnswerManager;
import utils.DocumentParser;
import utils.HtmlRuleProcessor;
import utils.HtmlTransformationRuleManager;
import view.panels.ControlPanel;

import javax.swing.*;


/**
 * The {@code CommandController} class orchestrates the interactions between various controllers and services,
 * handling tasks like processing answers, updating the GUI, and managing user interactions.
 */
public class CommandController {
    final private ControlPanel controlPanel;
    final private IOracleRepository oracleRepository;
    final private TabbedAnswerController tabbedAnswerController;
    final private TransformationRuleController ruleController;
    final private LoginController loginController;
    final private NetworkConfigController networkConfigController;
    final private AnswerManager answerManager;
    final private HtmlTransformationRuleManager ruleManager;

    /**
     * Constructs a {@code CommandController} with the required dependencies.
     *
     * @param controlPanel            The control panel to interact with.
     * @param tabbedAnswerController  The controller for managing the tabbed answer view.
     * @param ruleController          The controller for managing transformation rules.
     * @param loginController         The controller for user login interactions.
     * @param networkConfigController The controller for network configuration interactions.
     * @param oracleRepository        The repository to fetch and update Oracle answers.
     * @param answerManager           The manager to handle answers.
     * @param ruleManager             The manager to handle transformation rules.
     */
    public CommandController(
            ControlPanel controlPanel,
            TabbedAnswerController tabbedAnswerController,
            TransformationRuleController ruleController,
            LoginController loginController,
            NetworkConfigController networkConfigController,
            IOracleRepository oracleRepository,
            AnswerManager answerManager,
            HtmlTransformationRuleManager ruleManager) {
        this.controlPanel = controlPanel;
        this.tabbedAnswerController = tabbedAnswerController;
        this.ruleController = ruleController;
        this.loginController = loginController;
        this.networkConfigController = networkConfigController;
        this.oracleRepository = oracleRepository;
        this.answerManager = answerManager;
        this.ruleManager = ruleManager;

        setupListeners();
    }

    /**
     * Sets up listeners for user interactions on the control panel.
     */
    private void setupListeners() {
        controlPanel.addPropertyChangeListener("runStopToggle", evt -> toggleProcess((Boolean) evt.getNewValue()));
        controlPanel.addPropertyChangeListener("fileUploaded", evt -> loadAnswersFromFile((File) evt.getNewValue()));
        controlPanel.addPropertyChangeListener("addTransformation", evt -> ruleController.openTransformationDialog());
        controlPanel.addPropertyChangeListener("userSettings", evt -> loginController.openLoginDialog());
        controlPanel.addPropertyChangeListener("networkSettings", evt -> networkConfigController.openNetworkDialog());
    }

    /**
     * Toggles the process execution.
     * 
     * @param isRunning A flag indicating whether to start or stop the process.
     */
    private void toggleProcess(boolean isRunning) {
        if (!isRunning) return;

        // Disable the button while processing
        controlPanel.setRunning(true);
        controlPanel.setRunStopButtonEnabled(false);

        // Processing in a background thread using SwingWorker
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                List<Answer> answers = answerManager.getAnswers().stream()
                        .map(a -> new Answer(a.getId(), new LoadingAnswerState(a.getState().getDocument(), null)))
                        .toList();

                answers.forEach(CommandController.this::processAnswer);
                return null;
            }

            @Override
            protected void done() {
                // Re-enable the button after processing
                controlPanel.setRunning(false);
                controlPanel.setRunStopButtonEnabled(true);
            }
        }.execute(); // Start the background process
    }

    /**
     * Processes the given answer by fetching, transforming, and updating it.
     * 
     * @param answer The answer to process.
     */
    private void processAnswer(Answer answer) {
    	// Getting list of rules
        List<HtmlTransformationRule> rules = ruleManager.getRules().stream().map(r -> (HtmlTransformationRule) r).toList();

        // Rule processor
        HtmlRuleProcessor processor = new HtmlRuleProcessor();
        Answer newAnswer;

        // Fetching answer
        newAnswer = fetchAnswerFromRepository(answer);

        // Checking for error state
        if (newAnswer.getState() instanceof ErrorAnswerState) {
            return; // Stop if an error occurred
        }

        // Apply the transformation rules to the answer's document
        Document transformedDoc = applyTransformationRules(newAnswer, rules, processor);

        // Update the document and state
        updateAnswerState(newAnswer, transformedDoc, "The knowledge base is being updated");

        // Update the answer in the Oracle repository
        updateAnswerInRepository(newAnswer, transformedDoc);
    }

    /**
     * Fetches the answer from the Oracle repository and returns the updated answer state.
     * 
     * @param answer The answer to fetch.
     * @return The updated answer with the fetched state.
     */
    private Answer fetchAnswerFromRepository(Answer answer) {
        Answer newAnswer;
        try {
            OracleResponse response = oracleRepository.fetchAnswer(answer.getId());
            newAnswer = new Answer(answer.getId(), new UpdatingAnswerState(Jsoup.parse(response.html), null));
            answerManager.setAnswer(newAnswer);
            tabbedAnswerController.updateTabs();
        } catch (UnirestException e) {
            newAnswer = new Answer(answer.getId(), new ErrorAnswerState(null, e.getMessage()));
            answerManager.setAnswer(newAnswer);
            tabbedAnswerController.updateTabs();
        }
        return newAnswer;
    }

    /**
     * Applies transformation rules to the answer's document.
     * 
     * @param answer The answer to transform.
     * @param rules The list of transformation rules.
     * @param processor The rule processor to apply the transformations.
     * @return The transformed document.
     */
    private Document applyTransformationRules(Answer answer, List<HtmlTransformationRule> rules, HtmlRuleProcessor processor) {
        Document doc = processor.process(answer.getState().getDocument(), rules);
        return doc;
    }

    /**
     * Updates the answer's state with the transformed document and a new message.
     * 
     * @param answer The answer to update.
     * @param doc The transformed document.
     * @param message The status message to set for the answer.
     */
    private void updateAnswerState(Answer answer, Document doc, String message) {
        Answer newAnswer = new Answer(answer.getId(), new LoadingAnswerState(doc, message));
        answerManager.setAnswer(newAnswer);
        tabbedAnswerController.updateTabs();
    }

    /**
     * Updates the answer in the Oracle repository with the transformed document.
     * 
     * @param answer The answer to update in the repository.
     * @param doc The transformed document.
     */
    private void updateAnswerInRepository(Answer answer, Document doc) {
    	Answer newAnswer;
        try {
            doc.outputSettings().prettyPrint(false); // Turning pretty print off so the html is displayed in the original format
            
            // Update answer html
            oracleRepository.updateAnswer(answer.getId(), doc.toString());
            newAnswer = new Answer(answer.getId(), new CompleteAnswerState(doc, null)); // Setting finished state
        } catch (Exception e) {
            newAnswer = new Answer(answer.getId(), new ErrorAnswerState(null, "An error occurred while updating the knowledge base"));
        }
        
        // Updating manager and tabs
        answerManager.setAnswer(newAnswer);
        tabbedAnswerController.updateTabs();
    }

    /**
     * Loads answers from the file and sets them in the tabbed answer controller.
     * 
     * Gives the answers a LoadingAnswerState initially
     *
     * @param file The file containing the answers to load.
     */
    private void loadAnswersFromFile(File file) {
        try {
            List<Integer> answerIds = DocumentParser.getIds(file).stream().map(Integer::parseInt).toList();
            List<Answer> answers = answerIds.stream().map(id -> new Answer(id, new LoadingAnswerState(null, null))).toList();

            tabbedAnswerController.setAnswers(answers);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(controlPanel, "Failed to load file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


