package languageID.training;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Strings;

public class Driver {

	public static void main(String[] args) throws Exception {
		File dataDir = new File("data/Language_Data");
		File modelFile = new File("data/Language_Models/palito.model");
		int nGram = 5;
		int numChars = 10000000;

		String[] categories = dataDir.list();

		DynamicLMClassifier classifier = DynamicLMClassifier.createNGramProcess(categories, nGram);

		char[] csBuf = new char[numChars];
		for (int i = 0; i < categories.length; ++i) {
			String category = categories[i];

			File categoryFolder = new File(dataDir, category);
			File[] files = categoryFolder.listFiles();

			for (File trainingFile : files) {
				FileInputStream fileIn = new FileInputStream(trainingFile);
				InputStreamReader reader = new InputStreamReader(fileIn, Strings.UTF8);
				reader.read(csBuf);
				String text = new String(csBuf, 0, numChars);
				Classification c = new Classification(category);
				Classified<CharSequence> classified = new Classified<CharSequence>(text, c);
				classifier.handle(classified);
				reader.close();
			}

		}
		AbstractExternalizable.compileTo(classifier, modelFile);
	}

}