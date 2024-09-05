package model.operations;

/**
 * An operation that applies a sub-operation to a specific subphrase within the original content.
 * This allows for nested operations within a larger modification context.
 */
public class SubphraseOperation implements Operation<String> {
    private Operation<String> subOperation;
    private String searchPhrase;

    /**
     * Constructs a SubphraseOperation that targets a specific search phrase and applies a specified sub-operation to it.
     *
     * @param searchPhrase The phrase within the content to target for the sub-operation.
     * @param subOperation The operation to apply to the identified subphrase.
     */
    public SubphraseOperation(String searchPhrase, Operation<String> subOperation) {
        this.searchPhrase = searchPhrase;
        this.subOperation = subOperation;
    }
    
    /**
     * Applies the sub-operation to the search phrase within the element, if found.
     *
     * @param element The text to perform the operation on
     * @return The text with the operation applied to the subphrase.
     */
    @Override
    public String operate(String element) {
        if (element.contains(searchPhrase)) {
            // Replace the exact search phrase within the text with the result of the sub-operation
            String modifiedSubphrase = subOperation.operate(searchPhrase);
            return element.replace(searchPhrase, modifiedSubphrase);
        }
        return element;
    }

	@Override
	public String getName() {
		return subOperation.getName();
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check if they are the same instance
        if (obj == null || getClass() != obj.getClass()) return false;  // Check if the object is null or of a different class
        SubphraseOperation that = (SubphraseOperation) obj;  // Cast to the correct class
        return searchPhrase.equals(that.searchPhrase) && subOperation.equals(that.subOperation);  // Compare the search phrases
    }
}
