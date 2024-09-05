package model.answers.states;

public class Answer {
	private int id;
	private AnswerState state;
	
	public Answer(int id, AnswerState state) {
		this.id = id;
		if (state == null) state = new InitialAnswerState(null, null);
		this.state = state;
	}
	
	public int getId() { return id; }
	
	public AnswerState getState() { return state; }
}
