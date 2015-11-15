package informationExtraction.constituentParse;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class ClauseParser {

	public static Tree[] getIndependentClauses(CoreMap sentence) {
		Tree rootTree = sentence.get(TreeAnnotation.class);
		// I'm assuming an independent clause to have its first child as a NP
		// and second child as VP. If it does not meet this criteria, its not
		// considered an independent clause.
		List<Tree> subjectTrees = new ArrayList<>();
		for (Tree t : rootTree) {
			if (t.value().equals("S") && t.children().length > 1 && hasNounPhraseBeforeVerbPhraseAtTop(t)) {
				subjectTrees.add(t);
			}
		}
		return subjectTrees.toArray(new Tree[subjectTrees.size()]);
	}

	private static boolean hasNounPhraseBeforeVerbPhraseAtTop(Tree tree) {
		int npIndex = Integer.MAX_VALUE;
		for (int i = 0; i < tree.children().length; i++) {
			Tree t = tree.children()[i];
			if (t.value().equals("NP"))
				npIndex = i;
			if (t.value().equals("VP")) {
				if (i > npIndex)
					return true;
				else
					return false;
			}
		}
		return false;
	}
}
