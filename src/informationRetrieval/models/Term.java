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
		sb.append(text + "(" + idf + ") -> ");
		for (Posting posting : postings) {
			sb.append(posting.toString() + ", ");
		}
		return sb.toString();
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
}
