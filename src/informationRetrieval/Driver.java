package informationRetrieval;

import informationRetrieval.index.IDFExtender;
import informationRetrieval.index.InvertedIndexer;
import informationRetrieval.index.TFExtender;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.normalization.LowerCaseNormalizer;
import informationRetrieval.normalization.StopWordRemover;
import informationRetrieval.normalization.Trimmer;
import informationRetrieval.tokenization.RegexTokenizer;

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
		// feel free to add new normalization modules (like expanding
		// contractions e.g. aso't pusa -> aso at pusa))
		// look at the normalization package. implement the normalizer
		// interface then call dm.normalize() here with the new module.

		InvertedIndex basicIndex = InvertedIndexer.index(dm.getDocumentsAsList());
		// System.out.println(basicIndex);

		InvertedIndex tfIndex = TFExtender.extend(basicIndex);

		InvertedIndex tfIdfIndex = IDFExtender.extend(tfIndex);
		System.out.println(tfIdfIndex);

	}
}
