package informationRetrieval.normalization;

import java.util.List;

public interface Normalizer {
	// Takes a String then returns a List<String> containing the normalized
	// word(s).
	// It returns a list due to the possibility of expanding a word when
	// normalizing (e.g. expanding contractions)
	List<String> normalize(String word);
}
