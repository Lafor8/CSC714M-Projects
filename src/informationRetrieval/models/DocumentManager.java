package informationRetrieval.models;

import informationRetrieval.io.ArticleTxtFileReader;

import java.io.File;
import java.util.LinkedHashMap;

public class DocumentManager {

	private static DocumentManager instance;

	public static DocumentManager getInstance() {
		if (instance == null)
			instance = new DocumentManager();
		return instance;
	}

	private LinkedHashMap<Integer, Document> documents = new LinkedHashMap<Integer, Document>();
	private int documentCount = 0;

	public void populate(String folderPath) {
		// list all files
		// list all folders, recurse
		File folder = new File(folderPath);
		File[] list = folder.listFiles();

		for (File file : list) {
			if (file.isFile()) {
				if (file.getName().toLowerCase().contains(".txt")) {
					try {
						Document currDocument = ArticleTxtFileReader.parseToDocument(file);
						documentCount++;
						currDocument.documentNumber = documentCount;
						documents.put(currDocument.documentNumber, currDocument);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (file.isDirectory()) {
				populate(file.getAbsolutePath());
			}
		}

		System.out.println("Done reading " + documents.size() + " txt files.");
	}
}
