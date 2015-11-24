package languageID.classification;

import java.io.File;
import java.util.Scanner;

import com.aliasi.classify.BaseClassifier;
import com.aliasi.classify.Classification;
import com.aliasi.util.AbstractExternalizable;

public class Driver {

	public static void main(String[] args) throws Exception {
		File dataDir = new File("data/Language_Data");
		File modelFile = new File("data/Language_Models/palito.model");

		String[] categories = dataDir.list();

		BaseClassifier<CharSequence> classifier = (BaseClassifier<CharSequence>) AbstractExternalizable
				.readObject(modelFile);

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {

			String input = scanner.nextLine();
			Classification classification = classifier.classify(input);
			System.out.println("Language is: " + classification.bestCategory());
		}
	}
}
