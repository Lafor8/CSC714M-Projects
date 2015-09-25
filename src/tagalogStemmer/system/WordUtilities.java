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
	}

	public static boolean isVowel(String c) {
		return WordUtilities.vowelPattern.matcher(c).matches();
	}

	public static boolean runAcceptabilityTest(String word) {
		boolean verdict = false, isVowel;

		if (word.length() != 0) {
			isVowel = WordUtilities.isVowel(word.substring(0, 1));

			if (isVowel) {
				if (word.length() >= 3) {
					for (int i = 0; !verdict && i < word.length(); i++) {
						String c = word.substring(i, i + 1);
						verdict = verdict || !WordUtilities.isVowel(c);
					}
				}
			} else {
				if (word.length() >= 4) {
					for (int i = 0; !verdict && i < word.length(); i++) {
						String c = word.substring(i, i + 1);
						verdict = verdict || WordUtilities.isVowel(c);
					}
				}
			}
		}

		return verdict;
	}
}