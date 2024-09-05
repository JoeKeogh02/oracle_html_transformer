package utils;

import java.util.ArrayList;
import java.util.List;

import model.rules.TransformationRule;

public class TransformationRuleManager<Element> {
	List<TransformationRule<Element>> rules;
	
	public TransformationRuleManager() {
		rules = new ArrayList<TransformationRule<Element>>();
	}
	
	public List<TransformationRule<Element>> getRules() {
		return rules;
	}
	
	public void setRules(List<TransformationRule<Element>> rules) {
		if (rules == null) {
			this.rules = new ArrayList<TransformationRule<Element>>();
			return;
		}
		this.rules = rules;
	}
	
	public void deleteRule(TransformationRule<Element> rule) {
		System.out.println("Before delete");
		if (rule == null) return;
		System.out.println("Deleting");
		rules.removeIf((r) -> r.equals(rule));
		rules.remove(rule);
	}
	
	public void setRule(TransformationRule<Element> rule) {
		if (rule == null) return;
		rules.add(rule);
	}
}
