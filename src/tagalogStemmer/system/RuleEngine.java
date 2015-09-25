package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.StemmerResult;

public class RuleEngine {
	
	private StemmerResult result;

	
	public RuleEngine(){
	}
	
	public void initializeWithWord(String word){
		this.result = StemmerResult.getNullResult(word);
	}
	
	public StemmerResult getResult(){
		return result;
	}

	public void apply(Routine routine) {
		StemmerResult tempResult;
		for(Rule rule : routine.getRules()){
			tempResult = rule.apply(result);
			
			// TODO: Checking conditions to see if rule application is valid
		}
	}
}
