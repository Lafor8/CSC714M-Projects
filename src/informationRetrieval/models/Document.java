package informationRetrieval.models;

import java.util.List;

public class Document {

	public int documentNumber;
	public String fileName;
	public String text;
	public List<String> tokens;

	public Document(String fileName, String text) {
		this.fileName = fileName;
		this.text = text;
	}

}
