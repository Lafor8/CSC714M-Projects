package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class PrepositionParser {

	// by - actor
	// with, from, on - constraints
	// to - jurisdiction

	public static Tree getNounPhrase(Tree tree, String preposition) {
		for (Tree t : tree.children())
			if (t.yield().get(0).value().equals(preposition))
				for (Tree child : t.children())
					if (child.value().equals("NP"))
						return child;
		return null;
	}
}
