package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class SubjectParser {

	public static Tree getSubject(Tree clause) {
		// TODO
		for (Tree t : clause) {
			if (t.value().equals("NP")) {
				return t;
			}
		}
		return null;
	}

}
