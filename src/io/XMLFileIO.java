package io;

import java.io.File;
import java.io.IOException;

import models.ArticleFile;

public class XMLFileIO {

	XMLFileReader xfr;

	public XMLFileIO() {
		xfr = new XMLFileReader();
	}

	public void process(String path) throws IOException {
		File file = new File(path);
		System.out.println(file.getAbsolutePath());
		if (file.isFile()) {
			if (file.getName().toUpperCase().indexOf(".XML") != -1)
				processFile(file.getAbsolutePath());
		} else if (file.isDirectory()) {
			crawlFolder(file.getAbsolutePath());
		} else
			throw new IOException();
	}

	public void processFile(String path) {
		File file = new File(path);
		
		ArticleFile articles;

		articles = xfr.readXMLFile(file);

		System.out.print(articles.toString(0));
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
