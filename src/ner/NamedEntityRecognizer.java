package ner;

public class NamedEntityRecognizer {

	RegexMatcher namedEntityRegexMatcher;

	public NamedEntityRecognizer() {
		namedEntityRegexMatcher = RegexMatcher.getPersonOrLocationRegexMatcher();
	}

	public boolean isNamedEntity(String string) {
		return namedEntityRegexMatcher.matchesAnyRegex(string);
	}
}
