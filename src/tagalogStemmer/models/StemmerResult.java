package tagalogStemmer.models;

import java.util.ArrayList;

public class StemmerResult {

	private String word;
	private ArrayList<String> segments;
	private String root;
	private int rootIndex;
	private boolean isRootFound;

	public StemmerResult(String word, ArrayList<String> segments, String root, int rootIndex) {
		isRootFound = true;
		this.word = word;
		this.segments = segments;
		this.root = root;
		this.rootIndex = rootIndex;
	}

	public StemmerResult(String word) {
		isRootFound = false;
		this.word = word;
		this.segments = new ArrayList<>();
		this.segments.add(word);
		this.root = word;
		this.rootIndex = 0;
	}

	public ArrayList<String> getSegments() {
		return this.segments;
	}

	public String getRoot() {
		return this.root;
	}
	
	public void setRoot(String root) {
		this.root = root;
	}
	
	public int getRootIndex() {
		return this.rootIndex;
	}
	
	public void setRootIndex(int rootIndex) {
		this.rootIndex = rootIndex;
	}

	public static StemmerResult getNullResult(String word) {
		return new StemmerResult(word);
	}

	public void setFinished(boolean finished) {
		//TODO: rootIndex
		this.isRootFound = finished;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (!isRootFound) {
			sb.append("Error: No Root Found for ");
			sb.append(this.word);
		} else {
			sb.append(this.word);
			sb.append("(");
			sb.append(this.root);
			sb.append(")");
			sb.append(" = ");

			for (int i = 0; i < segments.size(); ++i) {
				if (i != 0)
					sb.append(" + ");
				sb.append(segments.get(i));
			}
		}

		return sb.toString();
	}

	public boolean equals(StemmerResult b) {
		if (this.isRootFound == b.isRootFound && this.word.equals(b.word) && this.segments.size() == b.segments.size())
			return true;
		else
			return false;
	}
}
