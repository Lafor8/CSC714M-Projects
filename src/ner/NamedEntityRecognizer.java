package ner;

import models.NamedEntity;
import models.NamedEntity.Category;

public class NamedEntityRecognizer {

	RegexMatcher namedEntityRegexMatcher;
	RegexMatcher personRegexMatcher;
	RegexMatcher locationRegexMatcher;
	RegexMatcher dateRegexMatcher;

	public NamedEntityRecognizer() {
		namedEntityRegexMatcher = RegexMatcher.getNamedEntityRegexMatcher();
		personRegexMatcher = RegexMatcher.getPersonRegexMatcher();
		locationRegexMatcher = RegexMatcher.getLocationRegexMatcher();
		dateRegexMatcher = RegexMatcher.getDateRegexMatcher();
	}

	public NamedEntity isNamedEntity(String string) {

		NamedEntity ne = new NamedEntity(string, null);

		if (dateRegexMatcher.matchesAnyRegex(string))
			ne.category = Category.DATE;
		else if (locationRegexMatcher.matchesAnyRegex(string))
			ne.category = Category.LOCATION;
		else if (personRegexMatcher.matchesAnyRegex(string))
			ne.category = Category.PERSON;
		else {

			if (namedEntityRegexMatcher.matchesAnyRegex(string))
				ne.category = Category.PERSON; // default
			else
				return null; // not a named entity
		}

		// retain as null if did not match any regex
		// namedEntityRegexMatcher.matchesAnyRegex(string);
		return ne;
	}
}
