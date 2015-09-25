package tagalogStemmer.system;

import tagalogStemmer.models.Rule;
import tagalogStemmer.models.Word;

public class RuleEngine {
	
	private Word word;

	
	public RuleEngine(){
	}
	
	public void initializeWithWord(String word){
		this.word = new Word(word);
	}
	
	public Word getResult(){
		return word;
	}

	public void apply(Routine routine) {
		Word tempWord;
		for(Rule rule : routine.getRules()){
			tempWord = rule.apply(word);
			
			// TODO: Checking conditions to see if rule application is valid
		}
	}
}
