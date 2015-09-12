import io.XMLFileReader;

import java.io.File;

import models.ArticleFile;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("data/News/2001/April.xml");
		ArticleFile articles;

		XMLFileReader xfr = new XMLFileReader();
		articles = xfr.readXMLFile(file);

		System.out.println(articles.toString(10));
	}

}
