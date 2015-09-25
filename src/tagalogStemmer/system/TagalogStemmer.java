package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.Word;

public class TagalogStemmer {

	public TagalogStemmer() {
		// TODO: Load Dictionary
	}

	public ArrayList<Word> stemWordsFromList(ArrayList<String> inputWords) {
		ArrayList<Word> words = new ArrayList<>();

		for (String inputWord : inputWords) {
			Word word;

			word = this.stemWord(inputWord);

			words.add(word);
		}

		return words;
	}

	public Word stemWord(String word) {
		RuleEngine ruleEngine = new RuleEngine();

		// INITIALIZATION
		ruleEngine.initializeWithWord(word);

		// ROUTINE 1: Hyphen Search

		// ROUTINE 2: Dictionary Search

		// ROUTINE 3: /-in-/ Removal
		Routine routine3 = new Routine();
		routine3.addRule(new ProductionRule("in", "", ProductionRule.RULE_TYPE_INFIX));

		ruleEngine.apply(routine3);

		// ROUTINE 4: Prefix Removal
		Routine routine4 = new Routine();
		// routine4.addRule(new ProductionRule("i", "", ProductionRule.RULE_TYPE_PREFIX));
		// routine4.addRule(new ProductionRule("pag", "", ProductionRule.RULE_TYPE_PREFIX));
		// routine4.addRule(new ProductionRule("ka", "", ProductionRule.RULE_TYPE_PREFIX));
		routine4.addRule(new ProductionRule("mag", "", ProductionRule.RULE_TYPE_PREFIX));

		ruleEngine.apply(routine4);

		// ROUTINE 5: /-um-/ Removal
		Routine routine5 = new Routine();
		routine5.addRule(new ProductionRule("um", "", ProductionRule.RULE_TYPE_INFIX));

		ruleEngine.apply(routine5);
		// ROUTINE 6: Partial Reduplication
		// TODO:

		// ROUTINE 7: Suffix Removal

		Routine routine7 = new Routine();
		// routine4.addRule(new ProductionRule("i", "", ProductionRule.RULE_TYPE_PREFIX));
		// routine4.addRule(new ProductionRule("pag", "", ProductionRule.RULE_TYPE_PREFIX));
		// routine4.addRule(new ProductionRule("ka", "", ProductionRule.RULE_TYPE_PREFIX));
		routine7.addRule(new ProductionRule("hin", "", ProductionRule.RULE_TYPE_SUFFIX));
		routine7.addRule(new ProductionRule("han", "", ProductionRule.RULE_TYPE_SUFFIX));
		routine7.addRule(new ProductionRule("in", "", ProductionRule.RULE_TYPE_SUFFIX));
		routine7.addRule(new ProductionRule("an", "", ProductionRule.RULE_TYPE_SUFFIX));

		ruleEngine.apply(routine7);
		//TODO: Swap routine 4 and 7

		// ROUTINE 8: Full Reduplication/Compounding
		// TODO:

		return ruleEngine.getResult();
	}
}
