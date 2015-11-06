package informationRetrieval;

import informationRetrieval.index.IDFExtender;
import informationRetrieval.index.InvertedIndexer;
import informationRetrieval.index.TFExtender;
import informationRetrieval.models.Document;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.normalization.LowerCaseNormalizer;
import informationRetrieval.normalization.StopWordRemover;
import informationRetrieval.normalization.Trimmer;
import informationRetrieval.search.BasicSearch;
import informationRetrieval.search.SearchStrategy;
import informationRetrieval.search.TFIDFSearch;
import informationRetrieval.search.TFSearch;
import informationRetrieval.tokenization.RegexTokenizer;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) {

		/* Load Data */
		DocumentManager dm = DocumentManager.getInstance();
		dm.populate("data/IR_Data");

		/* Tokenization and Normalization */
		dm.tokenize(new RegexTokenizer("[\\p{Punct}\\s“”‘’–]+"));

		dm.normalize(new Trimmer());
		dm.normalize(new LowerCaseNormalizer());
		dm.normalize(new StopWordRemover());
		// TODO: implement stop word remover
		// feel free to add new normalization modules (like expanding
		// contractions e.g. aso't pusa -> aso at pusa))
		// look at the normalization package. implement the normalizer
		// interface then call dm.normalize() here with the new module.

		/* Inverted Index Generation */

		// the extender classes clone the index passed to it for extension, so
		// there are three different indices: basic, tf, and tfidf
		InvertedIndex basicIndex = InvertedIndexer.index(dm.getDocumentsAsList());
		InvertedIndex tfIndex = TFExtender.extend(basicIndex);
		InvertedIndex tfIdfIndex = IDFExtender.extend(tfIndex);

		//System.out.println(tfIndex);

		/* Get input from the user */

		// TODO: ask for search string from user.
		// once the search string is acquired from the user,
		// tokenize and normalize into a List<String> object
		List<String> searchTerms = new ArrayList<String>();
		searchTerms.add("world");
		searchTerms.add("worth");

		/* Perform the actual search */

		// TODO: implement these three search strategies
		SearchStrategy basic = new BasicSearch();
		SearchStrategy tf = new TFSearch();
		SearchStrategy tfIdf = new TFIDFSearch();

		List<Document> basicResults = basic.search(basicIndex, searchTerms);
		List<Document> tfResults = tf.search(tfIndex, searchTerms);
		List<Document> tfIdfResults = tfIdf.search(tfIdfIndex, searchTerms);

		/* Display output based on results */
		System.out.println(tfResults);
	}
}
