package io;

import java.io.File;

import models.ArticleFile;

public class XMLFileIO {

	public void processFile(String path) {
		File file = new File(path);
		ArticleFile articles;

		XMLFileReader xfr = new XMLFileReader();
		articles = xfr.readXMLFile(file);

		// System.out.println(articles.toString(0));
		System.out.print(articles.toString(0));
	}

	public void crawlFolder(String path) {
		File folder = new File(path);

		File[] list = folder.listFiles();
		for (File file : list) {
			if (file.isFile()) {
				// System.out.println("File " + file.getName());
				processFile(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				System.out.println("Folder: " + file.getName());
				crawlFolder(file.getAbsolutePath());
			}
		}
	}
}
