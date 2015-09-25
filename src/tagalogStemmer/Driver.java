package tagalogStemmer;

import java.io.IOException;
import java.util.ArrayList;

import tagalogStemmer.io.ArticleConverter;
import tagalogStemmer.models.Word;
import tagalogStemmer.system.TagalogStemmer;

import common.io.ArticleXMLFileIO;
import common.models.ArticleFile;

public class Driver {

	public static void main(String[] args) throws IOException {
		ArticleXMLFileIO io = new ArticleXMLFileIO();

		ArrayList<ArticleFile> articleFiles;

		// STEP 01 - READING INPUT FILES
		
		String filePath = "data/";
		String fileName = "NewsTest1";
		//String fileName = "OpinionTest";

		articleFiles = io.process(filePath + fileName + ".xml");

		// STEP 02 - PROCESS DATA
		ArticleConverter converter = new ArticleConverter();
		ArrayList<String> inputWords = converter.convertArticleFilesToWordList(articleFiles);
		
		TagalogStemmer tagalogStemmer = new TagalogStemmer();
		//TODO:
		ArrayList<Word> resultingWords = tagalogStemmer.stemWordsFromList(inputWords);

		// STEP 03 - OUTPUT RESULTS TO FILE / DISPLAY RESULTS TO CONSOLE
		System.out.println();
		System.out.println("##### PRINTING RESULTS #####");
		System.out.println();
		
		for(Word result : resultingWords)
			System.out.print(result.getPrintableWordHistory());
	}
}

/* TODO:

* NOT NEEDED ANYMORE
D Done (probably)
P Partially done/ In-progress

*Dictionary Loader:
*	HTML File Reader
*	Word Models

Tagalog Stemmer:
	Build Rule Tables
*	Routine 1
*	Routine 2
D	Routine 3
P	Routine 4
D	Routine 5
P	Routine 6
P	Routine 7
	Routine 8
D	Acceptability Conditions
	Assimilatory Conditions
	Phoneme Change Rules
*/