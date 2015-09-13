package ner;

import models.NamedEntity.Category;

public class NamedEntityCategorizer {

	RegexMatcher dateRegexMatcher;
	RegexMatcher locationKeywordsRegexMatcher; // will most probably contain
												// keywords instead of actual
												// regexes

	public NamedEntityCategorizer() {
		dateRegexMatcher = RegexMatcher.getDateRegexMatcher();
		locationKeywordsRegexMatcher = RegexMatcher.getLocationKeywordsRegexMatcher();
	}

	public Category categorize(String string) {

		if (dateRegexMatcher.matchesAnyRegex(string))
			return Category.DATE;

		if (locationKeywordsRegexMatcher.matchesAnyRegex(string))
			return Category.LOCATION;

		// Default category is person, since it's the most generic entity
		return Category.PERSON;
	}
}
