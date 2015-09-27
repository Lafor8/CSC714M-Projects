package tagalogStemmer.system;

import java.util.regex.Pattern;

public class WordUtilities {

	public static Pattern vowelPattern = Pattern.compile("[aeiou]");

	public static void main(String args[]) {
		// Testing Acceptability Test

		// vowel
		System.out.println(WordUtilities.runAcceptabilityTest("aaaa") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("abaa") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("abba") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("abbb") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("aaa") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("aba") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("abb") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("aa") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("ab") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("a") == false);

		// consonant
		System.out.println(WordUtilities.runAcceptabilityTest("bbbbb") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("babbb") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("baabb") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("baaab") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("baaaa") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("bbbb") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("babb") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("baab") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("baaa") == true);
		System.out.println(WordUtilities.runAcceptabilityTest("bbb") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("bab") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("baa") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("bb") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("ba") == false);
		System.out.println(WordUtilities.runAcceptabilityTest("b") == false);
		
		System.out.println(WordUtilities.stringFollowsCVPattern("tahi","cvcv"));
		System.out.println(WordUtilities.isCharVowel('t'));
		System.out.println(WordUtilities.isCharVowel('a'));
		System.out.println(WordUtilities.isCharVowel('h'));
		System.out.println(WordUtilities.isCharVowel('i'));
	}

	public static boolean isCharVowel(String c) {
		return WordUtilities.vowelPattern.matcher(c).matches();
	}

	public static boolean isCharVowel(char c) {
		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
	}

	public static boolean stringHasConsonant(String str) {
		boolean verdict = false;

		for (int i = 0; !verdict && i < str.length(); i++) {
			String c = str.substring(i, i + 1);
			verdict = verdict || !WordUtilities.isCharVowel(c);
		}

		return verdict;
	}

	public static boolean stringHasVowel(String str) {
		boolean verdict = false;

		for (int i = 0; !verdict && i < str.length(); i++) {
			String c = str.substring(i, i + 1);
			verdict = verdict || WordUtilities.isCharVowel(c);
		}

		return verdict;
	}

	public static boolean stringFollowsCVPattern(String str, String pattern) {

		char letters[] = str.substring(0, pattern.length()).toCharArray();
		pattern = pattern.toLowerCase();
		
		boolean verdict = true;
		
		for(int i = 0; verdict && i < letters.length; ++i){
			if(pattern.charAt(i) =='c')
				verdict = verdict && !WordUtilities.isCharVowel(letters[i]);
			else if(pattern.charAt(i)=='v')
				verdict = verdict && WordUtilities.isCharVowel(letters[i]);
		}
		return verdict;
	}

	public static boolean runAcceptabilityTest(String word) {
		boolean verdict = false, isVowel;

		if (word.length() != 0) {
			isVowel = WordUtilities.isCharVowel(word.substring(0, 1));

			if (isVowel) {
				if (word.length() >= 3) {
					verdict = verdict || WordUtilities.stringHasConsonant(word);
				}
			} else {
				if (word.length() >= 4) {
					verdict = verdict || WordUtilities.stringHasVowel(word);
				}
			}
		}

		return verdict;
	}
}