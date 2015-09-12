import java.io.IOException;
import java.util.ArrayList;

import models.ArticleFile;

import io.XMLFileIO;

public class Driver {

	public static void main(String[] args) throws IOException {
		XMLFileIO io = new XMLFileIO();
		
		ArrayList<ArticleFile> articleFiles;
		
		// Read files
		//articleFiles = io.process("data");
		//articleFiles = io.process("data/News/2001/April.xml");
		//articleFiles = io.process("data/Opinyon/2001/June.xml");
		articleFiles = io.process("data/News/2001/April.xml");
		
		// TODO: Extract Named Entities
		
		// TODO: Categorize Recognized Entities
		
		// TODO: Write Output
		
	}
}

/*
Notes:

> XMLFileIO's process function can take either directory or filepath
> Only .xml files will be processed.
> Given other data, the program may fail if it encounters an entity that isn't coded in
	(See XMLFileReader's expandHeader)
> Some files don't have a closing </data> tag
> Some files don't escape characters that are supposed to be escaped in XML (& in Opinyon/2001/June.xml
> http://www.w3.org/TR/html4/sgml/entities.html

*/