package tagalogStemmer.system;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import tagalogStemmer.models.Word;

public class MetricsCalculator {

	public static void calculateAndPrint(LinkedHashMap<String, Word> correct, LinkedHashMap<String, Word> generated) {

		int tp = 0;
		int fp = 0;
		int tn = 0;
		int fn = 0;

		ArrayList<Word> tpList = new ArrayList<Word>();
		ArrayList<Word> fpList = new ArrayList<Word>();
		ArrayList<Word> tnList = new ArrayList<Word>();
		ArrayList<Word> fnList = new ArrayList<Word>();

		for (String generatedString : generated.keySet()) {
			Word correctWord = correct.get(generatedString);
			Word generatedWord = generated.get(generatedString);

			if (correctWord != null && generatedWord != null) {

				if (correctWord.currWord.equals(generatedWord.currWord)) {

					// word is not stemmable, and program rightly left it as is
					if (correctWord.isBaseWordEqualToCurrWordIgnoreHyphen()) {
						tnList.add(generatedWord);
						tn++;
					} else {
						// word is stemmable, and program stemmed it correctly
						tpList.add(generatedWord);
						tp++;
					}

				} else {
					// word is not stemmable, and program stemmed it
					if (correctWord.isBaseWordEqualToCurrWordIgnoreHyphen()) {
						fpList.add(generatedWord);
						fp++;
					} else {
						// word is stemmable, and program did not stem it or
						// stemmed
						// it wrongly
						fnList.add(generatedWord);
						fn++;
					}

				}
			}

		}

		System.out.println("TP: " + tp);
		System.out.println("FP: " + fp);
		System.out.println("TN: " + tn);
		System.out.println("FN: " + fn);

		double precision = tp * 1.0 / (tp + fp);
		double recall = tp * 1.0 / (tp + fn);
		double fmeasure = 2 * (precision * recall) / (precision + recall);
		System.out.println("Precision: " + precision);
		System.out.println("Recall: " + recall);
		System.out.println("F-Measure: " + fmeasure);

		FileWriter fw;
		try {
			fw = new FileWriter("output/results.csv");

			fw.write("True Positives\n");
			fw.write(generateCSVString(tpList));
			fw.write("\n");

			fw.write("False Positives\n");
			fw.write(generateCSVString(fpList));
			fw.write("\n");

			fw.write("True Negatives\n");
			fw.write(generateCSVString(tnList));
			fw.write("\n");

			fw.write("False Negatives\n");
			fw.write(generateCSVString(fnList));
			fw.write("\n");

			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String generateCSVString(ArrayList<Word> wordList) {
		StringBuilder sb = new StringBuilder();
		for (Word word : wordList) {
			sb.append(word.baseWord + "," + word.currWord + "\n");
		}

		return sb.toString();

	}
}
