package informationRetrieval.normalization;

public class LowerCaseNormalizer implements Normalizer {

	@Override
	public String normalize(String word) {
		return word.toLowerCase();
	}

}
