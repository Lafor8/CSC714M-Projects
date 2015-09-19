package ner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Article;
import models.ArticleFile;
import models.NamedEntity;
import models.NamedEntity.Category;

public class ArticleNamedEntityExtractor {

	public static final int N_GRAM_LIMIT = 8;

	public List<NamedEntity> process(List<ArticleFile> articleFiles) {
		HashSet<NamedEntity> namedEntities = new LinkedHashSet<NamedEntity>();

		for (ArticleFile articleFile : articleFiles) {
			for (Article article : articleFile.articles) {
				// Process Title
				namedEntities.addAll(processString(article.title));

				// Process Author
				HashSet<NamedEntity> authorNamedEntities = processString(article.author);
				// Make sure all the categories here are persons, since this is
				// the author tag
				Iterator<NamedEntity> authorIterator = authorNamedEntities.iterator();
				while (authorIterator.hasNext()) {
					NamedEntity curr = authorIterator.next();
					curr.category = Category.PERSON;
				}

				namedEntities.addAll(processString(article.author));

				// Process Date
				namedEntities.addAll(processString(article.date));

				// Process Body
				namedEntities.addAll(processString(article.body));
			}
		}

		List<NamedEntity> namedEntityList = convertSetToList(namedEntities);
		Collections.sort(namedEntityList);
		return namedEntityList;
	}

	private List<NamedEntity> convertSetToList(HashSet<NamedEntity> set) {
		List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
		for (NamedEntity namedEntity : set)
			namedEntityList.add(namedEntity);
		return namedEntityList;
	}

	private HashSet<NamedEntity> processString(String string) {
		HashSet<NamedEntity> namedEntities = new LinkedHashSet<NamedEntity>();

		if (string == null)
			return namedEntities;

		NamedEntityRecognizer ner = new NamedEntityRecognizer();
		NamedEntityCategorizer nec = new NamedEntityCategorizer();
		NGramIterator nGramIterator = new NGramIterator(string);

		String regex = ner.getMasterRegex();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);

		// System.out.println(regex);

		while (matcher.find()) {

			String currNGram = matcher.group();

			// Check if the n-gram is a Named Entity

			NamedEntity ne = ner.isNamedEntity(currNGram);

			if (ne != null) {

				// Categorize the Name Entity
				Category category = nec.categorize(ne);

				// Add the newly recognized and categorized Named
				// Entity to the set
				namedEntities.add(new NamedEntity("\"" + ne.getCleanString() + "\"", category));

				// Move the cursor after the current n-gram
				// This also invalidates the current n-gram tokens
				// so that they won't be considered in the next
				// iteration anymore
				nGramIterator.markLastNGramAsAlreadyProcessed();
			}

		}
		return namedEntities;
	}
}
