package informationRetrieval;

import informationRetrieval.index.IDFExtender;
import informationRetrieval.index.InvertedIndexer;
import informationRetrieval.index.TFExtender;
import informationRetrieval.models.Document;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.Posting;
import informationRetrieval.models.SearchResult;
import informationRetrieval.normalization.LowerCaseNormalizer;
import informationRetrieval.normalization.NormalizationFacade;
import informationRetrieval.normalization.Normalizer;
import informationRetrieval.normalization.StopWordRemover;
import informationRetrieval.normalization.Trimmer;
import informationRetrieval.search.BasicSearch;
import informationRetrieval.search.SearchStrategy;
import informationRetrieval.search.TFIDFSearch;
import informationRetrieval.search.TFSearch;
import informationRetrieval.tokenization.RegexTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.util.ConsoleInput;

public class Driver {

	static InvertedIndex basicIndex;
	static InvertedIndex tfIndex;
	static InvertedIndex tfIdfIndex;

	public static void main(String[] args) {
		// generateAndSaveIndices();
		loadIndices();
		// generateAndInitializeIndices();
		searchRoutine();
	}

	static void loadIndices() {
		basicIndex = InvertedIndex.load("data/IR_Indices/basic.txt");
		tfIndex = InvertedIndex.load("data/IR_Indices/tf.txt");
		tfIdfIndex = InvertedIndex.load("data/IR_Indices/tf_idf.txt");
	}

	static void generateAndSaveIndices() {
		/* Load Data */
		DocumentManager dm = DocumentManager.getInstance();

		/* Tokenization and Normalization */
		RegexTokenizer tokenizer = RegexTokenizer.createWhiteSpacePunctuationTokenizer();

		dm.tokenize(tokenizer);

		List<Normalizer> normalizationModules = new ArrayList<Normalizer>();
		normalizationModules.add(new Trimmer());
		normalizationModules.add(new LowerCaseNormalizer());
		normalizationModules.add(new StopWordRemover("data/fil-function-words.txt"));
		dm.normalize(normalizationModules);

		/* Inverted Index Generation */

		// the extender classes clone the index passed to it for extension, so
		// there are three different indices: basic, tf, and tfidf
		InvertedIndex basicIndex = InvertedIndexer.index(dm.getDocumentsAsList());
		InvertedIndex tfIndex = TFExtender.extend(basicIndex);
		InvertedIndex tfIdfIndex = IDFExtender.extend(tfIndex);

		basicIndex.save("data/IR_Indices/basic.txt");
		tfIndex.save("data/IR_Indices/tf.txt");
		tfIdfIndex.save("data/IR_Indices/tf_idf.txt");

	}

	static void searchRoutine() {
		/* Initialization */

		// Tokenization
		RegexTokenizer tokenizer = RegexTokenizer.createWhiteSpacePunctuationTokenizer();

		// Normalization
		List<Normalizer> normalizationModules = new ArrayList<Normalizer>();
		normalizationModules.add(new Trimmer());
		normalizationModules.add(new LowerCaseNormalizer());
		normalizationModules.add(new StopWordRemover("data/fil-function-words.txt"));
		NormalizationFacade normalizationFacade = new NormalizationFacade(normalizationModules);

		// Search Strategies
		SearchStrategy basic = new BasicSearch(); // BasicSearch.SEARCH_SETTING_AND);
		SearchStrategy tf = new TFSearch();
		SearchStrategy tfIdf = new TFIDFSearch();

		/* Continually get input from the user */
		String searchQuery;

		while (!(searchQuery = ConsoleInput.promptUserForInput("Search (/q to quit): ")).equals("/q")) {

			/* Tokenize and normalize search terms */
			List<String> searchTerms = tokenizer.tokenize(searchQuery);
			searchTerms = normalizationFacade.normalize(searchTerms);

			/* Perform the actual search */
			List<SearchResult> basicResults = basic.search(basicIndex, searchTerms);
			List<SearchResult> tfResults = tf.search(tfIndex, searchTerms);
			List<SearchResult> tfIdfResults = tfIdf.search(tfIdfIndex, searchTerms);

			/* Display output based on results */
			System.out.println("Search Terms: " + searchTerms + "\n");
			System.out.println("Query Results (Basic): " + basicResults.size()+" Item(s) found.\n" + basicResults + "\n\n");
			System.out.println("Query Results (TF): " + tfResults.size()+" Item(s) found.\n"+ tfResults + "\n\n");
			System.out.println("Query Results (TF.IDF): " + tfIdfResults.size()+" Item(s) found.\n"+ tfIdfResults + "\n\n");
		}
	}

	static void generateAndInitializeIndices() {
		/* Load Data */
		DocumentManager dm = DocumentManager.getInstance();

		/* Tokenization and Normalization */
		RegexTokenizer tokenizer = RegexTokenizer.createWhiteSpacePunctuationTokenizer();

		dm.tokenize(tokenizer);

		List<Normalizer> normalizationModules = new ArrayList<Normalizer>();
		normalizationModules.add(new Trimmer());
		normalizationModules.add(new LowerCaseNormalizer());
		normalizationModules.add(new StopWordRemover("data/fil-function-words.txt"));
		dm.normalize(normalizationModules);
		// feel free to add new normalization modules (like expanding
		// contractions e.g. aso't pusa -> aso at pusa))
		// look at the normalization package. implement the normalizer
		// interface then call dm.normalize() here with the new module.
		// this is iterative so order of normalization modules matters

		/* Inverted Index Generation */

		// the extender classes clone the index passed to it for extension, so
		// there are three different indices: basic, tf, and tfidf
		basicIndex = InvertedIndexer.index(dm.getDocumentsAsList());
		tfIndex = TFExtender.extend(basicIndex);
		tfIdfIndex = IDFExtender.extend(tfIndex);
	}
}
