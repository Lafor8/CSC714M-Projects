package namedEntityRecognizer.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.models.Article;
import common.models.ArticleFile;

import namedEntityRecognizer.models.NamedEntity;
import namedEntityRecognizer.models.NamedEntity.Category;

public class ArticleNamedEntityExtractor {

	public static final int N_GRAM_LIMIT = 8;

	public List<NamedEntity> process(List<ArticleFile> articleFiles) {
		HashSet<NamedEntity> namedEntities = new LinkedHashSet<NamedEntity>();

		for (ArticleFile articleFile : articleFiles) {
			for (Article article : articleFile.articles) {
				// Process Title
				// namedEntities.addAll(processString(article.title));

				// Process Author
				// HashSet<NamedEntity> authorNamedEntities =
				// processString(article.author);
				// // Make sure all the categories here are persons, since this
				// is
				// // the author tag
				// Iterator<NamedEntity> authorIterator =
				// authorNamedEntities.iterator();
				// while (authorIterator.hasNext()) {
				// NamedEntity curr = authorIterator.next();
				// curr.category = Category.PERSON;
				// }

				// namedEntities.addAll(processString(article.author));

				// Process Date
				// namedEntities.addAll(processString(article.date));

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

				// if (category.equals(Category.PERSON) ||
				// category.equals(Category.LOCATION)) {
				// ArrayList<NamedEntity> neList = splitAt(ne.getCleanString(),
				// category);
				// for (NamedEntity entity : neList)
				// namedEntities.add(new NamedEntity("\"" +
				// entity.getCleanString() + "\"", category));
				// } else {

				// Add the newly recognized and categorized Named
				// Entity to the set
				namedEntities.add(new NamedEntity("\"" + ne.getCleanString() + "\"", category));
				// }
			}

		}
		return namedEntities;
	}

	private ArrayList<NamedEntity> splitAt(String s, Category category) {
		ArrayList<NamedEntity> neList = new ArrayList<NamedEntity>();
		String[] tokens = s.split("\\sat\\s");
		for (String token : tokens)
			neList.add(new NamedEntity(token.trim(), category));

		return neList;
	}
}
