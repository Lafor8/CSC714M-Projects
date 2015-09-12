package io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import models.ArticleFile;

public class XMLFileReader {

	String defaultHeader = null;

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
			sb.append(expandHeader());

			// Escape links
			text = text.replaceAll("<link>", "<link><![CDATA[");
			text = text.replaceAll("</link>", "]]></link>");

			// Escape unescaped characters
			text = text.replaceAll("&", "&amp;");

			// Append data
			sb.append(text.substring(text.indexOf("<data>")));

			// Affix closing data tag if it doesn't exist
			int ind = text.indexOf("</data>");
			// System.out.println("INDEX: " + ind + "\n");
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

	private String expandHeader() {
		if (this.defaultHeader == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
			sb.append("<!DOCTYPE html [\n");
			sb.append("<!ENTITY acirc  \"&#194;\">\n");
			sb.append("<!ENTITY Atilde \"&#195;\">\n");
			sb.append("<!ENTITY plusmn \"&#177;\">\n");
			sb.append("<!ENTITY ntilde \"&#241;\">\n");
			sb.append("<!ENTITY brvbar \"&#166;\">\n");
			sb.append("<!ENTITY copy   \"&#169;\">\n");
			sb.append("<!ENTITY nbsp   \"&#160;\">\n");
			sb.append("<!ENTITY ccedil \"&#231;\">\n");
			sb.append("<!ENTITY eacute \"&#233;\">\n");
			sb.append("<!ENTITY igrave \"&#236;\">\n");
			sb.append("<!ENTITY icirc  \"&#238;\">\n");
			sb.append("<!ENTITY shy    \"&#173;\">\n");
			sb.append("<!ENTITY not    \"&#172;\">\n");
			sb.append("<!ENTITY deg    \"&#176;\">\n");
			sb.append("<!ENTITY egrave \"&#232;\">\n");
			sb.append("<!ENTITY Ecirc  \"&#202;\">\n");
			sb.append("<!ENTITY pound  \"&#163;\">\n");
			sb.append("<!ENTITY ordm   \"&#186;\">\n");
			sb.append("<!ENTITY iacute \"&#237;\">\n");
			sb.append("<!ENTITY Ntilde \"&#209;\">\n");
			sb.append("<!ENTITY yuml   \"&#255;\">\n");
			sb.append("<!ENTITY Ccedil \"&#199;\">\n");
			sb.append("<!ENTITY atilde \"&#227;\">\n");
			sb.append("<!ENTITY yacute \"&#253;\">\n");
			sb.append("<!ENTITY Acirc  \"&#194;\">\n");
			sb.append("<!ENTITY aacute \"&#225;\">\n");
			sb.append("<!ENTITY middot \"&#183;\">\n");
			sb.append("<!ENTITY raquo  \"&#187;\">\n");
			sb.append("<!ENTITY Aacute \"&#193;\">\n");
			sb.append("<!ENTITY cent   \"&#162;\">\n");
			sb.append("<!ENTITY acute  \"&#180;\">\n");
			sb.append("<!ENTITY uacute \"&#250;\">\n");
			sb.append("<!ENTITY agrave \"&#224;\">\n");
			sb.append("<!ENTITY oacute \"&#243;\">\n");
			sb.append("<!ENTITY laquo  \"&#171;\">\n");
			sb.append("<!ENTITY ordf   \"&#170;\">\n");
			sb.append("<!ENTITY Eacute \"&#201;\">\n");

			sb.append("]>\n");

			defaultHeader = sb.toString();
		}

		return defaultHeader;
	}
}
