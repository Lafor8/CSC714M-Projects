package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.TagalogStemmerResult;

public class TagalogStemmer {
	
	public TagalogStemmer(){
		//TODO: Load Dictionary
	}

	public ArrayList<TagalogStemmerResult> stemWordsFromList(ArrayList<String> words) {
		ArrayList<TagalogStemmerResult> results = new ArrayList<>();
	
		for(String word : words){
			TagalogStemmerResult result;
			
			result = this.stemWord(word);
			
			results.add(result);
		}
		
		return results;
	}
	
	public TagalogStemmerResult stemWord(String word){
		
		//TODO: Stem word
		
		return TagalogStemmerResult.getNullResult();
	}
}
