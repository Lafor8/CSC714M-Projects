package informationRetrieval.normalization;

import java.util.ArrayList;
import java.util.List;

public class Trimmer implements Normalizer {

	@Override
	public List<String> normalize(String word) {
		List<String> normalized = new ArrayList<String>();
		normalized.add(word.trim());
		return normalized;
	}

}
