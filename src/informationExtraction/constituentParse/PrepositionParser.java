package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class PrepositionParser {

	// by - actor
	// with, from, on - constraints
	// to - jurisdiction

	public static Tree getNounPhrase(Tree verbTree, String preposition) {
		for (Tree t : verbTree.children())
			if (t.value().equals(preposition))
				for (Tree child : t.children())
					if (child.value().equals("NP"))
						return child;
		return null;
	}
}
