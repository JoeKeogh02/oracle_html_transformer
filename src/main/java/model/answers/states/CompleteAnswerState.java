package model.answers.states;

import org.jsoup.nodes.Document;

import java.awt.Color;

/*
 * Finished Answer State
 * 
 * Applied at the end of a process
 */
public class CompleteAnswerState extends AnswerState {
	public CompleteAnswerState(Document document, String description) {
		super("Finished", description, Color.GREEN, document);
	}
	
	@Override
	public String getDescription() {
		if (super.getDescription() != null && !super.getDescription().equals("")) return super.getDescription();
		
		return "This answer has finished being transformed and updated";
	}
}

