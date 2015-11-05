package informationRetrieval.index;

import informationRetrieval.models.Document;
import informationRetrieval.models.DocumentManager;
import informationRetrieval.models.InvertedIndex;
import informationRetrieval.models.Posting;
import informationRetrieval.models.Term;

import java.util.Collections;

public class TFExtender {

	public static InvertedIndex extend(InvertedIndex index) {

		InvertedIndex tfExtendedIndex = index.clone();

		DocumentManager dm = DocumentManager.getInstance();

		for (Term term : tfExtendedIndex.terms) {

			for (Posting posting : term.postings) {
				Document document = dm.getDocumentByNumber(posting.documentNumber);
				posting.tf = Collections.frequency(document.tokens, term.text);
			}

		}

		return tfExtendedIndex;

	}
}
