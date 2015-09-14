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

	static String abbreviations = "(([A-Z]|[0-9]?Lt|Ar|Archt?|Atty|Bb|Bp|Br|Brig|Col|Di?r|Dra|Dn|Engg|Engr|Fr|G|Gen|Gng|Hon|J|Jr|Mr|Mr?s|Pr|Pres|Prof|Ptr|Rev|Sec|Sr|St|Supt)\\.)";
	static String capitalizedWord = "([A-Z][^(\\s|\\.|,)]+)";
	static String capitalizedStart = "(" + abbreviations + "|\"?" + capitalizedWord + "\"?)";
	static String number = "[0-9]+";

	static String articles = "(ng|mga|ni|at|of|on|the|an?)";

	static String first = "((^.\\s)[A-Z][^(\\s|\\.|,)]+)";
	static String middle = "( (" + capitalizedStart + "|" + articles + "))*";
	static String end = " (" + capitalizedStart + "|" + number + ")";
	static String neRegex = first + middle + end;

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
		RegexMatcher matcher = new RegexMatcher();

		// matcher.regexList.add("[A-Z].*");

		// 1-5 Consecutive Capitals
		// matcher.regexList.add("[A-Z][A-z]+ [0-9]{1,2}([,]?[ ]?[0-9]{2,4})?");
		// matcher.regexList.add("[0-9]{1,2}[/.-][0-9]{1,2}([/.-]([0-9][0-9]){1,2})?");
		// matcher.regexList.add("[Ii]ka-[0-9]{1,2} ng [A-z][a-z]+");
		// matcher.regexList.add("[0-9]{4}");
		// matcher.regexList.add("[Dd]ekada '[0-9]{2}");

		// Capital first but with 1 or more small intermediate

		return matcher;
	}

	public static RegexMatcher getPersonRegexMatcher() {
		String abbreviations = "([A-Z]|[0-9]?Lt|Ar|Archt?|Atty|Bb|Bp|Br|Brig|Col|Di?r|Dra|Dn|Engg|Engr|Fr|G|Gen|Gng|Hon|J|Jr|Mr|Mr?s|Pr|Pres|Prof|Ptr|Rev|Sec|Sr|St|Supt)\\.";
		String capitalizedWord = "([A-Z][^(\\s|\\.|,)]+)";
		String capitalizedStart = "(" + abbreviations + "|\"" + capitalizedWord + "\"|" + capitalizedWord + ")";
		String number = "[0-9]+";

		String articles = "(ng|mga|ni|of|on|the|an?|for)";

		// 1-5 Consecutive Capitals

		String first = capitalizedStart;
		String middle = "( (" + capitalizedStart + "|" + articles + "))*";
		String end = " (" + capitalizedStart + "|" + number + ")";
		String regex = first + middle + end;

		RegexMatcher matcher = new RegexMatcher();

		String pantukoy = "(ni|si|nina|sina|kay|kina)";

		matcher.regexList.add(pantukoy + "\\s" + regex);

		return matcher;

	}

	public static RegexMatcher getLocationRegexMatcher() {
		String abbreviations = "([A-Z]|[0-9]?Lt|Ar|Archt?|Atty|Bb|Bp|Br|Brig|Col|Di?r|Dra|Dn|Engg|Engr|Fr|G|Gen|Gng|Hon|J|Jr|Mr|Mr?s|Pr|Pres|Prof|Ptr|Rev|Sec|Sr|St|Supt)\\.";
		String capitalizedWord = "([A-Z][^(\\s|\\.|,|!|?)]+)";
		String capitalizedStart = "(" + abbreviations + "|\"" + capitalizedWord + "\"|" + capitalizedWord + ")";
		String number = "[0-9]+";

		String articles = "(ng|mga|ni|of|on|the|an?|for)";

		// 1-5 Consecutive Capitals

		String first = capitalizedStart;
		String middle = "( (" + capitalizedStart + "|" + articles + "))*";
		String end = " (" + capitalizedStart + "|" + number + ")";
		String regex = first + middle + end;

		RegexMatcher matcher = new RegexMatcher();

		String pantukoy = "(sa)";

		matcher.regexList.add(pantukoy + "\\s" + regex);

		return matcher;
	}

	/*
	 * This RegexMatcher will be used to determine whether a string refers to a
	 * person/location.
	 */
	public static RegexMatcher getPersonOrLocationRegexMatcher() {
		// TODO add the person or location regexes here
		RegexMatcher matcher = new RegexMatcher();

		// matcher.regexList.add("[A-Z].*");

		matcher.regexList.add(capitalizedWord);
		matcher.regexList.add(neRegex);

		// System.out.println(neRegex + "|" + capitalizedWord);

		//
		// // Capital first but with 1 or more small intermediate
		// matcher.regexList.add("([A-Z][^\\s]+ )([A-z][^\\s]+ ){0,3}([A-Z][^\\s]+)");

		return matcher;
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
		RegexMatcher locationKeywords = new RegexMatcher();

		String genericKeywords = ".*([lL]ungsod|[sS]i?yudad|[lL]alawigan|[mM]unisipyo).*";
		String actualPlaceNames = "";

		locationKeywords.regexList.add("**");
		locationKeywords.regexList.add("*[lL]ungsod*");

		return new RegexMatcher();
	}

}
