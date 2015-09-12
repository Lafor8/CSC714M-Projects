package io;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/// A handler for the Filipino News and Opinyon Corpus
public class FNOCHandler extends DefaultHandler {

	StringBuilder sb = new StringBuilder();
	boolean btitle = false;
	boolean bauthor = false;
	boolean bmonth = false;
	boolean bday = false;
	boolean byear = false;
	boolean bbody = false;
	boolean blink = false;
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

//		System.out.println("Start Element :" + qName);

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

//		System.out.println("End Element :" + qName);
		System.out.println(qName + ": " +sb.toString().trim());
		sb.setLength(0);
	}

	public void characters(char ch[], int start, int length) throws SAXException {
//		System.out.println("TEXT: **" + new String(ch, start, length)+"**");
		if (btitle) {
//			System.out.println("\tTitle : " + new String(ch, start, length));
			btitle = false;
		}

		if (bauthor) {
//			System.out.println("\tAuthor : " + new String(ch, start, length));
			bauthor = false;
		}

		if (bmonth) {
//			System.out.println("\tMonth : " + new String(ch, start, length));
			bmonth = false;
		}

		if (bday) {
//			System.out.println("\tDay : " + new String(ch, start, length));
			bday = false;
		}

		if (byear) {
//			System.out.println("\tYear : " + new String(ch, start, length));
			byear = false;
		}

		if (bbody) {
//			System.out.println("\tBody : " + new String(ch, start, length));
			bbody = false;
		}

		if (blink) {
//			System.out.println("\tLink : " + new String(ch, start, length));
			blink = false;
		}
		sb.append(ch, start, length);
	}
//    String content = "";
//    SystemTo systemTo=new SystemTo();
//
//    @Override
//    public void startElement(String uri, String localName, String qName,
//        Attributes attributes) throws SAXException {
//
//        switch (qName) {
//            case "system":
//                System.out.println("inside company");
//                break;
//        }
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName)
//        throws SAXException {
//        switch (qName) {
//            case "u_id":
//                systemTo.setUid(content);
//                break;
//            case "serial_no":
//                systemTo.setSerialNumber(content);
//                break;
//            case "branch_name":
//                systemTo.setName(content);
//                break;
//        }
//    }

//    @Override
//    public void characters(char[] ch, int start, int length)
//        throws SAXException {
//        content = String.copyValueOf(ch, start, length).trim();
//    }
}