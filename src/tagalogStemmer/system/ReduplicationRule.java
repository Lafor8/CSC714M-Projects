package tagalogStemmer.system;

import java.util.ArrayList;
import java.util.regex.Pattern;

import tagalogStemmer.models.Word;

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
		// TODO Auto-generated method stub
		return null;
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
