package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class ParentChecker {

	public static boolean isChildOfParent(Tree parent, Tree child) {
		if (parent != null)
			for (Tree t : parent) {
				if (t.equals(child))
					return true;
			}
		return false;
	}
}
