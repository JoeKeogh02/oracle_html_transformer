package model.operations;

/**
 * An operation that replaces the original content with a specified replacement text.
 */
public class ReplaceOperation implements Operation<String> {
	private String searchPhrase;
    private String replacementText;

    /**
     * Constructs a ReplaceOperation with the specified replacement text.
     *
     * @param replacementText The text to use as a replacement.
     */
    public ReplaceOperation(String searchPhrase, String replacementText) {
        this.replacementText = replacementText;
        this.searchPhrase = searchPhrase;
    }


    /**
     * Replaces all occurrences of the search phrase in the element with the replacement text.
     *
     * @param element The text to perform the operation on
     * @return The text with the search phrase replaced.
     */
    @Override
    public String operate(String element) {
        return element.replace(searchPhrase, replacementText);
    }


	@Override
	public String getName() {
		return "Replace with " + replacementText;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check if they are the same instance
        if (obj == null || getClass() != obj.getClass()) return false;  // Check if the object is null or of a different class
        ReplaceOperation that = (ReplaceOperation) obj;  // Cast to the correct class
        return replacementText.equals(that.replacementText);  // Compare the search phrases
    }
}
