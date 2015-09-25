package common.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import common.models.ArticleFile;


public class ArticleXMLFileIO {

	ArticleXMLFileReader xfr;
	ArrayList<ArticleFile> articleFiles;

	public ArticleXMLFileIO() {
		xfr = new ArticleXMLFileReader();
	}

	public ArrayList<ArticleFile> process(String path) throws IOException {
		articleFiles = new ArrayList<ArticleFile>();

		File file = new File(path);
		System.out.println(file.getAbsolutePath());
		if (file.isFile()) {
			if (file.getName().toUpperCase().indexOf(".XML") != -1)
				processFile(file.getAbsolutePath());
		} else if (file.isDirectory()) {
			crawlFolder(file.getAbsolutePath());
		} else
			throw new IOException();
		
		return articleFiles;
	}

	public void processFile(String path) {
		File file = new File(path);

		ArticleFile articles;

		articles = xfr.readFile(file);

		articleFiles.add(articles);
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
