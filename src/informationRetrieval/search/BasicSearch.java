package informationRetrieval.search;

import informationRetrieval.models.Document;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.Posting;
import informationRetrieval.models.SearchResult;
import informationRetrieval.models.Term;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BasicSearch implements SearchStrategy {

	public int searchSetting;
	public static final int SEARCH_SETTING_OR = 1;
	public static final int SEARCH_SETTING_AND = 2;

	public BasicSearch() {
		super();
		this.searchSetting = this.SEARCH_SETTING_OR;
	}

	public BasicSearch(int searchSetting) {
		super();
		this.searchSetting = searchSetting;
	}

	@Override
	public List<SearchResult> search(InvertedIndex index, List<String> searchTerms) {
		List<SearchResult> queryResults = new ArrayList<>();
		List<Posting> postingList = new ArrayList<>();

		List<Posting> andPostingList = new ArrayList<>();
		List<Posting> orPostingList = new ArrayList<>();
		boolean first = true;

		// Searching for the documents and their document-query score
		for (String searchTerm : searchTerms) {
			Term indexTerm = index.getTerm(searchTerm);

			if (indexTerm == null)
				continue;

			for (Posting posting : indexTerm.postings) {
				orPostingList.add(posting);
			}

			if (first) {
				andPostingList.addAll(orPostingList);
				first = false;
			} else {
				andPostingList.retainAll(orPostingList);
			}
		}

		switch (searchSetting) {
		case SEARCH_SETTING_OR:
			postingList = orPostingList;
			break;
		case SEARCH_SETTING_AND:
			postingList = andPostingList;
			break;
		default:
			System.err.println("Error: Invalid Search Setting");
		}

		// Building the Result List

		DocumentManager dm = DocumentManager.getInstance();

		for (Posting posting : postingList) {
			if (!queryResults.contains(posting)) {
				SearchResult res = new SearchResult(dm.getDocumentByNumber(posting.documentNumber), 1.0);
				queryResults.add(res);
			}
		}

		return queryResults;
	}
}
