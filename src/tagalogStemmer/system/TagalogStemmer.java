package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.Word;
import tagalogStemmer.rulebase.AffixRemovalRule;
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
			routine3.addRule(new AffixRemovalRule("in", "", AffixRemovalRule.RULE_TYPE_INFIX, false));

			ruleEngine.apply(routine3);
		}

		// ROUTINE 4: Prefix Removal
		{
			Routine routine4 = new Routine();

			// * Rules from Documentation
			routine4.addRule(new AffixRemovalRule("mag", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));

			// * Added Custom Rules
			routine4.addRule(new AffixRemovalRule("paka", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("ipagka", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("ipag", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("pagka", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("pag", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("nag", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("sipag", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("mang", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("in", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("ma", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));
			routine4.addRule(new AffixRemovalRule("ka", "", AffixRemovalRule.RULE_TYPE_PREFIX, true));

			ruleEngine.apply(routine4);
		}

		// ROUTINE 5: /-um-/ Removal
		{
			Routine routine5 = new Routine();
			routine5.addRule(new AffixRemovalRule("um", "", AffixRemovalRule.RULE_TYPE_INFIX, false));

			ruleEngine.apply(routine5);
		}

		// ROUTINE 6: Partial Reduplication
		{
			Routine routine6 = new Routine();
			routine6.addRule(new ReduplicationRule(ReduplicationRule.RULE_TYPE_PARTIAL_REDUPLICATION));

			ruleEngine.apply(routine6);
		}

		// ROUTINE 7: Suffix Remova l
		{
			Routine routine7 = new Routine();

			// * Rules from Documentation
			routine7.addRule(new AffixRemovalRule("hin", "", AffixRemovalRule.RULE_TYPE_SUFFIX, true));
			routine7.addRule(new AffixRemovalRule("han", "", AffixRemovalRule.RULE_TYPE_SUFFIX, true));
			routine7.addRule(new AffixRemovalRule("in", "", AffixRemovalRule.RULE_TYPE_SUFFIX, true));
			routine7.addRule(new AffixRemovalRule("an", "", AffixRemovalRule.RULE_TYPE_SUFFIX, true));

			// * Added Custom Rules

			ruleEngine.apply(routine7);
		}

		// TODO: Swap routine 4 and 7 (Swapping inquiry as explained in docu)

		// ROUTINE 8: Full Reduplication/Compounding
		{
			Routine routine8 = new Routine();

			routine8.addRule(new ReduplicationRule(ReduplicationRule.RULE_TYPE_FULL_REDUPLICATION));

			ruleEngine.apply(routine8);

		}

		return ruleEngine.getResult();
	}
}
