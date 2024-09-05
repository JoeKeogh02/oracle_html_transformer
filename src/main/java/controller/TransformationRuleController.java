package controller;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.jsoup.nodes.Element;

import model.rules.HtmlTransformationRule;
import model.rules.TransformationRule;
import utils.HtmlTransformationRuleManager;
import view.dialog.TransformationRuleDialog;
import view.panels.TransformationPanel;

/**
 * The TransformationRuleController class is responsible for managing transformation rules
 * and their interaction with the {@code TransformationPanel}. It handles user interactions
 * like editing and deleting rules and updates the transformation panel accordingly.
 */
public class TransformationRuleController {
    private HtmlTransformationRuleManager ruleManager;
    private TransformationPanel transformationPanel;

    /**
     * Constructs a new TransformationRuleController with the given rule manager and transformation panel.
     *
     * @param ruleManager    	the manager responsible for handling transformation rules.
     * @param transformationPanel  the panel that displays the transformation rules and handles user interactions.
     */
    public TransformationRuleController(HtmlTransformationRuleManager ruleManager, TransformationPanel transformationPanel) {
        this.ruleManager = ruleManager;
        this.transformationPanel = transformationPanel;
        attachEventListeners();
    }

    /**
     * Attaches event listeners to the transformation panel to listen for user interactions
     * such as deleting or editing a transformation rule.
     */
    private void attachEventListeners() {
        System.out.println("Event listeners attached");
        transformationPanel.addPropertyChangeListener("deleteRule", this::handleDeleteRule);
        transformationPanel.addPropertyChangeListener("editPopUpRule", this::handleEditPopUp);
    }

    /**
     * Handles the event triggered when a rule is to be deleted. It retrieves the rule from
     * the event and calls deleteRule(HtmlTransformationRule) to perform the deletion.
     *
     * @param evt the property change event containing the rule to be deleted.
     */
    private void handleDeleteRule(PropertyChangeEvent evt) {
        HtmlTransformationRule rule = (HtmlTransformationRule) evt.getNewValue();
        System.out.println("Deleting rule");
        deleteRule(rule);
    }

    /**
     * Handles the event triggered to open the transformation dialog for editing a rule.
     *
     * @param evt the property change event indicating the edit action.
     */
    private void handleEditPopUp(PropertyChangeEvent evt) {
        openTransformationDialog();
    }

    /**
     * Updates the transformation panel with the latest list of transformation rules.
     * This method is called after any rule is added, edited, or deleted.
     */
    private void updateTransformationPanel() {
        List<TransformationRule<Element>> rules = ruleManager.getRules();
        List<HtmlTransformationRule> htmlRules = rules.stream()
                .map(rule -> (HtmlTransformationRule) rule)
                .toList();
        
        transformationPanel.updateRules(htmlRules);
    }

    /**
     * Edits an existing transformation rule by deleting the old rule and setting the new rule.
     * After editing, the transformation panel is updated to reflect the changes.
     *
     * @param oldRule the existing rule to be replaced.
     * @param newRule the new rule to set in place of the old rule.
     */
    public void editRule(HtmlTransformationRule oldRule, HtmlTransformationRule newRule) {
        ruleManager.deleteRule(oldRule);
        ruleManager.setRule(newRule);
        System.out.println("Rule edited");
        updateTransformationPanel();
    }

    /**
     * Deletes the given transformation rule from the rule manager.
     * After deletion, the transformation panel is updated to reflect the removal.
     *
     * @param rule the rule to delete.
     */
    public void deleteRule(HtmlTransformationRule rule) {
        System.out.println("Rule deleted");
        ruleManager.deleteRule(rule);
        updateTransformationPanel();
    }

    /**
     * Opens the transformation rule dialog to allow the user to create or edit a transformation rule.
     * After the dialog is closed, the rule is either added or updated based on the user's input.
     */
    public void openTransformationDialog() {
        TransformationRuleDialog dialog = new TransformationRuleDialog(null, null, 
            (oldRule, newRule) -> editRule(oldRule, newRule));
        dialog.setVisible(true);
    }
}



