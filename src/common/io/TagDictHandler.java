package common.io;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import common.models.Article;
import common.models.DictionaryFile;


/// A handler for the Filipino News and Opinyon Corpus
public class TagDictHandler extends DefaultHandler {

	StringBuilder sb = new StringBuilder();
	DictionaryFile dictionaryFile;
	Article currArticle;
	StringBuilder db = new StringBuilder();

	public TagDictHandler(DictionaryFile dictionaryFile) {
		this.dictionaryFile = dictionaryFile;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("ARTICLE"))
			currArticle = new Article();
		else if (qName.equalsIgnoreCase("DATE"))
			db.setLength(0);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		String data = sb.toString().trim();

		switch (qName.toUpperCase()) {
		case "ARTICLE":
			dictionaryFile.articles.add(currArticle);
			break;
		case "TITLE":
			currArticle.title = data;
			break;
		case "AUTHOR":
			currArticle.author = data;
			break;
		case "MONTH":
			db.append(data);
			break;
		case "DAY":
			db.append(" " + data);
			break;
		case "YEAR":
			db.append(" " + data);
			break;
		case "DATE":
			currArticle.date = db.toString();
			break;
		case "BODY":
			currArticle.body = data;
			break;
		case "LINK":
			currArticle.link = data;
			break;
		}
		sb.setLength(0);
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
}