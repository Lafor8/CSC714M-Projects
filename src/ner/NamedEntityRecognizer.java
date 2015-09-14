package ner;

public class NamedEntityRecognizer {

	RegexMatcher namedEntityRegexMatcher;

	public NamedEntityRecognizer() {
		namedEntityRegexMatcher = RegexMatcher.getNamedEntityRegexMatcher();
	}

	public boolean isNamedEntity(String string) {
		return namedEntityRegexMatcher.matchesAnyRegex(string);
	}
}
