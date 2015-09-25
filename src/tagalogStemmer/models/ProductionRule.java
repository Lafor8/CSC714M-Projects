package tagalogStemmer.models;

import java.util.ArrayList;

public class ProductionRule {
	
	private String pattern;
	private String replacement;
	
	public ProductionRule(String pattern, String replacement){
		this.pattern = pattern;
		this.replacement = replacement;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.pattern);
		sb.append(" -> ");
		sb.append(this.replacement);

		return sb.toString();
	}
}
