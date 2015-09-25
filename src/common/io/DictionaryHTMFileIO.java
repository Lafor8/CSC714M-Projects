package common.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import common.models.DictionaryFile;


public class DictionaryHTMFileIO {

	DictionaryHTMFileReader hfr;
	ArrayList<DictionaryFile> dictionaryFiles;

	public DictionaryHTMFileIO() {
		hfr = new DictionaryHTMFileReader();
	}

	public ArrayList<DictionaryFile> process(String path) throws IOException {
		dictionaryFiles = new ArrayList<DictionaryFile>();

		File file = new File(path);
		System.out.println(file.getAbsolutePath());
		if (file.isFile()) {
			if (file.getName().toUpperCase().indexOf(".HTM") != -1)
				processFile(file.getAbsolutePath());
		} else if (file.isDirectory()) {
			crawlFolder(file.getAbsolutePath());
		} else
			throw new IOException();
		
		return dictionaryFiles;
	}

	public void processFile(String path) {
		File file = new File(path);

		DictionaryFile articles;

		articles = hfr.readFile(file);

		dictionaryFiles.add(articles);
		// System.out.print(articles.toString(0));
	}

	public void crawlFolder(String path) {
		File folder = new File(path);
		System.out.println("Folder: " + folder.getName());

		File[] list = folder.listFiles();
		for (File file : list) {
			if (file.isFile()) {
				if (file.getName().toUpperCase().indexOf(".XML") != -1)
					processFile(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				crawlFolder(file.getAbsolutePath());
			}
		}
	}
}
