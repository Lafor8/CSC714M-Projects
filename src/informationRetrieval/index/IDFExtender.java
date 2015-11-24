package informationRetrieval.index;

import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.Term;

public class IDFExtender {

	public static InvertedIndex extend(InvertedIndex index) {

		InvertedIndex idfExtended = index.clone();

		DocumentManager dm = DocumentManager.getInstance();

		for (Term term : idfExtended.terms) {
			double N = dm.getNumDocuments() * 1.0;
			double df = term.getDocumentFrequency() * 1.0;
			term.idf = Math.log10(N / df);
		}

		return idfExtended;

	}
}
