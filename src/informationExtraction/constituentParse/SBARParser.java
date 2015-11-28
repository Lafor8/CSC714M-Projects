package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class SBARParser {

	public static Tree getSBAR(Tree clause) {
		for (Tree t : clause) {
			if (t.value().equals("SBAR"))
				return t;
		}
		return null;
	}
}
