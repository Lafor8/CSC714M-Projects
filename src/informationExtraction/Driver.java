package informationExtraction;

import informationExtraction.constituentParse.FileManager;
import informationExtraction.constituentParse.InformationExtractor;
import informationExtraction.inputParser.InputParser;
import informationExtraction.inputParser.Sentence;

import java.io.IOException;
import java.util.List;

public class Driver {
	public static void main(String[] args) throws IOException {

		String[] lines = FileManager.read("isp.txt");

		// printLines(lines);

		List<Sentence> sentences = new InputParser().parse(lines);

		StringBuilder sb = new StringBuilder();

		for (Sentence sentence : sentences) {
			sb.append(sentence.toString()).append("\n");
		}

		// FileWriter fw = new FileWriter("sentences.txt");
		// fw.write(sb.toString());
		// fw.close();
		// System.out.println(sb.toString());

		List<ExtractionResult> results = InformationExtractor.parse(sentences);

		ResultFileWriter.writeToFile("isp-extraction.csv", results);

	}
}
