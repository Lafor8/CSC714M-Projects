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

	public static final String PERSON_PANTUKOY = "(ni|[Ss]i|nina|[Ss]ina|[Kk]ay|[Kk]ina|[Ss]ila|nila)";
	public static final String LOCATION_PANTUKOY = "([Ss]a)";

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

		// Specific dates/periods/events
		matcher.regexList.add("([A-z][A-z']+ ){1,4}[Aa]nniversary");
		matcher.regexList.add("([A-z][A-z']+ ){1,4}[Dd]ay");
		matcher.regexList.add("[Aa]raw [Nn]g ([A-z][A-z]+ ){0,3}[A-z][A-z]+");
		matcher.regexList.add("([Ii]ka-[0-9]{1,3} )?[Aa]nibersaryo [Nn]g ([A-z][A-z]+ ){0,3}[A-z][A-z]+");
		matcher.regexList.add("[Dd]ekada '[0-9]{2}");

		// In numeric format
		// matcher.regexList.add("([0-3][0-9]([0-9]{2})?[/.- ])?([0-3][0-9])[/.- ]([0-3][0-9]([0-9]{2})?)");

		// Year only
		String pantukoy = "(noong|nakaraang)";
		String optPantukoy = "(" + pantukoy + "|(" + pantukoy + "\\s.*\\s" + "))";

		matcher.regexList.add(optPantukoy + "(['][0-9]{2})|([1-2][0-9]{3})");

		// Days of the week
		String araw[] = { "Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo" };
		String day[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		String dayAbbreviations[] = { "Mon\\.?", "Tue(s)?\\.?", "Wed\\.?", "Thu(rs)?\\.?", "Fri\\.?", "Sat\\.?",
				"Sun\\.?" };

		for (String s : araw) {
			matcher.regexList.add(s);
		}
		for (String s : day) {
			matcher.regexList.add(s);
		}
		for (String s : dayAbbreviations) {
			matcher.regexList.add("\\s" + s + "\\s");
		}

		// In the format: MONTH [DAY][,] ['][YEAR]
		String buwan[] = { "Enero", "Pebrero", "Marso", "Abril", "Mayo", "Hu[nl]yo?", "Agosto", "Setyembre", "Oktubre",
				"Nobyembre", "Disyembre" };
		String month[] = { "January", "February", "March", "April", "May", "June", "July", "September", "October",
				"November", "December" };

		String buwanAbbreviations[] = { "Ene\\.?", "Peb\\.?", "Abr\\.?", "Hu[nl]\\.?", "Ago\\.?", "Set\\.?", "Okt\\.?",
				"Nob\\.?", "Dis\\.?" };
		String monthAbbreviations[] = { "Jan\\.?", "Feb\\.?", "Mar\\.?", "Apr\\.?", "Jun\\.?", "Jul\\.?",
				"Sep(t)?\\.?", "Oct\\.?", "Nov\\.?", "Dec\\.?" };

		String year = "(([1-2][0-9]{3})|(['][0-9]{2}))";
		String dayYear = "( ([0-3]?[0-9][,]? ?))?(" + year + ")?"; // includes
																	// just day

		// TODO: test
		// hunyo 12
		// Nobyembre 4
		// Enero 20
		// hulyo 2, 2014
		// hunyo 2 '13
		// hulyo '23
		// hunyo 12,2012

		for (String s : buwan) {
			matcher.regexList.add(s + year);
			matcher.regexList.add(s + dayYear);
			matcher.regexList.add(s);
			matcher.regexList.add("[Ii]ka-[0-3]?[0-9] [Nn]g " + s);
		}
		for (String s : month) {
			matcher.regexList.add(s + year);
			matcher.regexList.add(s + dayYear);
			matcher.regexList.add(s);
		}
		for (String s : buwanAbbreviations) {
			matcher.regexList.add(s + year);
			matcher.regexList.add(s + dayYear);
			matcher.regexList.add("\\s" + s + "\\s");
			matcher.regexList.add("[Ii]ka-[0-3]?[0-9] [Nn]g " + s);
		}
		for (String s : monthAbbreviations) {
			matcher.regexList.add(s + year);
			matcher.regexList.add(s + dayYear);
			matcher.regexList.add("\\s" + s + "\\s");
		}

		// TODO:
		// noong (nakaraang)? WORD
		// kahapon, bukas
		// etc.

		return matcher;
	}

	public static RegexMatcher getPersonRegexMatcher() {
		String abbreviations = "([A-Z]|[0-9]?Lt|Ar|Archt?|Atty|Bb|Bp|Br|Brig|Col|Di?r|Dra|Dn|Engg|Engr|Fr|G|Gen|Gng|Hon|J|Jr|Mr|Mr?s|Pr|Pres|Prof|Ptr|Rev|Sec|Sr|St|Supt)\\.";
		String capitalizedWord = "([A-Z][^(\\s|\\.|!|\\?|;)]+)";
		String capitalizedStart = "(" + abbreviations + "|\"" + capitalizedWord + "\"|" + capitalizedWord + ")";
		String number = "[0-9]+";
		String word = "([^(\\s|\\.|!|\\?|;)]+)";

		String articles = "(ng|mga|ni|of|on|the|an?|for|at)";

		// 1-5 Consecutive Capitals

		String first = capitalizedStart;
		String middle = "( (" + capitalizedStart + "|" + articles + "))*";
		String end = " (" + capitalizedStart + ")";
		String regex = first + middle + end;

		RegexMatcher matcher = new RegexMatcher();

		String pantukoy = PERSON_PANTUKOY;
		String optPantukoy = "(" + pantukoy + "|(" + pantukoy + "\\s" + word + "))";

		matcher.regexList.add(optPantukoy + "\\s" + regex);
		matcher.regexList.add(optPantukoy + "\\s" + capitalizedStart);

		return matcher;

	}

	public static RegexMatcher getLocationRegexMatcher() {
		String capitalizedWord = "([A-Z][^(\\s|\\.|!|\\?|;)]+)";
		String capitalizedStart = "(\"" + capitalizedWord + "\"|" + capitalizedWord + ")";
		String number = "[0-9]+";

		String word = "([^(\\s|\\.|!|\\?|;)]+)";

		String articles = "(ng|mga|ni|of|on|the|an?|for|at)";

		// 1-5 Consecutive Capitals

		String first = capitalizedStart;
		String middle = "( (" + capitalizedStart + "|" + articles + "))*";
		String end = " (" + capitalizedStart + "|" + number + ")";
		String regex = first + middle + end;

		RegexMatcher matcher = new RegexMatcher();

		String pantukoy = LOCATION_PANTUKOY;
		String optPantukoy = "(" + pantukoy + "|(" + pantukoy + "\\s" + word + "))";

		matcher.regexList.add(optPantukoy + "\\s" + regex);
		matcher.regexList.add(optPantukoy + "\\s" + capitalizedStart);

		// System.out.println(pantukoy + "\\s" + capitalizedStart);
		// System.out.println(pantukoy + "\\s" + regex);

		// matcher.regexList
		// .add("( ((\"([A-Z][^(\\s|\\.|,|!|?|;)]+)\"|([A-Z][^(\\s|\\.|,|!|?|;)]+))|(ng|mga|ni|of|on|the|an?|for)))*");
		// System.out.println(pantukoy + "\\s" + regex);

		return matcher;
	}

	/*
	 * This RegexMatcher will be used to determine whether a string refers to a
	 * person/location.
	 */
	public static RegexMatcher getPersonOrLocationRegexMatcher() {

		String abbreviations = "([A-Z]|[0-9]?Lt|Ar|Archt?|Atty|Bb|Bp|Br|Brig|Col|Di?r|Dra|Dn|Engg|Engr|Fr|G|Gen|Gng|Hon|J|Jr|Mr|Mr?s|Pr|Pres|Prof|Ptr|Rev|Sec|Sr|St|Supt)\\.";
		String capitalizedWord = "([A-Z][^(\\s|\\.|!|\\?|;)]+)";

		String capitalizedStart = "(" + abbreviations + "|\"" + capitalizedWord + "\"|" + capitalizedWord + ")";
		String number = "[0-9]+";

		String articles = "(ng|mga|ni|of|on|the|an?|for)";

		// 1-5 Consecutive Capitals

		String first = capitalizedStart;
		String middle = "( (" + capitalizedStart + "|" + articles + "))*";
		String end = " (" + capitalizedStart + "|" + number + ")";
		String regex = first + middle + end;

		// TODO add the person or location regexes here
		RegexMatcher matcher = new RegexMatcher();

		// matcher.regexList.add("[A-Z].*");

		matcher.regexList.add(capitalizedWord);
		matcher.regexList.add(regex);

		//
		// // Capital first but with 1 or more small intermediate
		// matcher.regexList.add("([A-Z][^\\s]+ )([A-z][^\\s]+
		// ){0,3}([A-Z][^\\s]+)");

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
		String genericEnglishKeywords = ".*([cC]ity|[cC]ountry|[pP]rovince|[rR]egion|[sS]tate|[rR]epublic|[sS]treet|[sS]t\\.).*";
		String actualPlaceNames = "[aA]laminos|[aA]ngeles|[aA]ntipolo|[bB]acolod|[bB]acoor|[bB]ago|[bB]aguio|[bB]ais|[bB]alanga|[bB]atac|[bB]atangas|[bB]ayawan|[bB]aybay|[bB]ayugan|[bB]binan|[bB]islig|[bB]ogo|[bB]orongan|[bB]utuan|[cC]abadbaran|[cC]abanatuan|[cC]abuyao|[cC]adiz|[cC]agayan|[cC]alamba|[pP]hilippines|Manila|Metro|Quezon|Pasay|Alabang|Muntinlupa";

		locationKeywords.regexList.add(genericFilipinoKeywords);
		locationKeywords.regexList.add(genericEnglishKeywords);
		locationKeywords.regexList.add(actualPlaceNames);

		return new RegexMatcher();
	}
}
