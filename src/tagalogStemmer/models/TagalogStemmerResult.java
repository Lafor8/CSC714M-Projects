package tagalogStemmer.models;

import java.util.ArrayList;

public class TagalogStemmerResult {
	
	private ArrayList<String> segments;
	private int rootIndex;
	private boolean isRootFound;
	
	public TagalogStemmerResult(ArrayList<String> segments, int rootIndex){
		isRootFound = true;
		this.segments= segments;
		this.rootIndex = rootIndex;
	}
	
	public TagalogStemmerResult(){
		isRootFound = false;
	}
	
	public static TagalogStemmerResult getNullResult(){
		return new TagalogStemmerResult();
	}

	public String toString() {
		if(!isRootFound)
			return "Error: No Root Found";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < segments.size(); ++i) {
			if (i != 0)
				sb.append(" + ");
			if (i == rootIndex)
				sb.append("(" + segments.get(i) + ")");
			else
				sb.append(segments.get(i));
		}

		return sb.toString();
	}
}
