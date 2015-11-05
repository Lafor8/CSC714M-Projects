package informationRetrieval.normalization;

public class Trimmer implements Normalizer {

	@Override
	public String normalize(String word) {
		return word.trim();
	}

}
