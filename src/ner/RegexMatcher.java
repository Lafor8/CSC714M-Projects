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
		RegexMatcher matcher = new RegexMatcher();

		// matcher.regexList.add("[A-Z].*");

		// 1-5 Consecutive Capitals
		matcher.regexList.add("[A-Z][A-z]+ [0-9]{1,2}([,]?[ ]?[0-9]{2,4})?");
		matcher.regexList.add("[0-9]{1,2}[/.-][0-9]{1,2}([/.-]([0-9][0-9]){1,2})?");
		matcher.regexList.add("[Ii]ka-[0-9]{1,2} ng [A-z][a-z]+");
		matcher.regexList.add("[0-9]{4}");
		matcher.regexList.add("[Dd]ekada '[0-9]{2}");

		// Capital first but with 1 or more small intermediate

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

		matcher.regexList.add(capitalizedWord);
		matcher.regexList.add(regex);

		// System.out.println(capitalizedStart);
		System.out.println(regex + "|" + capitalizedWord);

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

		RegexMatcher locationKeywords = new RegexMatcher();

		String genericFilipinoKeywords = ".*([lL]ungsod|[sS]i?yudad|[lL]alawigan|[mM]unisipyo|[lL]ugar|[pP]ook).*";
		String genericEnglishKeywords = ".*([cC]ity|[cC]ountry|[pP]rovince|[rR]egion|[sS]tate|[rR]epublic).*";
		String actualPlaceNames = "[aA]laminos|[aA]ngeles|[aA]ntipolo|[bB]acolod|[bB]acoor|[bB]ago|[bB]aguio|[bB]ais|[bB]alanga|[bB]atac|[bB]atangas|[bB]ayawan|[bB]aybay|[bB]ayugan|[bB]binan|[bB]islig|[bB]ogo|[bB]orongan|[bB]utuan|[cC]abadbaran|[cC]abanatuan|[cC]abuyao|[cC]adiz|[cC]agayan|[cC]alamba[pP]hilippines";

		locationKeywords.regexList.add(genericFilipinoKeywords);
		locationKeywords.regexList.add(genericEnglishKeywords);
		locationKeywords.regexList.add(actualPlaceNames);

		return new RegexMatcher();
	}

}
