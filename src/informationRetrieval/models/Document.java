package informationRetrieval.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		NumberFormat formatter = new DecimalFormat("000");    
		
		sb.append("#");
		sb.append(formatter.format(documentNumber));
		sb.append(": ");
		sb.append(filePath);
		
		return sb.toString();
	}
}
