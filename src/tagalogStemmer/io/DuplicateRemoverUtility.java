package tagalogStemmer.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import tagalogStemmer.models.Word;

public class DuplicateRemoverUtility {

	public static void cleanCSV(String fileName) {

		// Read as ArrayList<Word>

		List<Word> correctWordList = CSVReader.readWordsFromCSV(fileName);

		LinkedHashSet<Word> correctWordSet = convertListToHashSet(correctWordList);

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

	public static LinkedHashSet<Word> convertListToHashSet(List<Word> wordList) {

		LinkedHashSet<Word> wordSet = new LinkedHashSet<Word>();

		for (Word word : wordList) {
			wordSet.add(word);
		}

		return wordSet;

	}

	public static LinkedHashMap<String, Word> convertListToHashMap(List<Word> wordList) {

		LinkedHashMap<String, Word> wordMap = new LinkedHashMap<String, Word>();

		for (Word word : wordList) {
			wordMap.put(word.baseWord, word);
		}

		return wordMap;

	}

}
