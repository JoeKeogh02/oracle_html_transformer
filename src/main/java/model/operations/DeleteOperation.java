package model.operations;

import java.util.regex.Pattern;

import java.util.Objects;

/**
 * An operation that deletes the original content, effectively replacing it with an empty string.
 */
public class DeleteOperation implements Operation<String> {
    final private String searchPhrase;

    /**
     * Constructs a DeleteOperation, setting the replacement text to an empty string.
     *
     * @param searchPhrase The phrase to delete from the content.
     */
    public DeleteOperation(String searchPhrase) {
        this.searchPhrase = searchPhrase; // Deleting text means setting it to an empty string
    }

    /**
     * Removes the search phrase from the element, cleaning up extra spaces and punctuation on the right side only.
     *
     * @param element The text to perform the operation on
     * @return The text with the search phrase deleted.
     */
    @Override
    public String operate(String element) {
        // Regex to remove the phrase and any spaces/punctuation on the right side only
        String regex = "\\b" + Pattern.quote(searchPhrase) + "\\b\\s*";
        return element.replaceAll(regex, "").trim();
    }
    
    @Override
    public String getName() {
        return "Delete";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check if they are the same instance
        if (obj == null || getClass() != obj.getClass()) return false;  // Check if the object is null or of a different class
        DeleteOperation that = (DeleteOperation) obj;  // Cast to the correct class
        return searchPhrase.equals(that.searchPhrase);  // Compare the search phrases
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchPhrase);  // Use the search phrase for hash code calculation
    }
}

