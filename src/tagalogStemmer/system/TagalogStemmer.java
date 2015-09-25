package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.Word;

public class TagalogStemmer {
	
	public TagalogStemmer(){
		//TODO: Load Dictionary
	}

	public ArrayList<Word> stemWordsFromList(ArrayList<String> inputWords) {
		ArrayList<Word> words = new ArrayList<>();
	
		for(String inputWord : inputWords){
			Word word;
			
			word = this.stemWord(inputWord);
			
			words.add(word);
		}
		
		return words;
	}
	
	public Word stemWord(String word){
		
		//TODO: Stem word
		RuleEngine ruleEngine = new RuleEngine();
		
		ruleEngine.initializeWithWord(word);
		// ROUTINE 1

		// ROUTINE 2
		// ROUTINE 3
		Routine routine3 = new Routine();
		routine3.addRule(new ProductionRule(".+in.+", "", ProductionRule.RULE_TYPE_INFIX));
		
		ruleEngine.apply(routine3);
		
		// ROUTINE 4

		// ROUTINE 5

		// ROUTINE 6

		// ROUTINE 7

		// ROUTINE 8
		
		return ruleEngine.getResult();
	}
}
