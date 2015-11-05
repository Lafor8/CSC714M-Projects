package informationRetrieval.models;

import java.util.ArrayList;
import java.util.List;

public class InvertedIndex {

	public List<Term> terms;

	public InvertedIndex(List<Term> terms) {
		this.terms = terms;
	}

	public void save(String filePath) {
		// TODO
	}

	public static InvertedIndex load(String filePath) {
		// TODO
		return new InvertedIndex(new ArrayList<Term>());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Term term : terms) {
			sb.append(term.toString()).append("\n");
		}
		return sb.toString();
	}

	public InvertedIndex clone() {

		List<Term> cloneTerms = new ArrayList<Term>();
		for (Term term : terms)
			cloneTerms.add(term.clone());

		InvertedIndex clone = new InvertedIndex(cloneTerms);
		return clone;
	}
}
