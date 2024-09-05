package view.interfaces;

import model.operations.Operation;

public interface OperationPanel {
    Operation<String> getOperation(String searchPhrase);
}

