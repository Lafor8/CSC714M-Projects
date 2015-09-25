package tagalogStemmer.system;

import java.util.ArrayList;
import java.util.regex.Pattern;

import tagalogStemmer.models.StemmerResult;

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
	public StemmerResult apply(StemmerResult input) {
		StemmerResult result = input;

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

	private StemmerResult applyCircumfix(StemmerResult input) {
		// TODO Auto-generated method stub
		return null;
	}

	private StemmerResult applySuffix(StemmerResult input) {
		// TODO Auto-generated method stub
		return null;
	}

	private StemmerResult applyPrefix(StemmerResult input) {
		// TODO Auto-generated method stub
		return null;
	}

	private StemmerResult applyInfix(StemmerResult input) {
		ArrayList<String> segments = input.getSegments();
		String word = input.getRoot();

		String trimmed;
		trimmed = word.substring(1, word.length() - 1);
		boolean applicable = Pattern.compile(this.pattern).matcher(trimmed).matches();
		if (applicable) {
			String infix;

			System.out.println(word);
			// System.out.println(trimmed);

			infix = this.pattern.substring(2, this.pattern.length() - 2);
			trimmed = trimmed.replaceFirst(infix, "0");
			String ends[] = trimmed.split("0");

			System.out.println(word.charAt(0) + ends[0] + " + " + infix + " + " + ends[1] + word.charAt(word.length() - 1));

			word = word.charAt(0) + ends[0] + ends[1] + word.charAt(word.length() - 1);
			String segment = word.charAt(0) + ends[0] + "(" + infix + ")" + ends[1] + word.charAt(word.length() - 1);
			input.setRoot(word);

			System.out.println(word);
			System.out.println();

			//TODO: temp
			input.setFinished(true);
			
			int rootIndex = input.getRootIndex();
			segments.remove(rootIndex);
			segments.add(rootIndex, segment);
			
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
