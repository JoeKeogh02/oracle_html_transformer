package model.answers.states;

import org.jsoup.nodes.Document;

import java.awt.Color;

public class InitialAnswerState extends AnswerState {
	public InitialAnswerState(Document document, String description) {
		super("Fetching", description, Color.LIGHT_GRAY, document);
	}
	
	@Override
	public String getDescription() {
		if (super.getDescription() != null && !super.getDescription().equals("")) return super.getDescription();
		
		return "";
	}
}
