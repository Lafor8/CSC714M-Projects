package tagalogStemmer.system;

import java.util.LinkedHashMap;

import tagalogStemmer.models.Word;

public class MetricsCalculator {

	public static void calculateAndPrint(LinkedHashMap<String, Word> correct, LinkedHashMap<String, Word> generated) {

		int tp = 0;
		int fp = 0;
		int tn = 0;
		int fn = 0;

		for (String generatedString : generated.keySet()) {
			Word correctWord = correct.get(generatedString);
			Word generatedWord = generated.get(generatedString);

			if (correctWord != null && generatedWord != null) {

				if (correctWord.currWord.equals(generatedWord.currWord)) {

					// word is not stemmable, and program rightly left it as is
					if (correctWord.isBaseWordEqualToCurrWordIgnoreHyphen())
						tn++;
					else
						// word is stemmable, and program stemmed it correctly
						tp++;

				} else {
					// word is not stemmable, and program stemmed it
					if (correctWord.isBaseWordEqualToCurrWordIgnoreHyphen())
						fp++;
					else
						// word is stemmable, and program did not stem it or
						// stemmed
						// it wrongly
						fn++;

				}
			}

		}

		System.out.println("TP: " + tp);
		System.out.println("FP: " + fp);
		System.out.println("TN: " + tn);
		System.out.println("FN: " + fn);

	}
}
