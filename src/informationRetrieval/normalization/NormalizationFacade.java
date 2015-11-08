package informationRetrieval.normalization;

import java.util.ArrayList;
import java.util.List;

public class NormalizationFacade {

	public List<Normalizer> normalizationModules = new ArrayList<Normalizer>();

	public NormalizationFacade(List<Normalizer> normalizationModules) {
		this.normalizationModules = normalizationModules;
	}

	public List<String> normalize(List<String> stringList) {

		for (Normalizer normalizer : normalizationModules) {
			List<String> normalized = new ArrayList<String>();
			for (String string : stringList) {
				normalized.addAll(normalizer.normalize(string));
			}
			stringList = normalized;
		}

		return stringList;
	}
}
