package tagalogStemmer.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tagalogStemmer.models.Word;

public class CSVReader {

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
