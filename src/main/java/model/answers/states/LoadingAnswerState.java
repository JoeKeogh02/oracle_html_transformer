package model.answers.states;

import org.jsoup.nodes.Document;

/*
 * Answer state to determine idle / loading state
 */
public class LoadingAnswerState extends AnswerState {
	public LoadingAnswerState(Document document, String description) {
		super("Waiting", description, null, document);
	}
	
	@Override
	public String getDescription() {
		if (super.getDescription() != null && !super.getDescription().equals("")) return super.getDescription();
		
		return "Waiting for a response from the network";
	}
}