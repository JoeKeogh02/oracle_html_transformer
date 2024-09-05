package utils;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import model.rules.HtmlTransformationRule;

public class HtmlRuleProcessor {
    /**
     * Processes the given HTML content according to the specified list of transformation rules.
     *
     * @param htmlContent the HTML content to process
     * @param rules the list of transformation rules to apply
     * @return the processed HTML content
     */
    public Document process(Document doc, List<HtmlTransformationRule> rules) {     
    	if (doc == null) return doc;
        doc.outputSettings().prettyPrint(false);
		
		for (HtmlTransformationRule rule : rules) {
            Elements elements = doc.body().getAllElements(); // Simple selector based on searchPhrase
            
            elements.stream().forEach((e) -> rule.transform(e)); // Transforming the elements
        }
		
		return doc;
    }
}
