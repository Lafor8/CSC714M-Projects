package models;

import ner.RegexMatcher;

public class NamedEntity implements Comparable<NamedEntity> {

	public enum Category {
		PERSON("Person"), LOCATION("Location"), DATE("Date");

		String value;

		Category(String value) {
			this.value = value;
		}

	}

	public String string;
	public Category category;

	public NamedEntity(String string, Category category) {
		this.string = string.trim();
		this.category = category;
	}

	public String getCleanString() {
		if (category.equals(Category.PERSON)) {
			// remove si/ni/kay/sina/nina/kina
			return string.replaceAll(RegexMatcher.PERSON_PANTUKOY + "\\s", "").trim();
		} else if (category.equals(Category.LOCATION)) {
			return string.replaceAll(RegexMatcher.LOCATION_PANTUKOY + "\\s", "").trim();
		} else if (category.equals(Category.DATE)) {
			return string.replaceAll(RegexMatcher.DATE_PANTUKOY + "\\s", "").trim();
		}

		return string;

	}

	@Override
	public String toString() {
		return string + "," + category;
	}

	@Override
	public boolean equals(Object o) {
		NamedEntity other = (NamedEntity) o;
		return string.equals(other.string);
	}

	@Override
	public int hashCode() {
		return string.hashCode();
	}

	@Override
	public int compareTo(NamedEntity other) {
		if (!this.category.equals(other.category))
			return this.category.value.compareTo(other.category.value);

		return this.string.compareTo(other.string);
	}
}
