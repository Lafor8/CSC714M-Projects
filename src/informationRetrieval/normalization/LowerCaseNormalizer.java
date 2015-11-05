package informationRetrieval.normalization;

import java.util.ArrayList;
import java.util.List;

public class LowerCaseNormalizer implements Normalizer {

	@Override
	public List<String> normalize(String word) {
		List<String> normalized = new ArrayList<String>();
		normalized.add(word.toLowerCase());
		return normalized;
	}
}
