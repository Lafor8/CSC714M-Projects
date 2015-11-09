package informationRetrieval.models;

import java.util.ArrayList;
import java.util.List;

public class Term {

	public String text;
	public double idf;
	public List<Posting> postings = new ArrayList<Posting>();

	public Term(String text) {
		this.text = text;
	}

	public int getDocumentFrequency() {
		return postings.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(text + " : " + idf + " = ");
		for (Posting posting : postings) {
			sb.append(posting.toString() + " ,");
		}
		return sb.toString().substring(0, sb.length() - 2);
	}

	public Term clone() {

		List<Posting> clonePostings = new ArrayList<Posting>();
		for (Posting posting : postings)
			clonePostings.add(posting.clone());

		Term clone = new Term(text);
		clone.idf = idf;
		clone.postings = clonePostings;
		return clone;
	}

	public static Term loadFromString(String string) {

		String[] tokens = string.split("[=,]");

		String[] termTokens = tokens[0].trim().split("[\\s:]+");
		Term term = new Term(termTokens[0]);
		term.idf = Double.parseDouble(termTokens[1]);

		for (int i = 1; i < tokens.length; i++) {
			term.postings.add(Posting.loadFromString(tokens[i].trim()));
		}

		return term;

	}
}
