package models;

public class NamedEntity {

	public enum Category {
		PERSON, LOCATION, DATE
	}

	public String string;
	public Category category;

	public NamedEntity(String string, Category category) {
		this.string = string;
		this.category = category;
	}

}
