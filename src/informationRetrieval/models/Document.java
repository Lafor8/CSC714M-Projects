package informationRetrieval.models;

public class Document {

	public int documentNumber;
	public String fileName;
	public String text;

	public Document(String fileName, String text) {
		this.fileName = fileName;
		this.text = text;
	}

}
