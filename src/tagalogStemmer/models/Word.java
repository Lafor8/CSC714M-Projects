package tagalogStemmer.models;

import java.util.ArrayList;

public class Word {

	public String baseWord;
	public String currWord;
	public ArrayList<String> history;
	public StringBuilder printHistory;

	public Word(String word) {
		this.baseWord = word;
		this.currWord = word;
		this.history = new ArrayList<>();
		this.printHistory = new StringBuilder();

		this.history.add(word);

		this.printHistory.append("base word: ");
		this.printHistory.append(word);
		this.printHistory.append("\n");
	}

	public void addToHistory(String text) {
		this.printHistory.append(text);
		this.printHistory.append("\n");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (this.baseWord.equals(this.currWord)) {
			// sb.append("NOTICE: ");
			// sb.append(this.baseWord);
			// sb.append(" was not stemmed");
			// sb.append("\n");
		} else {
			sb.append(this.baseWord);
			sb.append(" = ");
			sb.append(this.currWord);
			sb.append("\n");
		}

		return sb.toString();
	}

	public String getPrintableWordHistory() {
		if (this.baseWord.equals(this.currWord)) {
			// TODO: what to do if untrimmed or cannot trim
			
			return "";
		} else {
			this.printHistory.append(this.toString() + "\n");

			return this.printHistory.toString();
		}
	}
}
