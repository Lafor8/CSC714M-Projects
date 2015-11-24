package informationRetrieval.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SearchResult {

	public Document document;
	public double score;

	public SearchResult(Document document, double score) {
		this.document = document;
		this.score = score;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		NumberFormat formatter = new DecimalFormat("#0.000");     
		
		sb.append(formatter.format(score));
		sb.append(" - ");
		sb.append(document);
		sb.append("\n");
		
		return sb.toString();
	}
}
