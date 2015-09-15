package ner;

public class NGramIterator {

	public String text;

	public String[] tokens;
	public boolean[] isTokenAlreadyNamedEntity; // to mark whether a token is
												// already included as part of a
												// named entity

	private int cursorIndex;

	private int n;

	public NGramIterator(String text) {
		if (text == null)
			text = "";

		this.text = text.trim();
		this.tokens = this.text.split("\\s");

		this.isTokenAlreadyNamedEntity = new boolean[tokens.length];
		this.cursorIndex = 0;
	}

	public void restart(int newN) {
		this.n = newN;
		cursorIndex = 0;
	}

	public boolean hasNext() {
		return cursorIndex < tokens.length - n + 1;
	}

	public String next() {

		if (!hasNext())
			return null;

		StringBuilder nextStringBuilder = new StringBuilder();

		int i;
		for (i = cursorIndex; i < cursorIndex + n; i++) {
			// Encountered a token that should not be considered anymore. So
			// stop appending to the n-gram
			// if (isTokenAlreadyNamedEntity[i])
			// break;

			nextStringBuilder.append(tokens[i] + " ");
		}
		cursorIndex++;

		// System.out.println("Iterator: " + cursorIndex + " - " +
		// nextStringBuilder.toString().trim());
		return nextStringBuilder.toString().trim();
	}

	public void markLastNGramAsAlreadyProcessed() {

		// The current n-gram is already considered a named entity, so mark it
		// accordingly
		for (int i = cursorIndex - 1; i < cursorIndex - 1 + n && i < tokens.length; i++)
			isTokenAlreadyNamedEntity[i] = true;

		// Move the cursor to point after the marked n-gram
		cursorIndex = Math.min(tokens.length, cursorIndex + n);
	}

}
