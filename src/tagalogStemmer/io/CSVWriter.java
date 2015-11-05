package tagalogStemmer.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

	public static final String outputFolder = "output/";

	public static void write(String fileName, List<String> wordList) {
		String toWrite = generateStringToWrite(wordList);
		try {
			FileWriter fw = new FileWriter(outputFolder + fileName);
			fw.append(toWrite);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String generateStringToWrite(List<String> wordList) {
		StringBuilder sb = new StringBuilder();
		for (String string : wordList)
			sb.append(string).append("\n");

		return sb.toString();
	}
}
