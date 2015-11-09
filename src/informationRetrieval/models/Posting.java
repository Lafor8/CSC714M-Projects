package informationRetrieval.models;

public class Posting {

	public String filePath;
	public int documentNumber;
	public int tf;

	private Posting(String filePath, int documentNumber, int tf) {
		this.filePath = filePath;
		this.documentNumber = documentNumber;
		this.tf = tf;
	}

	public Posting(Document document) {
		this.filePath = document.filePath;
		this.documentNumber = document.documentNumber;
	}

	public String toString() {
		return documentNumber + " : '" + filePath + "' : " + tf;
	}

	public Posting clone() {
		return new Posting(filePath, documentNumber, tf);
	}

	public static Posting loadFromString(String string) {
		String[] tokens = string.split(":");

		String filePath = tokens[1].replaceAll("'", "").trim();
		int documentNumber = Integer.parseInt(tokens[0].trim());
		int tf = Integer.parseInt(tokens[2].trim());

		return new Posting(filePath, documentNumber, tf);
	}
}
