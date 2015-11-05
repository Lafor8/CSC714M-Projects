package informationRetrieval;

import informationRetrieval.models.DocumentManager;
import informationRetrieval.tokenization.RegexTokenizer;

public class Driver {

	public static void main(String[] args) {

		DocumentManager dm = DocumentManager.getInstance();
		dm.populate("data/IR_Data");

		dm.tokenize(new RegexTokenizer("[\\p{Punct}\\s]+"));

	}
}
