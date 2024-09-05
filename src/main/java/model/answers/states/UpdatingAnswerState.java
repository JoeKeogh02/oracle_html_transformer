package model.answers.states;

import org.jsoup.nodes.Document;

/*
 * Answer state representing it is being updated
 */
public class UpdatingAnswerState extends AnswerState {
	public UpdatingAnswerState(Document document, String description) {
		super("Updating", description, null, document);
	}
	
	@Override
	public String getDescription() {
		if (super.getDescription() != null && !super.getDescription().equals("")) return super.getDescription();
		
		return "Transforming the html content";
	}
}

