package tagalogStemmer.io;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import common.models.Article;
import common.models.ArticleFile;

public class ArticleConverter {

	public final int MIN_WORD_SIZE = 1;
	
	// TODO: Confirm if the only characters that can be found in a filipino word are unicode letters and "-"
	
	/*
	 * \p{L} - any letter in any language 
	 * \p{Pd} - any form of dash or hyphen 
	 * \p{Cf} - invisible formatting indicator (because there was a "-" left otherwise)
	 * Alternate pattern (lacks some characters) - "([()\\.,\"\\?]{0,}[\\s]{1,}[()\\.,\"\\?]{0,})|([()\\.,\"\\?]{1,})" 
	 */
	public final String WORD_SPLIT_REGEX = "[^\\p{L}\\p{Pd}\\p{Cf}]{1,}";

	public ArrayList<String> convertArticleFilesToWordList(ArrayList<ArticleFile> articleFiles) {
		ArrayList<String> wordList = new ArrayList<>();

		for (ArticleFile articleFile : articleFiles) {
			ArrayList<String> wordsFromArticleFile = new ArrayList<>();

			wordsFromArticleFile = this.convertArticleFileToWordList(articleFile);

			wordList.addAll(wordsFromArticleFile);
		}

		return wordList;
	}

	public ArrayList<String> convertArticleFileToWordList(ArticleFile articleFile) {
		ArrayList<String> wordList = new ArrayList<>();

		for (Article article : articleFile.articles) {
			ArrayList<String> wordsFromArticle = new ArrayList<>();

			wordsFromArticle = this.convertArticleToWordList(article);

			wordList.addAll(wordsFromArticle);
		}

		return wordList;
	}

	public ArrayList<String> convertArticleToWordList(Article article) {
		ArrayList<String> wordList = new ArrayList<>();
		LinkedHashSet<String> wordSet = new LinkedHashSet<>();
		
		// TODO: Confirm if other sections of the article will be processed
		// for now, only the body is processed
		String list[];

		list = article.body.split(this.WORD_SPLIT_REGEX);
		
		System.out.println();
		System.out.println("Size before duplicate removal: " + list.length);
		for (String word : list) {
			if (word.length() >= this.MIN_WORD_SIZE)
				wordSet.add(word.toLowerCase());
		}
		
		System.out.println("Size after duplicate removal: " + wordSet.size());
		System.out.println();
		
		wordList.addAll(wordSet);
		return wordList;
	}
}
