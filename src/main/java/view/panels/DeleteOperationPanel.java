package view.panels;

import javax.swing.JPanel;

import model.operations.DeleteOperation;
import model.operations.Operation;
import view.interfaces.OperationPanel;

import java.awt.BorderLayout;

public class DeleteOperationPanel extends JPanel implements OperationPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1282098374940309462L;

	public DeleteOperationPanel() {
        setLayout(new BorderLayout());
        //add(new JLabel("Delete Operation: No additional input needed."), BorderLayout.CENTER);
    }

    @Override
    public Operation<String> getOperation(String searchPhrase) {
        return new DeleteOperation(searchPhrase);
    }
}
