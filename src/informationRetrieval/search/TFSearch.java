package informationRetrieval.search;

import informationRetrieval.models.Document;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.Posting;
import informationRetrieval.models.Term;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import common.util.CollectionsUtility;

public class TFSearch implements SearchStrategy {

	@Override
	public List<Document> search(InvertedIndex index, List<String> searchTerms) {
		List<Document> queryResults = new ArrayList<>();
		HashMap<Posting, Double> postingScores = new HashMap<>();
		List<Entry<Posting, Double>> postingList;

		// Searching for the documents and their document-query score

		for (String searchTerm : searchTerms) {
			Term indexTerm = index.getTerm(searchTerm);

			if (indexTerm == null)
				continue;

			for (Posting posting : indexTerm.postings) {
				Double newScore = (1 + Math.log10(posting.tf));

				if (postingScores.containsKey(posting)) {
					Double oldScore = postingScores.get(posting);

					newScore += oldScore;
				}
				postingScores.put(posting, newScore);
			}
		}

		postingList = CollectionsUtility.entriesSortedByValues(postingScores);

		// Building the Result List

		DocumentManager dm = DocumentManager.getInstance();

		for (Entry<Posting, Double> postingEntry : postingList) {
			queryResults.add(dm.getDocumentByNumber(postingEntry.getKey().documentNumber));
		}

		return queryResults;
	}
}
