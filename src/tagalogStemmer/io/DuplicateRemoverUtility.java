package tagalogStemmer.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

import tagalogStemmer.models.Word;

public class DuplicateRemoverUtility {

	public static void cleanCSV(String fileName) {

		// Read as ArrayList<Word>

		List<Word> correctWordList = readWordsFromCSV(fileName);

		LinkedHashSet<Word> correctWordSet = new LinkedHashSet<Word>();

		for (Word correctWord : correctWordList) {
			correctWordSet.add(correctWord);
		}

		try {
			FileWriter fw = new FileWriter("output/cleaned.csv");

			for (Word correctWord : correctWordSet) {
				fw.write(correctWord.baseWord + "," + correctWord.currWord + "\n");
			}

			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<Word> readWordsFromCSV(String fileName) {
		Scanner scanner;
		List<Word> wordList = new ArrayList<Word>();

		try {
			scanner = new Scanner(new FileReader(fileName));
			while (scanner.hasNextLine()) {

				String[] tokens = scanner.nextLine().split(",");

				if (tokens.length == 2) {
					String baseWord = tokens[0];
					String stemmed = tokens[1];

					Word word = new Word(baseWord);
					word.currWord = stemmed;

					wordList.add(word);
				}

			}

			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wordList;

	}
}
