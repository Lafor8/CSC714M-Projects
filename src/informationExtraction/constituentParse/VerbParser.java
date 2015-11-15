package informationExtraction.constituentParse;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.Tree;

public class VerbParser {

	public static String[] linkingVerbs = { "be", "is", "are", "was", "were", "can", "will", "may", "had", "has",
			"have", "should", "am", "might", "could", "would", "seem" };

	public static Tree getVerbPhraseTreeFromClause(Tree clause) {
		for (Tree t : clause.children())
			if (t.value().equals("VP") && hasANonLinkingVerb(t))
				return t;
			else if (t.value().equals("VP")) {
				return findInnerVerbPhrase(t);
			}
		return null;
	}

	private static Tree findInnerVerbPhrase(Tree tree) {
		for (Tree t : tree) {
			if (t.value().equals("VP") && !t.equals(tree) && hasANonLinkingVerb(t))
				return t;
		}
		return null;
	}

	private static boolean hasANonLinkingVerb(Tree verbTree) {
		for (Tree t : verbTree.children()) {
			if (!t.isPhrasal() && isNotALinkingVerb(t)) {
				return true;
			} else if (t.value().equals("VP"))
				return hasANonLinkingVerb(t);
		}
		return false;
	}

	private static boolean isNotALinkingVerb(Tree t) {
		String word = t.yield().get(0).value().toLowerCase();
		for (String lv : linkingVerbs)
			if (lv.equals(word))
				return false;
		return true;
	}

	public static Tree[] getVerbPhrases(Tree clause) {
		Tree verbPhraseTree = VerbParser.getVerbPhraseTreeFromClause(clause);

		Tree[] multipleVerbPhrases = getMultipleVerbPhrases(verbPhraseTree);
		if (multipleVerbPhrases != null) {
			for (int i = 0; i < multipleVerbPhrases.length; i++) {
				Tree t = multipleVerbPhrases[i];
				multipleVerbPhrases[i] = getBaseVerbPhrase(t);
			}
			return multipleVerbPhrases;
		} else {
			List<Tree> verbs = new ArrayList<>();
			verbs.add(getBaseVerbPhrase(verbPhraseTree));
			return verbs.toArray(new Tree[verbs.size()]);
		}
	}

	private static Tree getBaseVerbPhrase(Tree verbPhraseTree) {
		for (Tree t : verbPhraseTree.children())
			if (t.value().equals("VP"))
				return getBaseVerbPhrase(t);
		return verbPhraseTree;
	}

	private static Tree[] getMultipleVerbPhrases(Tree verbPhraseTree) {
		for (Tree t : verbPhraseTree)
			if (t.value().equals("CC")) {
				Tree[] siblings = getVPSiblingsOfCC(verbPhraseTree, t);
				if (siblings != null && siblings.length >= 2)
					return siblings;
			}
		return null;
	}

	private static Tree[] getVPSiblingsOfCC(Tree rootTree, Tree cc) {
		List<Tree> siblings = new ArrayList<>();

		boolean hasTheCC = false;

		for (Tree t : rootTree.children()) {
			if (t.value().equals("VP") && !t.equals(cc))
				siblings.add(t);
			else if (t.equals(cc))
				hasTheCC = true;
		}
		if (hasTheCC)
			return siblings.toArray(new Tree[siblings.size()]);
		else {
			if (rootTree.isPhrasal())
				for (Tree t : rootTree.children()) {
					Tree[] s = getVPSiblingsOfCC(t, cc);
					if (s != null)
						return s;
				}
		}
		return null;

	}
}
