package model.answers.states;

import org.jsoup.nodes.Document;

import java.awt.Color;

/*
 * Represents a state an answer can be in
 * 
 * Holds the document and description of the answer
 * 
 */
public class AnswerState {
	final private String name;
	final private Color colour; // Colour related to the state
	final private String description;
	final private Document document;
	
	public AnswerState(String name, String description, Color colour, Document document) {
		if (name == null) name = "";
		if (colour == null) colour = Color.LIGHT_GRAY;
		if (description == null) description = "";
		
		this.name = name;
		this.colour = colour;
		this.description = description;
		this.document = document;
	}

	/*
	 * Name of this state
	 */
	public String getName() {
		return name;
	}

	/*
	 * Description of this state
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * Colour of this state
	 */
	public Color getColour() {
		return colour;
	}

	/*
	 * Current state of the html document
	 */
	public Document getDocument() {
		return document;
	}
}
