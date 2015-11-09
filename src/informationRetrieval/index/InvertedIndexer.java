package informationRetrieval.index;

import informationRetrieval.models.Document;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.Posting;
import informationRetrieval.models.Term;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class InvertedIndexer {

	public static InvertedIndex index(List<Document> documents) {

		// collect all unique terms hashset throughout all documents
		// sort the unique terms alphabetically
		List<Term> uniqueTerms = retrieveUniqueTerms(documents);

		// for each term,
		// look for all documents that had the term.
		// for each document having the term, create a posting referring to the
		// document
		// add this posting to the term
		for (Term term : uniqueTerms) {
			for (Document document : documents) {
				if (document.containsString(term.text)) {
					term.postings.add(new Posting(document));
				}
			}
		}

		return new InvertedIndex(uniqueTerms);
	}

	private static List<Term> retrieveUniqueTerms(List<Document> documents) {

		HashSet<String> uniqueStringSet = new HashSet<String>();
		for (Document document : documents) {
			for (String string : document.tokens)
				uniqueStringSet.add(string);
		}

		List<String> uniqueStringList = new ArrayList<String>(uniqueStringSet);
		Collections.sort(uniqueStringList);

		List<Term> uniqueTerms = new ArrayList<Term>();
		for (String string : uniqueStringList) {
			uniqueTerms.add(new Term(string));
		}

		return uniqueTerms;
	}

}
