package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class DOBJParser {

	public static Tree getDirectObject(Tree verb) {
		for (Tree t : verb.children()) {
			if (t.value().equals("NP"))
				return NounPhraseParser.getBaseNounPhrase(t);
		}
		return null;
	}

	public static Tree getPrepositionalPhraseOfDirectObject(Tree verb) {
		for (Tree t : verb.children()) {
			if (t.value().equals("NP")) {
				for (Tree c : t.children()) {
					if (c.value().equals("PP"))
						return c;
				}
			}
		}
		return null;
	}
}
