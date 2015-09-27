package tagalogStemmer.rulebase;

import java.util.ArrayList;
import java.util.regex.Pattern;

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

		boolean wordStartsWithVowel, wordStartsWithConsonantCluster;
		int syllables = 3;

		wordStartsWithVowel = WordUtilities.isCharVowel(word.substring(0, 1));

		String history = "ROUTINE 6: ";

		boolean rule3Applied = false;
		if (!wordStartsWithVowel) {
			rule3Applied = false;
			// RULE 3
			// Basically given multiple consonants as starting (C1 C2 V1),
			// partial reduplication is possible for 2 ways (C1 V1 C1 C2 V1) or (C1 C2 V1 C1 C2 V1)
			// eg. prito -> piprito, priprito

			boolean cond[] = new boolean[2];
			if (word.length() > 5) {
				// CVCCV
				cond[0] = (word.charAt(2) + "" + word.charAt(4)).equals(word.substring(0, 2));
			}
			if (word.length() > 6) {
				// CCVCCV
				cond[1] = (word.substring(0, 3)).equals(word.substring(3, 6));
			}

			if (cond[0]) {
				history += "(" + word.substring(0, 2) + ")" + word.substring(2);
				word = word.substring(2);
				history += " = " + word;

				input.applyChanges(word, history);
			} else if (cond[1]) {
				history += "(" + word.substring(0, 3) + ")" + word.substring(3);
				word = word.substring(3);
				history += " = " + word;

				input.applyChanges(word, history);
			}

		}
		if (!rule3Applied) {
			if (syllables == 3) {
				if (wordStartsWithVowel) {
					// RULE 1
					// TODO: confirm if the only syllable format for vowels as first letter is just (V)
					if (word.charAt(0) == word.charAt(1)) {
						// Apply Changes
						history += "(" + word.substring(0, 1) + ")" + word.substring(1);
						word = word.substring(1);
						history += " = " + word;

						input.applyChanges(word, history);
					}
				} else {
					// RULE 2
					// TODO: confirm if the only syllable format for consonants as first letter is just (CV) or (CVC)
					if (word.length() > 4 && word.charAt(0) == word.charAt(2) && word.charAt(1) == word.charAt(3)) {
						// Apply Changes
						history += "(" + word.substring(0, 2) + ")" + word.substring(2);
						word = word.substring(2);
						history += " = " + word;

						input.applyChanges(word, history);

					} else if (word.length() > 6 && word.charAt(0) == word.charAt(3) && word.charAt(1) == word.charAt(4) && word.charAt(2) == word.charAt(5)) {
						// Apply Changes

						history += "(" + word.substring(0, 3) + ")" + word.substring(3);
						word = word.substring(3);
						history += " = " + word;

						input.applyChanges(word, history);
					}
				}

				// TODO: Find out how to syllabize a word
				if (syllables == 3) {
					// RULE 4

					String patterns[] = { "v", "cv", "vc", "cvc", "vcv", "cvcc", "ccvc" };

					int totalLength;
					for (int i = 0; i < patterns.length; ++i) {
						for (int j = 0; j < patterns.length; ++j) {
							totalLength = patterns[i].length() + patterns[j].length();

							if (totalLength * 2 + 1 <= word.length()) {

								boolean cond[] = new boolean[2];

								cond[0] = word.substring(0, totalLength).equals(word.substring(totalLength, totalLength * 2));
								cond[1] = WordUtilities.stringFollowsCVPattern(word.substring(0, totalLength), patterns[i] + patterns[j]);

								if (cond[0] && cond[1]) {
									history += "(" + word.substring(0, totalLength) + ")" + word.substring(totalLength);
									word = word.substring(totalLength);
									history += " = " + word;

									input.applyChanges(word, history);
								}
							}
						}
					}
				}
			}
		}
		return input;
	}

	private Word applyFullRedup(Word input) {
		// TODO Auto-generated method stub
		return null;
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
