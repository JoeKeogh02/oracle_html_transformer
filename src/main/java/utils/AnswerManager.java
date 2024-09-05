package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.answers.states.Answer;

public class AnswerManager {
	Map<Integer, Answer> answers;
	
	public AnswerManager() {
		answers = new HashMap<Integer, Answer>();
	}
	
	public Answer getAnswerById(int id) {
		return answers.get(id);
	}
	
	public List<Answer> getAnswers() {
		return new ArrayList<Answer>(answers.values());
	}
	
	public void setAnswers(List<Answer> answers) {
		answers.stream().forEach((a) -> this.answers.putIfAbsent(a.getId(), a));
	}
	
	public void deleteAnswer(int id) {
		answers.remove(id);
	}
	
	public void setAnswer(Answer answer) {
		answers.put(answer.getId(), answer);
	}
}
