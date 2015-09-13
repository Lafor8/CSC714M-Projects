package ner;

import java.util.ArrayList;
import java.util.List;

public class RegexMatcher {

	public List<String> regexList;

	public RegexMatcher() {
		regexList = new ArrayList<String>();
	}

	public boolean matchesAnyRegex(String string) {
		for (String regex : regexList)
			if (string.matches(regex))
				return true;

		return false;
	}

	public RegexMatcher mergeWith(RegexMatcher other) {
		RegexMatcher newRegexMatcher = new RegexMatcher();
		newRegexMatcher.regexList.addAll(this.regexList);
		newRegexMatcher.regexList.addAll(other.regexList);
		return newRegexMatcher;
	}

	/*
	 * This RegexMatcher will be used for deciding whether a string is a named
	 * entity or not. Basically, the date + person/location regex matchers
	 * merged into one.
	 */
	public static RegexMatcher getNamedEntityRegexMatcher() {
		return getDateRegexMatcher().mergeWith(getPersonOrLocationRegexMatcher());
	}

	/*
	 * This RegexMatcher will be used to determine whether a string is a date or
	 * not.
	 */

	public static RegexMatcher getDateRegexMatcher() {
		// TODO add the date regexes here
		return new RegexMatcher();
	}

	/*
	 * This RegexMatcher will be used to determine whether a string refers to a
	 * person/location.
	 */
	public static RegexMatcher getPersonOrLocationRegexMatcher() {
		// TODO add the person or location regexes here
		return new RegexMatcher();
	}

	/*
	 * This RegexMatcher will be used for categorizing an already recognized
	 * Named Entity.
	 * 
	 * The regexes here won't actually be regexes; they'll most likely be
	 * location keywords.
	 */
	public static RegexMatcher getLocationKeywordsRegexMatcher() {
		// TODO add the location keywords here
		return new RegexMatcher();
	}

}
