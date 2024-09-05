package model.operations;

/**
 * Represents a generic operation to be performed on text.
 * This interface defines a method for retrieving the replacement text as a result of an operation.
 */
public interface Operation<Element> {    
    /**
     * Performs the operation on the given element.
     *
     * @param element The element to perform the operation on
     * 
     * @return The updated element.
     */
    public Element operate(Element element); 
    
    public String getName();
}
