package namedEntityRecognizer.system;

import namedEntityRecognizer.models.NamedEntity;
import namedEntityRecognizer.models.NamedEntity.Category;

public class NamedEntityCategorizer {

	RegexMatcher dateRegexMatcher;
	RegexMatcher locationKeywordsRegexMatcher; // will most probably contain
												// keywords instead of actual
												// regexes

	public NamedEntityCategorizer() {
		dateRegexMatcher = RegexMatcher.getDateKeywordsRegexMatcher();
		locationKeywordsRegexMatcher = RegexMatcher.getLocationKeywordsRegexMatcher();
	}

	public Category categorize(NamedEntity ne) {

		String string = ne.getCleanString();

		if (ne.category != null && !ne.category.equals(Category.PERSON) && dateRegexMatcher.matchesAnyRegex(string))
			return Category.DATE;

		if (ne.category != null)
			return ne.category;

		if (locationKeywordsRegexMatcher.matchesAnyRegex(string))
			return Category.LOCATION;

		// Default category is person, since it's the most generic entity
		return Category.PERSON;
	}
}
