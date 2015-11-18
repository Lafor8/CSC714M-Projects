package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class DOBJParser {

	public static Tree getDirectObject(Tree verb) {
		for (Tree t : verb.children()) {
			if (t.value().equals("NP"))
				return t;
		}
		return null;
	}

}
