package informationRetrieval.models;

import java.util.List;

public class Document {

	public int documentNumber;
	public String filePath;
	public String text;
	public List<String> tokens;

	public Document(String filePath, String text) {
		this.filePath = filePath;
		this.text = text;
	}

	public boolean containsString(String string) {
		return tokens.contains(string);
	}

}
