package tagalogStemmer.rulebase;

import tagalogStemmer.models.Rule;
import tagalogStemmer.models.Word;

public class HyphenRemovalRule implements Rule {

	@Override
	public Word apply(Word input) {
		String word = input.currWord;
		String history = "ROUTINE 1: ";
		if (word.contains("-")) {
			word = word.replaceAll("-", "");
			history += "Removed hyphens: " + input.baseWord + " = " + word;

			input.applyChanges(word, history);
		}
		return input;
	}

}
