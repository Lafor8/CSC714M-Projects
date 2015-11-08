package informationRetrieval.normalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StopWordRemover implements Normalizer {

	List<String> stopWords = new ArrayList<String>();

	public StopWordRemover(String stopWordsFilePath) {
		File stopWordsFile = new File(stopWordsFilePath);
		Scanner scanner;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(stopWordsFile)));
			while (scanner.hasNext()) {
				stopWords.add(scanner.next());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> normalize(String word) {
		List<String> normalized = new ArrayList<String>();
		if (!stopWords.contains(word))
			normalized.add(word); // temporary stub
		return normalized;
	}

}
