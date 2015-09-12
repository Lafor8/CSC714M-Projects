package models;

import java.io.File;
import java.util.ArrayList;

public class ArticleFile {
	public ArrayList<Article> articles = new ArrayList<Article>();
	public File file;

	public ArticleFile(File file) {
		this.file = file;
	}
	public ArticleFile(String filePath) {
		this.file = new File(filePath);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("File: " + file.getName() + "\n\n");

		for (Article a : articles) {
			sb.append(a.toString());
		}

		return sb.toString();
	}
	
	public String toString(int n) {
		StringBuilder sb = new StringBuilder();

		sb.append("File: " + file.getName() + "\n\n");

		for (int i = 0; i < n; i++){
			sb.append(articles.get(i).toString());
		}

		return sb.toString();
	}
}
