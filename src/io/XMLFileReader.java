package io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLFileReader {

	public static void main(String[] args) {
		File file = new File("data/News/2001/April.xml");
		ArticleFile articles;

		XMLFileReader xfr = new XMLFileReader();
		articles = xfr.readXMLFile(file);

		System.out.println(articles.toString(10));
	}

	public ArticleFile readXMLFile(File file) {
		ArticleFile articleFile = new ArticleFile(file);

		try {
			// Preprocess file content
			String text = this.preprocessContent(file);

			// Setting up parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			FNOCHandler handler = new FNOCHandler(articleFile);

			// Call parser
			saxParser.parse(new ByteArrayInputStream(text.getBytes(Charset.defaultCharset())), handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleFile;
	}

	private String preprocessContent(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			// File reading
			String text = readFile(file.getAbsolutePath(), Charset.defaultCharset());

			// Expand header
			sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
			sb.append("<!DOCTYPE html [\n");
			sb.append("<!ENTITY acirc \"&#194;\">\n");
			sb.append("<!ENTITY Atilde \"&#195;\">\n");
			sb.append("<!ENTITY plusmn \"&#177;\">\n");
			sb.append("<!ENTITY ntilde \"&#241;\">\n");
			sb.append("]>\n");

			// Escape links
			text = text.replaceAll("<link>", "<link><![CDATA[");
			text = text.replaceAll("</link>", "]]></link>");

			// Append data
			sb.append(text.substring(text.indexOf("<data>")));

			// Affix closing data tag if it doesn't exist
			int ind = text.indexOf("</data>");
			System.out.println("INDEX: " + ind + "\n");
			if (ind == -1) {
				sb.append("</data>");
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
		// Build preprocessed file
		return sb.toString();
	}

	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
