package informationRetrieval.search;

import informationRetrieval.models.Document;
import informationRetrieval.models.InvertedIndex;

import java.util.List;

public interface SearchStrategy {

	List<Document> search(InvertedIndex index, List<String> searchTerms);

}
