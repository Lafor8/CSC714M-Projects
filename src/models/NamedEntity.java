package models;

public class NamedEntity {

	public enum Category {
		Person, Location, Date
	}

	public String string;
	public Category category;

	public NamedEntity(String string, Category category) {
		this.string = string;
		this.category = category;
	}

}
