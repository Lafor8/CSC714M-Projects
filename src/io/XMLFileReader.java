package io;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLFileReader {

	public static void main(String[] args) {
		File fxml = new File("data/News/2001/April.xml");

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean btitle = false;
				boolean bauthor = false;
				boolean bmonth = false;
				boolean bday = false;
				boolean byear = false;
				boolean bbody = false;
				boolean blink = false;
				
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

					System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("TITLE")) {
						btitle = true;
					}

					if (qName.equalsIgnoreCase("AUTHOR")) {
						bauthor = true;
					}

					if (qName.equalsIgnoreCase("MONTH")) {
						bmonth = true;
					}

					if (qName.equalsIgnoreCase("DAY")) {
						bday = true;
					}
					
					if (qName.equalsIgnoreCase("YEAR")) {
						byear = true;
					}

					if (qName.equalsIgnoreCase("BODY")) {
						bbody = true;
					}

					if (qName.equalsIgnoreCase("LINK")) {
						blink = true;
					}

				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

					System.out.println("End Element :" + qName);

				}

				public void characters(char ch[], int start, int length) throws SAXException {

					if (btitle) {
						System.out.println("\tTitle : " + new String(ch, start, length));
						btitle = false;
					}

					if (bauthor) {
						System.out.println("\tAuthor : " + new String(ch, start, length));
						bauthor = false;
					}

					if (bmonth) {
						System.out.println("\tMonth : " + new String(ch, start, length));
						bmonth = false;
					}

					if (bday) {
						System.out.println("\tDay : " + new String(ch, start, length));
						bday = false;
					}

					if (byear) {
						System.out.println("\tYear : " + new String(ch, start, length));
						byear = false;
					}

					if (bbody) {
						System.out.println("\tBody : " + new String(ch, start, length));
						bbody = false;
					}

					if (blink) {
						System.out.println("\tLink : " + new String(ch, start, length));
						blink = false;
					}
				}

			};

			saxParser.parse(fxml.getAbsolutePath(), handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
