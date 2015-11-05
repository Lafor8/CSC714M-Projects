package informationRetrieval.normalization;

import java.util.ArrayList;
import java.util.List;

public class StopWordRemover implements Normalizer {

	@Override
	public List<String> normalize(String word) {
		List<String> normalized = new ArrayList<String>();
		// TODO check if the word is in the list of stop words.
		// If yes, then return an empty list. Otherwise, return the word as is.
		normalized.add(word); // temporary stub
		return normalized;
	}

}
