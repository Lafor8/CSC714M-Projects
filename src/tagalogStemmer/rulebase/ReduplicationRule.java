package tagalogStemmer.rulebase;

import tagalogStemmer.models.Rule;
import tagalogStemmer.models.Word;
import tagalogStemmer.system.WordUtilities;

public class ReduplicationRule implements Rule {

	public static final int RULE_TYPE_PARTIAL_REDUPLICATION = 0;
	public static final int RULE_TYPE_FULL_REDUPLICATION = 1;

	private int type;

	public ReduplicationRule(int type) {
		this.type = type;
	}

	@Override
	public Word apply(Word input) {
		Word result = input;

		switch (type) {
		case RULE_TYPE_PARTIAL_REDUPLICATION:
			result = applyPartialRedup(input);
			break;
		case RULE_TYPE_FULL_REDUPLICATION:
			result = applyFullRedup(input);
			break;
		default:
			// TODO: no rule type defined
		}

		return result;
	}

	private Word applyPartialRedup(Word input) {
		String word = input.currWord;

		if (word.length() < 4)
			return input;

		boolean wordStartsWithVowel;
		int syllables = 3;

		wordStartsWithVowel = WordUtilities.isCharVowel(word.substring(0, 1));

		String history = "ROUTINE 6: ";

		if (syllables == 3) {
			if (wordStartsWithVowel) {
				// RULE 1
				// TODO: confirm if the only syllable format for vowels as first
				// letter is just (V)
				if (word.charAt(0) == word.charAt(1)) {
					// Apply Changes
					history += "(" + word.substring(0, 1) + ")" + word.substring(1);
					word = word.substring(1);
					history += " = " + word;

					input.applyChanges(word, history);
				}
			} else {
				// RULE 2
				// TODO: confirm if the only syllable format for consonants as
				// first letter is just (CV) or (CVC)
				if (word.length() > 4 && word.charAt(0) == word.charAt(2) && word.charAt(1) == word.charAt(3)) {
					// Apply Changes
					history += "(" + word.substring(0, 2) + ")" + word.substring(2);
					word = word.substring(2);
					history += " = " + word;

					input.applyChanges(word, history);

				} else if (word.length() > 6 && word.charAt(0) == word.charAt(3) && word.charAt(1) == word.charAt(4)
						&& word.charAt(2) == word.charAt(5)) {
					// Apply Changes

					history += "(" + word.substring(0, 3) + ")" + word.substring(3);
					word = word.substring(3);
					history += " = " + word;

					input.applyChanges(word, history);
				}
			}
		} else {
			// RULE 4
			// TODO: Find out how to syllabize a word
		}

		// TODO: RULE 3
		// Unimplemented because I can't understand the rule
		// EDIT: I GET IT NOW OMG IT'S SO SIMPLE PALA MAGULO LANG PAGWORD NIYA
		// Basically given multiple consonants as starting (C1 C2 V1),
		// partial reduplication is possible for 2 ways (C1 V1 C1 C2 V1) or (C1
		// C2 V1 C1 C2 V1)
		// eg. prito -> piprito, priprito

		return input;
	}

	private Word applyFullRedup(Word input) {

		String word = input.currWord;
		String history = "ROUTINE 8: ";

		String firstWord;
		String secondWord;

		// RULE 1: Reduplication and hyphenation of a two-syllable root without
		// any affix e.g. halu-halo
		if (word.contains("-")) {

			String[] tokens = word.split("-");

			// TODO check if it is two-syllable?
			// TODO what about those with multiple hyphens?
			firstWord = tokens[0].trim();
			secondWord = tokens[1].trim();

			history += "(" + firstWord + "-)" + secondWord;

		} else {
			firstWord = word.substring(0, word.length() / 2);
			secondWord = word.substring(word.length() / 2);

			history += "(" + firstWord + ")" + secondWord;
		}

		boolean sameExceptLastLetter = firstWord.substring(0, firstWord.length() - 1).equals(
				secondWord.substring(0, secondWord.length() - 1));
		boolean lastLetterIsUAndO = firstWord.charAt(firstWord.length() - 1) == 'u'
				&& secondWord.charAt(secondWord.length() - 1) == 'o';

		if (firstWord.equals(secondWord) || sameExceptLastLetter && lastLetterIsUAndO) {

			word = secondWord; // Use the second word for cases like
								// halu-halo
			input.applyChanges(word, history);
		}

		return input;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (type == this.RULE_TYPE_FULL_REDUPLICATION)
			sb.append("FULL ");
		else if (type == this.RULE_TYPE_PARTIAL_REDUPLICATION)
			sb.append("FULL ");
		sb.append(" REDUPLICATION");

		return sb.toString();
	}
}
