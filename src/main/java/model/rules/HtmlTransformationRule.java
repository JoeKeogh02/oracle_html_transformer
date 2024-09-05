package model.rules;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Objects;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import model.operations.EmptyOperation;
import model.operations.Operation;
import model.rules.TargetConfig.TargetType;

/**
 * Represents a transformation rule that defines how text should be modified.
 * This class encapsulates a search phrase, an operation to apply, and targeting configuration to specify where the operation should take effect.
 */
public class HtmlTransformationRule implements TransformationRule<Element> {
    private String searchPhrase;
    private Operation<String> operation;
    private TargetConfig targetConfig;

    /**
     * Constructs a new TransformationRule with a search phrase, an operation, and a target configuration.
     *
     * @param searchPhrase The phrase to search for in the content.
     * @param operation The operation to apply when the search phrase is found.
     * @param targetConfig The configuration that defines where and how the operation should be applied.
     */
    public HtmlTransformationRule(String searchPhrase, Operation<String> operation, TargetConfig targetConfig) {
        if (searchPhrase == null) searchPhrase = "";
        if (operation == null) operation = new EmptyOperation();
        if (targetConfig == null) targetConfig = new TargetConfig(EnumSet.allOf(TargetType.class), new HashMap<TargetType, Set<String>>());

        this.searchPhrase = searchPhrase;
        this.operation = operation;
        this.targetConfig = targetConfig;
    }

    /**
     * Retrieves the search phrase associated with this rule.
     *
     * @return The search phrase used for identifying the content to modify.
     */
    public String getSearchPhrase() {
        return searchPhrase;
    }

    /**
     * Retrieves the operation to be applied by this rule.
     *
     * @return The operation configured to modify the identified content.
     */
    public Operation<String> getOperation() {
        return operation;
    }

    /**
     * Retrieves the targeting configuration for this rule.
     *
     * @return The configuration detailing where and how the rule should be applied.
     */
    public TargetConfig getTargetConfig() {
        return targetConfig;
    }

    @Override
    public void transform(Element element) {
        _transformTag(element); // Transforming the tag name
        _transformText(element.textNodes()); // Transforming text nodes
        _transformAttributes(element.attributes().asList()); // Transforming attribute nodes
    }

    public void _transformTag(Element element) {
        boolean targetTags = targetConfig.checkTarget(TargetType.TAGS);
        boolean validTag = (targetTags && !targetConfig.containsSpecific(TargetType.TAGS, element.tagName())) || (!targetTags && targetConfig.containsSpecific(TargetType.TAGS, element.tagName()));

        if (validTag) return; // Checking if the element is valid to transform

        element.tagName(operation.operate(element.tagName()));
    }

    public void _transformAttributes(List<Attribute> attributes) {
        boolean targetAttr = targetConfig.checkTarget(TargetType.ATTRIBUTES);

        attributes.stream()
            .filter((a) -> (targetAttr && !targetConfig.containsSpecific(TargetType.ATTRIBUTES, a.getKey())) || (!targetAttr && targetConfig.containsSpecific(TargetType.ATTRIBUTES, a.getKey())))
            .forEach(att -> {
                att.setValue(operation.operate(att.getValue()));
            });
    }

    public void _transformText(List<TextNode> textNodes) {
        if (!targetConfig.checkTarget(TargetType.TEXT)) return;

        textNodes.forEach(tn -> tn.text(operation.operate(tn.toString())));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HtmlTransformationRule that = (HtmlTransformationRule) obj;
        return searchPhrase.equals(that.searchPhrase) &&
               operation.equals(that.operation) &&
               targetConfig.equals(that.targetConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchPhrase, operation, targetConfig);
    }
}

