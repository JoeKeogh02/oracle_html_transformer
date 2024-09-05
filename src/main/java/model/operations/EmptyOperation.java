package model.operations;

/**
 * An operation that deletes the original content, effectively replacing it with an empty string.
 */
public class EmptyOperation implements Operation<String> {
    /**
     * Constructs a DeleteOperation, setting the replacement text to an empty string.
     */
    public EmptyOperation() {}
    
    /**
     * Removes the search phrase from the element, cleaning up extra spaces and punctuation.
     *
     * @param element The text to perform the operation on
     * @return The text with the search phrase deleted.
     */
    @Override
    public String operate(String element) {
    	return "";
    }

	@Override
	public String getName() {
		return "";
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check if they are the same instance
        if (obj == null || getClass() != obj.getClass()) return false;  // Check if the object is null or of a different class
        
        return true;
    }
}
