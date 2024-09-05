package model.answers.states;

import org.jsoup.nodes.Document;

import java.awt.Color;

/*
 * State for when an error occurs while process an answer.
 */
public class ErrorAnswerState extends AnswerState {
	public ErrorAnswerState(Document document, String description) {
		super("Error", description, Color.RED, document);
	}
	
	@Override
	public String getDescription() {
		if (super.getDescription() != null && !super.getDescription().equals("")) return super.getDescription();
		
		return "Error";
	}
}
