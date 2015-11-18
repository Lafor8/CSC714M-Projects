package informationRetrieval.io;

import informationRetrieval.models.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ArticleTxtFileReader {

	public static Document parseToDocument(File file) throws FileNotFoundException {

		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));

		StringBuilder sb = new StringBuilder();
		while (scanner.hasNext()){
			sb.append(scanner.nextLine());
			sb.append("\n");
		}
		
		scanner.close();

		return new Document(file.getName(), sb.toString());
	}

}
