package informationExtraction.inputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser {

	private String currChapter = "";
	private String currSection = "";
	private String currSentence = "";
	private String mergeString = "";

	private boolean ignoreNextLine;
	private boolean isMerging;

	private static void printLines(String[] lines) {
		for (String line : lines)
			System.out.println(line);
	}

	public List<Sentence> parse(String[] lines) {

		List<Sentence> sentenceList = new ArrayList<Sentence>();

		for (String line : lines) {

			if (ignoreNextLine) {
				ignoreNextLine = false;
				continue;
			}

			if (line.indexOf("The following") == 0) {
				continue;
			}

			String[] lineTokens = line.split("\\s+");

			for (int tokenIndex = 0; tokenIndex < lineTokens.length; tokenIndex++) {

				String currToken = lineTokens[tokenIndex];

				if (currToken.equals("Appendix") && tokenIndex == 0)
					return sentenceList;

				boolean isSection = currToken.matches("\\d+(\\.\\d+)+");
				String nextToken = tokenIndex + 1 < lineTokens.length ? lineTokens[tokenIndex + 1] : "";

				if (currToken.contains("Chapter") && tokenIndex == 0) {
					currChapter = lineTokens[tokenIndex + 1];
					currSentence = "";
					currSection = "Chapter " + currChapter;
					break;
				} else if (isSection) {

					boolean isNextTokenLetter = nextToken.contains(")");
					boolean isNextTokenLast = tokenIndex + 2 >= lineTokens.length;

					// 1.1.1 // 1.1.1 a)
					if (nextToken.isEmpty() || (isNextTokenLetter && isNextTokenLast))
						ignoreNextLine = true;

					if (isNextTokenLetter)
						currSection = (currToken + " " + nextToken).trim();
					else
						currSection = currToken.trim();

					if (currToken.matches("\\d+(\\.\\d){2}")) {
						sentenceList = deleteDuplicates(sentenceList, currSection);
					}

					currSentence = "";

				} else if (currToken.matches(".*\\)")) {
					currSentence = "";
					break;
				} else if (shouldBeIgnored(currToken)) {
					currSentence = "";
				} else if (currToken.matches("[a-z].*:")) {
					mergeString = currSentence + " " + currToken.substring(0, currToken.length() - 1);
					currSentence = "";
					isMerging = true;
				} else if (currToken.equals("•")) {
					if (isMerging && !currSentence.trim().isEmpty()) {
						saveMergedSentence(sentenceList);
					}

					currSentence = "";

					if (!isMerging)
						break;

				} else if (currToken.equals("To")) {
					currSentence = "The policy-doer should";
				} else {
					currSentence += " " + currToken;

					if (currToken.matches(".*\\.")) {
						if (isMerging) {
							saveMergedSentence(sentenceList);
							isMerging = false;

							mergeString = "";
						} else {
							saveSentence(sentenceList);
						}

					}
				}
			}

		}
		return sentenceList;
	}

	private List<Sentence> deleteDuplicates(List<Sentence> sentenceList, String section) {

		List<Sentence> newList = new ArrayList<Sentence>();

		for (Sentence sentence : sentenceList) {
			if (sentence.getSection() == null || !sentence.getSection().equals(section))
				newList.add(sentence);
		}

		return newList;
	}

	private void saveMergedSentence(List<Sentence> sentenceList) {
		currSentence = currSentence.trim();
		currSentence = (currSentence.charAt(0) + "").toLowerCase() + currSentence.substring(1);
		String newSentence = addPeriod((mergeString.trim() + " " + currSentence.trim()).trim());
		sentenceList.add(new Sentence(newSentence, currSection, currChapter));
		currSentence = "";
	}

	private String addPeriod(String string) {
		string = string.trim();

		char lastChar = string.charAt(string.length() - 1);
		if (lastChar == ';')
			string = string.substring(0, string.length() - 1);

		if (lastChar != '.')
			string += ".";

		return string;
	}

	private void saveSentence(List<Sentence> sentenceList) {
		currSentence = addPeriod(currSentence);
		sentenceList.add(new Sentence(currSentence, currSection, currChapter));
		currSentence = "";
	}

	private boolean shouldBeIgnored(String token) {

		token = token.trim();

		return token.matches("[A-Z].*:") || token.matches(".*\\)") || token.matches(".*\\?");
		// skip bullet
	}
}
