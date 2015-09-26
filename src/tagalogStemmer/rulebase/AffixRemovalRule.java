package tagalogStemmer.rulebase;

import java.util.ArrayList;
import java.util.regex.Pattern;

import tagalogStemmer.models.Rule;
import tagalogStemmer.models.Word;
import tagalogStemmer.system.WordUtilities;

public class AffixRemovalRule implements Rule {

	public static final int RULE_TYPE_INFIX = 0;
	public static final int RULE_TYPE_PREFIX = 1;
	public static final int RULE_TYPE_SUFFIX = 2;
	public static final int RULE_TYPE_CIRCUMFIX = 3;

	private String pattern;
	private String replacement;
	private int type;
	private boolean checkAcceptability;

	public AffixRemovalRule(String pattern, String replacement, int type, boolean checkAcceptability) {
		this.pattern = pattern;
		this.replacement = replacement;
		this.type = type;
		this.checkAcceptability = checkAcceptability;
	}

	@Override
	public Word apply(Word input) {
		Word result = input;

		switch (type) {
		case RULE_TYPE_INFIX:
			result = applyInfix(input);
			break;
		case RULE_TYPE_PREFIX:
			result = applyPrefix(input);
			break;
		case RULE_TYPE_SUFFIX:
			result = applySuffix(input);
			break;
		case RULE_TYPE_CIRCUMFIX:
			result = applyCircumfix(input);
			break;
		default:
			// TODO: no rule type defined
		}

		return result;
	}

	private Word applyCircumfix(Word input) {
		// TODO Auto-generated method stub
		return null;
	}

	private Word applySuffix(Word input) {
		String word = input.currWord;

		boolean isRuleApplicable = Pattern.compile(".+" + this.pattern).matcher(word).matches();

		if (isRuleApplicable) {
			// AFFIX REMOVAL
			String affix;

			// System.out.println(word);

			affix = this.pattern;

			String history = "ROUTINE 7: ";
			history += word.substring(0, word.length() - affix.length()) + "(" + affix + ")";

			word = word.replaceFirst(affix, this.replacement);
			history += " = " + word;

			// ACCEPTABILITY TEST
			if (this.checkAcceptability) {
				boolean acceptable;

				acceptable = WordUtilities.runAcceptabilityTest(word);

				if (acceptable) {
					// System.out.println("Accepted: " + word);
					// System.out.println();

					// PHONEME CHANGE RULE
					{
						if (word.charAt(word.length() - 2) == 'u') {
							history += "\nROUTINE 7 (PHONEME CHANGE): ";
							history += word.substring(0, word.length() - 2) + "(u)" + word.substring(word.length() - 1);

							word = word.substring(0, word.length() - 2) + 'o' + word.substring(word.length() - 1);
							history += " = " + word;
						}
					}

					// Add changes
					input.applyChanges(word, history);
				} else {
					// System.out.println("Not Accepted: " + word);
					// System.out.println();
				}
			} else {// Add changes
				input.applyChanges(word, history);
			}

			// System.out.println(word);
			// System.out.println();
		}

		return input;
	}

	private Word applyPrefix(Word input) {
		String word = input.currWord;

		boolean isRuleApplicable = Pattern.compile(this.pattern + ".+").matcher(word).matches();

		if (isRuleApplicable) {
			// AFFIX REMOVAL
			String affix;

			// System.out.println(word);

			affix = this.pattern;

			String history = "ROUTINE 4: ";
			history += "(" + affix + ")" + word.substring(affix.length());

			word = word.replaceFirst(affix, this.replacement);
			history += " = " + word;

			// ACCEPTABILITY TEST
			if (this.checkAcceptability) {
				boolean acceptable;

				acceptable = WordUtilities.runAcceptabilityTest(word);

				if (acceptable) {
					// System.out.println("Accepted: " + word);
					// System.out.println();

					// PHONEME CHANGE RULES
					{
						boolean cond[] = new boolean[4];
						cond[0] = word.charAt(0) == 'r';
						cond[1] = WordUtilities.isCharVowel(affix.charAt(affix.length() - 1) + "");
						cond[2] = WordUtilities.isCharVowel(word.charAt(1) + "");
						cond[3] = word.charAt(2) == 'r';

						if (cond[0] && cond[1] && cond[2]) {
							history += "\nROUTINE 4 (PHONEME CHANGE): ";
							if (cond[3]) {
								history += "(r)" + word.substring(1, 2) + "(r)" + word.substring(3);
								word = 'd' + word.substring(1, 2) + 'd' + word.substring(3);
							} else {
								history += "(r)" + word.substring(1);
								word = 'd' + word.substring(1);
							}
							history += " = " + word;
						}
					}

					// Add changes
					input.applyChanges(word, history);
				} else {
					// System.out.println("Not Accepted: " + word);
					// System.out.println();
				}
			} else {// Add changes
				input.applyChanges(word, history);
			}

			// System.out.println(word);
			// System.out.println();
		}

		return input;
	}

	private Word applyInfix(Word input) {
		String word = input.currWord;

		boolean isRuleApplicable = Pattern.compile(".+" + this.pattern + ".+").matcher(word).matches();

		if (isRuleApplicable) {
			// AFFIX REMOVAL
			String infix;

			String trimmed;
			trimmed = word.substring(1, word.length() - 1);

			// System.out.println(word);

			infix = this.pattern;
			trimmed = trimmed.replaceFirst(infix, "0");
			String ends[] = trimmed.split("0");

			// oh my gosh this is horrible
			String history;

			if (infix.equals("in"))
				history = "ROUTINE 3: ";
			else if (infix.equals("um"))
				history = "ROUTINE 5: ";
			else
				history = "ROUTINE ???: ";

			if (ends.length == 2) {
				history += word.charAt(0) + ends[0] + "(" + infix + ")" + ends[1] + word.charAt(word.length() - 1);
				word = word.charAt(0) + ends[0] + ends[1] + word.charAt(word.length() - 1);
			} else if (ends.length == 1) {
				history += word.charAt(0) + "(" + infix + ")" + ends[0] + word.charAt(word.length() - 1);
				word = word.charAt(0) + ends[0] + word.charAt(word.length() - 1);
			}

			history += " = " + word;

			input.applyChanges(word, history);

			// System.out.println(word);
			// System.out.println();
		}

		return input;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.pattern);
		sb.append(" -> ");
		sb.append(this.replacement);

		return sb.toString();
	}
}
