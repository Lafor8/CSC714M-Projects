package ner;

import java.util.List;

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

	public String getMasterRegex() {
		String personRegex = convertToString(personRegexMatcher.regexList);
		String locationRegex = convertToString(locationRegexMatcher.regexList);
		String dateRegex = convertToString(dateRegexMatcher.regexList);

		String defaultRegex = convertToString(namedEntityRegexMatcher.regexList);
		// System.out.println(locationRegex);

		// return locationRegex;
		return "(" + locationRegex + ")|(" + dateRegex + ")|(" + personRegex + "|" + defaultRegex + ")";
	}

	public String convertToString(List<String> regexList) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String regex : regexList) {
			if (!first) {
				sb.append("|");
			}
			first = false;
			sb.append("(" + regex + ")");

		}

		return sb.toString().trim();
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
				ne.category = null; // unknown
			else
				return null; // not a named entity
		}

		// retain as null if did not match any regex
		// namedEntityRegexMatcher.matchesAnyRegex(string);
		return ne;
	}
}
