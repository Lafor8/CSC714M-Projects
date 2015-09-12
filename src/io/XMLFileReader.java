package io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLFileReader {
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	public static void main(String[] args) {
		File fxml = new File("data/News/2001/April.xml");

		// try {
		//
		// File file = new File("data/News/2001/April.xml");
		//
		// DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//
		// Document doc = dBuilder.parse(file);
		//
		// System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		//
		// if (doc.hasChildNodes()) {
		//
		// printNote(doc.getChildNodes());
		//
		// }
		//
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }

		
		try {
			String text = readFile(fxml.getAbsolutePath(),Charset.defaultCharset());
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
			sb.append("<!DOCTYPE html [\n");
			sb.append("<!ENTITY acirc \"&#194;\">\n");
			sb.append("<!ENTITY Atilde \"&#195;\">\n");
			sb.append("<!ENTITY plusmn \"&#177;\">\n");
			sb.append("<!ENTITY ntilde \"&#241;\">\n");
			sb.append("]>\n");

			text = text.replaceAll("<link>", "<link><![CDATA[");
			text = text.replaceAll("</link>", "]]></link>");
			
			sb.append(text.substring(text.indexOf("<data>")));
			
			int ind = text.indexOf("</data>");
			System.out.println("INDEX: "+ind);
			if(ind == -1){
				sb.append("</data>");
			}
			
			text = sb.toString();
			System.out.println(text.substring(0,1000));
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			FNOCHandler handler = new FNOCHandler();
//			saxParser.parse(new XMLFilterInputStream(new FileInputStream(fxml)), handler);
			saxParser.parse(new ByteArrayInputStream(text.getBytes(Charset.defaultCharset())), handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());

					}

				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					printNote(tempNode.getChildNodes());

				}

				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
			}
		}
	}
}
