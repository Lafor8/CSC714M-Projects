package models;

public class Article {
	public String title;
	public String author;
	public String date;
	public String body;
	public String link;

	public Article(String title, String author, String date, String body, String link) {
		this.title = title;
		this.author = author;
		this.date = date;
		this.body = body;
		this.link = link;
	}

	public Article() {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Title: " + this.title + "\n");
		sb.append("Author: " + this.author + "\n");
		sb.append("Date: " + this.date + "\n");
		sb.append("Body: " + this.body + "\n");
		sb.append("Link: " + this.link + "\n");
		sb.append("\n");

		return sb.toString();
	}
}
