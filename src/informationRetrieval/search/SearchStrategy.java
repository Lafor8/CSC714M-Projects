package informationRetrieval.search;

import informationRetrieval.models.Document;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.SearchResult;

import java.util.List;

public interface SearchStrategy {

	List<SearchResult> search(InvertedIndex index, List<String> searchTerms);

}