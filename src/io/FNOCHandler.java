package io;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/// A handler for the Filipino News and Opinyon Corpus
public class FNOCHandler extends DefaultHandler {

	StringBuilder sb = new StringBuilder();
	ArticleFile articleFile;
	boolean btitle = false;
	boolean bauthor = false;
	boolean bmonth = false;
	boolean bday = false;
	boolean byear = false;
	boolean bbody = false;
	boolean blink = false;

	public FNOCHandler(ArticleFile articleFile) {
		this.articleFile = articleFile;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		// System.out.println("Start Element :" + qName);
		switch (qName.toUpperCase()) {
		case "TITLE":
			btitle = true;
			break;
		case "AUTHOR":
			bauthor = true;
			break;
		case "MONTH":
			bmonth = true;
			break;
		case "DAY":
			bday = true;
			break;
		case "YEAR":
			byear = true;
			break;
		case "BODY":
			bbody = true;
			break;
		case "LINK":
			blink = true;
			break;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		// System.out.println("End Element :" + qName);
		System.out.println(qName + ": " + sb.toString().trim());
		sb.setLength(0);
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		if (btitle) {
			// System.out.println("\tTitle : " + new String(ch, start, length));
			btitle = false;
		} else if (bauthor) {
			// System.out.println("\tAuthor : " + new String(ch, start, length));
			bauthor = false;
		}
		else if (bmonth) {
			// System.out.println("\tMonth : " + new String(ch, start, length));
			bmonth = false;
		}
		else if (bday) {
			// System.out.println("\tDay : " + new String(ch, start, length));
			bday = false;
		}
		else if (byear) {
			// System.out.println("\tYear : " + new String(ch, start, length));
			byear = false;
		}
		else if (bbody) {
			// System.out.println("\tBody : " + new String(ch, start, length));
			bbody = false;
		}
		else if (blink) {
			// System.out.println("\tLink : " + new String(ch, start, length));
			blink = false;
		}
		
		sb.append(ch, start, length);
	}
}