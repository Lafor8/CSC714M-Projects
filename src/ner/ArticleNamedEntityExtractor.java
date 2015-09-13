package ner;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import models.Article;
import models.ArticleFile;
import models.NamedEntity;
import models.NamedEntity.Category;

public class ArticleNamedEntityExtractor {

	public HashSet<NamedEntity> process(List<ArticleFile> articleFiles) {
		HashSet<NamedEntity> namedEntities = new LinkedHashSet<NamedEntity>();

		NamedEntityRecognizer ner = new NamedEntityRecognizer();
		NamedEntityCategorizer nec = new NamedEntityCategorizer();

		for (ArticleFile articleFile : articleFiles) {
			for (Article article : articleFile.articles) {
				// TODO Process Title

				// TODO Process Author

				// TODO Process Date

				// Process Body
				String bodyText = article.body;

				// Try 5-Grams until 1-Grams
				for (int n = 5; n > 0; n--) {

					NGramIterator nGramIterator = new NGramIterator(bodyText, n);

					while (nGramIterator.hasNext()) {

						String currNGram = nGramIterator.next();

						// Check if the n-gram is a Named Entity
						if (ner.isNamedEntity(currNGram)) {

							// Categorize the Name Entity
							Category category = nec.categorize(currNGram);

							// Add the newly recognized and categorized Named
							// Entity to the set
							namedEntities.add(new NamedEntity(currNGram, category));

							// Move the cursor after the current n-gram
							// This also invalidates the current n-gram tokens
							// so that they won't be considered in the next
							// iteration anymore
							nGramIterator.moveCursorAfterCurrNGram();
						}

					}
				}
			}
		}

		return namedEntities;
	}
}
