package view.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.operations.Operation;
import model.operations.ReplaceOperation;

import view.interfaces.OperationPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class ReplaceOperationPanel extends JPanel implements OperationPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3048599841481643798L;
	
	private JTextField replaceWithField;

    public ReplaceOperationPanel() {
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(1, 1));
        replaceWithField = new JTextField();
        inputPanel.add(new JLabel("Replace with:"));
        inputPanel.add(replaceWithField);
        
        add(inputPanel, BorderLayout.NORTH);
    }

    @Override
    public Operation<String> getOperation(String searchPhrase) {
        String replacementText = replaceWithField.getText();
        
        if (replacementText.isEmpty() || searchPhrase.isEmpty()) return null; // Field doesn't have a valid entry
        
        
        return new ReplaceOperation(searchPhrase, replacementText);
    }
}
