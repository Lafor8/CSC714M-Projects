package tagalogStemmer.system;

import tagalogStemmer.models.Word;

public interface Rule {
	public Word apply(Word word);
}
