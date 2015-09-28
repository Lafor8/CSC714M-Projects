package tagalogStemmer;

import java.io.IOException;
import java.util.ArrayList;

import tagalogStemmer.io.ArticleConverter;
import tagalogStemmer.io.DuplicateRemoverUtility;
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

		// CSVWriter.write("correct.csv", inputWords);

		DuplicateRemoverUtility.cleanCSV("data/cleaned.csv");

		TagalogStemmer tagalogStemmer = new TagalogStemmer();
		// TODO:
		ArrayList<Word> resultingWords = tagalogStemmer.stemWordsFromList(inputWords);

		// STEP 03 - OUTPUT RESULTS TO FILE / DISPLAY RESULTS TO CONSOLE
		// System.out.println();
		// System.out.println("##### PRINTING RESULTS #####");
		// System.out.println();
		//
		// for (Word result : resultingWords)
		// System.out.print(result.getPrintableWordHistory());
	}
}

/*
 * TODO:
 * 
 * LEGEND: N NOT NEEDED ANYMORE D Done (probably) P Partially done/ In-progress
 * 
 * Dictionary Loader: N HTML File Reader N Word Models
 * 
 * Tagalog Stemmer: Build Rule Tables N Routine 1 N Routine 2 D Routine 3 P
 * Routine 4 - need more rules D Routine 5 D Routine 6 P Routine 7 - need more
 * rules Routine 8
 * 
 * D Acceptability Conditions Assimilatory Conditions D Phoneme Change Rules
 * Swapping
 * 
 * 
 * NOTES: > TODO: Prefix and Suffix rules must be defined with these
 * instructions: > reprocess remaining > check acceptability > go to next
 * routine This is important, as certain rules would necessitate specific
 * behavior For example, with the Suffix Removal Rule - Remove /-han/.
 * Currently, all prefix and suffix rules are tested for acceptability. But for
 * the Remove /-han/ rule, the test might not be needed(or maybe it still is).
 * For the sample word tauhan, the /-han/ rule can be applied, but if the
 * acceptability test is run, the test will fail and thus the rule won't be
 * applied. For this case, the rule Remove /-an/ would pass the acceptability
 * test and thus the root derived would then be "tauh", which is obviously
 * wrong.
 * 
 * ** For more information on how Bonus defined the specifics of a rule, check
 * slide 12 of his slides or the right column of page 65 of bonus' paper.
 * 
 * > TODO: Swapping is basically what was detailed in the paper and slides on
 * candidates. basically, due to the complexity of the system as well as the
 * language, 2 runs are made per word, with the first run being with the
 * original setup, and if it doesn't detect a root word via dictionary lookup,
 * then another run will commence though this time, Routines 4 and 7 are
 * swapped, basically the Suffix removal will come before the affix removal
 * 
 * ** Though this might be a crazy idea, I suggest we branch before each routine
 * is applied, as although this will result in a lot more results (exponential
 * even) we can compensate for the fact that we don't have a dictionary lookup.
 * This will resolve cases wherein our system would accept the application (eg.
 * parating -> paratg ROUTINE 3) but the original TagSA would reject due to it
 * failing the dictionary lookup. That, or we can implement the dictionary
 * lookup and pretend we didn't read Matthew's post on not implementing the
 * dictionary component.
 * 
 * > TODO: Assimilatory Conditions, along with Swapping necessitates a multiple
 * solution candidates implementation. Basically multiple stemmings/manipulation
 * available to a single word.
 * 
 * ** I have a dirty idea on how to implement it, but I am sleepy so I'll get to
 * it if I get back to a PC before you do :))
 * 
 * > Although bonus specified the suffix phoneme change rules to apply only when
 * there is a consonant after 'u' we applied the phoneme change rule to roots
 * ending with 'u' as we had found cases for it. eg. takbuhin, saluhin, talunin
 * 
 * > BonusTest contains all words used as examples in the slides > StemmerTest
 * is just another test file > Affixes is a file that list all the affixes I
 * found on the net, I've ignored the wiki page for noun affixes in tagalog
 * though That might be a more orderly made list
 */