package tagalogStemmer.models;

import java.util.ArrayList;

public class TagalogStemmerResult {

	private String word;
	private ArrayList<String> segments;
	private int rootIndex;
	private boolean isRootFound;

	public TagalogStemmerResult(String word, ArrayList<String> segments, int rootIndex) {
		isRootFound = true;
		this.word = word;
		this.segments = segments;
		this.rootIndex = rootIndex;
	}

	public TagalogStemmerResult(String word) {
		isRootFound = false;
		this.word = word;
	}

	public static TagalogStemmerResult getNullResult(String word) {
		return new TagalogStemmerResult(word);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (!isRootFound) {
			sb.append("Error: No Root Found for ");
			sb.append(this.word);
		} else {
			sb.append(this.word);
			sb.append(" = ");

			for (int i = 0; i < segments.size(); ++i) {
				if (i != 0)
					sb.append(" + ");
				if (i == rootIndex) {
					sb.append("(");
					sb.append(segments.get(i));
					sb.append(")");
				} else
					sb.append(segments.get(i));
			}
		}

		return sb.toString();
	}
}
