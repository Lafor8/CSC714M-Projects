package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class PrepositionParser {

	// by - actor
	// with, from, on - constraints
	// to - jurisdiction

	public static Tree getNounPhrase(Tree tree, String preposition, boolean getBaseNP) {
		for (Tree t : tree.children()) {
			if (t.yield().get(0).value().equals(preposition)) {
				for (Tree child : t.children())
					if (child.value().equals("NP") && getBaseNP == true) {
						return NounPhraseParser.getBaseNounPhrase(child);
					} else if (child.value().equals("NP")) {
						return child;
					}
			} else if (t.value().equals("NP")) {
				for (Tree child : t.children()) {
					if (child.yield().get(0).value().equals(preposition)) {
						for (Tree x : child.children())
							if (x.value().equals("NP") && getBaseNP == true) {
								return NounPhraseParser.getBaseNounPhrase(x);
							} else if (x.value().equals("NP")) {
								return x;
							}
					}
				}
			}
		}
		return null;
	}
}
