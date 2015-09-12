import java.io.IOException;

import io.XMLFileIO;

public class Driver {

	public static void main(String[] args) throws IOException {
		XMLFileIO io = new XMLFileIO();
		
		//io.process("data/News/2001/April.xml");
		//io.process("data/Opinyon/2001/June.xml");
		io.process("data");
	}
}

/*
Notes:

> Only .xml files will be processed.
> Given other data, the program may fail if it encounters an entity that isn't coded in
	(See XMLFileReader's expandHeader)
> Some files don't have a closing </data> tag
> Some files don't escape characters that are supposed to be escaped in XML (& in Opinyon/2001/June.xml

*/