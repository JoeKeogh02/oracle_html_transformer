package model.rules;

public interface TransformationRule<Element> {
	public void transform(Element element);
}