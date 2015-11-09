package informationRetrieval.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvertedIndex {

	public List<Term> terms;

	public InvertedIndex(List<Term> terms) {
		this.terms = terms;
	}

	public void save(String filePath) {
		try {

			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();

			FileWriter fw = new FileWriter(file);
			fw.append(this.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static InvertedIndex load(String filePath) {

		try {

			List<Term> terms = new ArrayList<Term>();

			Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)));

			while (scanner.hasNext()) {
				String currLine = scanner.nextLine();
				terms.add(Term.loadFromString(currLine));
			}

			scanner.close();

			return new InvertedIndex(terms);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return new InvertedIndex(new ArrayList<Term>());
	}

	public Term getTerm(String text) {
		for (Term term : this.terms) {
			if (term.text.equals(text))
				return term;
		}

		return null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Term term : terms) {
			sb.append(term.toString()).append("\n");
		}
		return sb.toString();
	}

	public InvertedIndex clone() {

		List<Term> cloneTerms = new ArrayList<Term>();
		for (Term term : terms)
			cloneTerms.add(term.clone());

		InvertedIndex clone = new InvertedIndex(cloneTerms);
		return clone;
	}

}
