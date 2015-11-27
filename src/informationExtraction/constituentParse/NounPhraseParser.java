package informationExtraction.constituentParse;

import edu.stanford.nlp.trees.Tree;

public class NounPhraseParser {

	public static Tree getBaseNounPhrase(Tree nounPhrase) {
		if (!hasCompoundNounPhrases(nounPhrase))
			for (Tree t : nounPhrase.children()) {
				if (t.value().equals("NP"))
					return getBaseNounPhrase(t);
			}
		return nounPhrase;
	}

	private static boolean hasCompoundNounPhrases(Tree nounPhrase) {
		for (Tree t : nounPhrase.children()) {
			if (t.value().equals(",") || t.value().equals("CC"))
				return true;
		}
		return false;
	}

	public static boolean containsActorWords(Tree nounPhrase, String[] actorWords) {
		for (Tree t : nounPhrase) {
			for (String actorWord : actorWords) {
				if (t.yield().get(0).value().toLowerCase().equals(actorWord))
					return true;
			}
		}
		return false;
	}

}
