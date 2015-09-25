package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.Word;
import tagalogStemmer.rulebase.ProductionRule;
import tagalogStemmer.rulebase.ReduplicationRule;
import tagalogStemmer.rulebase.Routine;

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
		{
			Routine routine3 = new Routine();
			routine3.addRule(new ProductionRule("in", "", ProductionRule.RULE_TYPE_INFIX, false));

			ruleEngine.apply(routine3);
		}

		// ROUTINE 4: Prefix Removal
		{
			Routine routine4 = new Routine();

			// * Rules from Documentation
			routine4.addRule(new ProductionRule("mag", "", ProductionRule.RULE_TYPE_PREFIX, true));

			// * Added Custom Rules
			routine4.addRule(new ProductionRule("ipagka", "", ProductionRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new ProductionRule("ipag", "", ProductionRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new ProductionRule("pagka", "", ProductionRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new ProductionRule("pag", "", ProductionRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new ProductionRule("in", "", ProductionRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new ProductionRule("ma", "", ProductionRule.RULE_TYPE_PREFIX, true));

			ruleEngine.apply(routine4);
		}

		// ROUTINE 5: /-um-/ Removal
		{
			Routine routine5 = new Routine();
			routine5.addRule(new ProductionRule("um", "", ProductionRule.RULE_TYPE_INFIX, false));

			ruleEngine.apply(routine5);
		}
		
		// ROUTINE 6: Partial Reduplication
		{
			Routine routine6 = new Routine();
			routine6.addRule(new ReduplicationRule(ReduplicationRule.RULE_TYPE_PARTIAL_REDUPLICATION));

			ruleEngine.apply(routine6);
		}

		// ROUTINE 7: Suffix Removal
		{
			Routine routine7 = new Routine();

			// * Rules from Documentation
			routine7.addRule(new ProductionRule("hin", "", ProductionRule.RULE_TYPE_SUFFIX, true));
			routine7.addRule(new ProductionRule("han", "", ProductionRule.RULE_TYPE_SUFFIX, true));
			routine7.addRule(new ProductionRule("in", "", ProductionRule.RULE_TYPE_SUFFIX, true));
			routine7.addRule(new ProductionRule("an", "", ProductionRule.RULE_TYPE_SUFFIX, true));

			// * Added Custom Rules

			ruleEngine.apply(routine7);
		}

		// TODO: Swap routine 4 and 7 (Swapping inquiry as explained in docu)

		// ROUTINE 8: Full Reduplication/Compounding
		// TODO:

		return ruleEngine.getResult();
	}
}
