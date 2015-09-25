package tagalogStemmer.system;

import tagalogStemmer.models.StemmerResult;

public interface Rule {
	public StemmerResult apply(StemmerResult result);
}
