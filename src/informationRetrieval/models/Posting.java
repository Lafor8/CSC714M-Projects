package informationRetrieval.models;

public class Posting {

	public String filePath;
	public int documentNumber;
	public double tf;

	public Posting(Document document) {
		this.filePath = document.filePath;
		this.documentNumber = document.documentNumber;
	}

	public String toString() {
		return documentNumber + "-" + filePath;
	}
}
