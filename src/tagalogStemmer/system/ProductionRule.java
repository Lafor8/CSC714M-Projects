package tagalogStemmer.system;

import java.util.ArrayList;
import java.util.regex.Pattern;

import tagalogStemmer.models.Word;

public class ProductionRule implements Rule {

	public static final int RULE_TYPE_INFIX = 0;
	public static final int RULE_TYPE_PREFIX = 1;
	public static final int RULE_TYPE_SUFFIX = 2;
	public static final int RULE_TYPE_CIRCUMFIX = 3;

	private String pattern;
	private String replacement;
	private int type;

	public ProductionRule(String pattern, String replacement, int type) {
		this.pattern = pattern;
		this.replacement = replacement;
		this.type = type;
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
			String affix;

			// System.out.println(word);

			affix = this.pattern;

			String history = "ROUTINE 7: ";
			history += word.substring(0, word.length() - affix.length()) + "(" + affix + ")";

			word = word.replaceFirst(affix, this.replacement);
			history += " = " + word;

			input.addToHistory(history);
			input.history.add(word);
			input.currWord = word;

			// System.out.println(word);
			// System.out.println();
		}

		return input;
	}

	private Word applyPrefix(Word input) {
		String word = input.currWord;

		boolean isRuleApplicable = Pattern.compile(this.pattern + ".+").matcher(word).matches();

		if (isRuleApplicable) {
			String affix;

			// System.out.println(word);

			affix = this.pattern;

			String history = "ROUTINE 4: ";
			history += "(" + affix + ")" + word.substring(affix.length());

			word = word.replaceFirst(affix, this.replacement);
			history += " = " + word;

			input.addToHistory(history);
			input.history.add(word);
			input.currWord = word;

			// System.out.println(word);
			// System.out.println();
		}

		return input;
	}

	private Word applyInfix(Word input) {
		String word = input.currWord;

		String trimmed;
		trimmed = word.substring(1, word.length() - 1);

		boolean isRuleApplicable = Pattern.compile(".+" + this.pattern + ".+").matcher(trimmed).matches();

		if (isRuleApplicable) {
			String infix;

			// System.out.println(word);

			infix = this.pattern;
			trimmed = trimmed.replaceFirst(infix, "0");
			String ends[] = trimmed.split("0");

			String history = "ROUTINE 3: ";
			history += word.charAt(0) + ends[0] + "(" + infix + ")" + ends[1] + word.charAt(word.length() - 1);
			word = word.charAt(0) + ends[0] + ends[1] + word.charAt(word.length() - 1);
			history += " = " + word;

			input.addToHistory(history);
			input.history.add(word);
			input.currWord = word;

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
