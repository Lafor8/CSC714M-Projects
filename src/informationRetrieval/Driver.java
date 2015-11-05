package informationRetrieval;

import informationRetrieval.index.InvertedIndexer;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.normalization.LowerCaseNormalizer;
import informationRetrieval.normalization.Trimmer;
import informationRetrieval.tokenization.RegexTokenizer;

public class Driver {

	public static void main(String[] args) {

		DocumentManager dm = DocumentManager.getInstance();
		dm.populate("data/IR_Data");

		dm.tokenize(new RegexTokenizer("[\\p{Punct}\\s“”‘’–]+"));

		dm.normalize(new Trimmer());
		dm.normalize(new LowerCaseNormalizer());
		// dm.normalize(new StopWordRemover());
		InvertedIndex basicIndex = InvertedIndexer.index(dm.getDocumentsAsList());
		System.out.println(basicIndex);
	}
}
