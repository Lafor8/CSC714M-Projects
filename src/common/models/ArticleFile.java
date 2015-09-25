package common.models;

import java.io.File;
import java.util.ArrayList;

public class ArticleFile extends CustomFile{
	public ArrayList<Article> articles = new ArrayList<Article>();

	public ArticleFile(File file) {
		super(file);
	}
	
	public ArticleFile(String filePath) {
		super(filePath);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("File: " + file.getName() + "\n");

		for (Article a : articles) {
			sb.append(a.toString());
		}

		return sb.toString();
	}
	
	public String toString(int n) {
		StringBuilder sb = new StringBuilder();

		sb.append("File: " + file.getName() + "\n");

		for (int i = 0; i < n; i++){
			sb.append(articles.get(i).toString());
		}

		return sb.toString();
	}
}
