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

public class BasicSearch implements SearchStrategy {

	@Override
	public List<Document> search(InvertedIndex index, List<String> searchTerms) {
		List<Document> queryResults = new ArrayList<>();
		List<Posting> postingList = new ArrayList<>();

		// Searching for the documents and their document-query score

		for (String searchTerm : searchTerms) {
			Term indexTerm = index.getTerm(searchTerm);

			if (indexTerm == null)
				continue;

			for (Posting posting : indexTerm.postings) {
				postingList.add(posting);
			}
		}

		// Building the Result List

		DocumentManager dm = DocumentManager.getInstance();

		for (Posting posting : postingList) {
			if(!queryResults.contains(posting))
				queryResults.add(dm.getDocumentByNumber(posting.documentNumber));
		}

		return queryResults;
	}

	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}
}
