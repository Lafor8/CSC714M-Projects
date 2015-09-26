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
		String fileName;

		// fileName = "NewsTest1";
		fileName = "StemmerTest";
		// fileName = "OpinionTest";

		articleFiles = io.process(filePath + fileName + ".xml");

		// STEP 02 - PROCESS DATA
		ArticleConverter converter = new ArticleConverter();
		ArrayList<String> inputWords = converter.convertArticleFilesToWordList(articleFiles);

		TagalogStemmer tagalogStemmer = new TagalogStemmer();
		// TODO:
		ArrayList<Word> resultingWords = tagalogStemmer.stemWordsFromList(inputWords);

		// STEP 03 - OUTPUT RESULTS TO FILE / DISPLAY RESULTS TO CONSOLE
		System.out.println();
		System.out.println("##### PRINTING RESULTS #####");
		System.out.println();

		for (Word result : resultingWords)
			System.out.print(result.getPrintableWordHistory());
	}
}

/*
 * TODO:
 * 
 * LEGEND:
 * N NOT NEEDED ANYMORE 
 * D Done (probably) 
 * P Partially done/ In-progress
 * 
 * Dictionary Loader: 
 * N HTML File Reader 
 * N Word Models
 * 
 * Tagalog Stemmer: 
 *   Build Rule Tables 
 * N Routine 1 
 * N Routine 2 
 * D Routine 3 
 * P Routine 4 - need more rules 
 * D Routine 5 
 * P Routine 6 - need to implement rule 3 and 4 
 * P Routine 7 - need more rules 
 *   Routine 8 
 * D Acceptability Conditions 
 *   Assimilatory Conditions 
 * D Phoneme Change Rules
 * 
 * 
 * NOTES:
 * > TODO: Prefix and Suffix rules must be defined with these instructions:
 * 			> reprocess remaining
 * 			> check acceptability
 * 			> go to next routine
 * 		This is important, as certain rules would necessitate specific behavior
 * 		For example, with the Suffix Removal Rule - Remove /-han/. 
 * 		Currently, all prefix and suffix rules are tested for acceptability.
 * 		But for the Remove /-han/ rule, the test might not be needed(or maybe it still is).
 * 		For the sample word tauhan, the /-han/ rule can be applied, but if the acceptability
 * 		test is run, the test will fail and thus the rule won't be applied. For this case,
 * 		the rule Remove /-an/ would pass the acceptability test and thus the root derived would
 * 		then be "tauh", which is obviously wrong.
 * 
 * 	For more information on how Bonus defined the specifics of a rule, check slide 12 of his slides
 * 		or the right column of page 65 of bonus' paper.
 * 
 */