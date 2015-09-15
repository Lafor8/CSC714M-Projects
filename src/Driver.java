import io.CSVWriter;
import io.XMLFileIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.ArticleFile;
import models.NamedEntity;
import ner.ArticleNamedEntityExtractor;

public class Driver {

	public static void main(String[] args) throws IOException {
		XMLFileIO io = new XMLFileIO();

		ArrayList<ArticleFile> articleFiles;

		// Read files
		// articleFiles = io.process("data");
		// articleFiles = io.process("data/News/2004/May.xml");
		articleFiles = io.process("data/Test.xml");
		// articleFiles = io.process("data");

		// Categorize Recognized Entities
		ArticleNamedEntityExtractor articleNamedEntityExtractor = new ArticleNamedEntityExtractor();
		List<NamedEntity> namedEntityList = articleNamedEntityExtractor.process(articleFiles);

		// Write the output to a file

		CSVWriter.write("output.csv", namedEntityList); // TODO: Improve
		// Write Output if
		// needed

	}
}

/*
 * Notes:
 * 
 * > XMLFileIO's process function can take either directory or filepath > Only
 * .xml files will be processed. > Given other data, the program may fail if it
 * encounters an entity that isn't coded in (See XMLFileReader's expandHeader) >
 * Some files don't have a closing </data> tag > Some files don't escape
 * characters that are supposed to be escaped in XML (& in Opinyon/2001/June.xml
 * > http://www.w3.org/TR/html4/sgml/entities.html
 */