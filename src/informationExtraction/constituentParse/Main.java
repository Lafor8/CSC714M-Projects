package informationExtraction.constituentParse;

import java.io.FileNotFoundException;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class Main {

	public static String[] SUBJECT_WORDS = { "office", "personnel", "management", "staff", "officer", "organization",
			"administration", "users" };

	private static String[] sentences;

	private static String sentencesArrayToString() {
		String s = "";
		for (String sentence : sentences)
			s += sentence + " ";
		return s;
	}

	public static void main(String[] args) throws FileNotFoundException {
		sentences = FileManager.read("input.txt");

		String text = sentences[0];
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,depparse,natlog");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Annotate an example document.
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);

		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree rootTree = sentence.get(TreeAnnotation.class);
			System.out.println(rootTree.pennString());
			Tree[] clauses = ClauseParser.getIndependentClauses(sentence);

			for (Tree clause : clauses) {
				System.out.println(clause);

				Tree subject = SubjectParser.getSubject(clause);
				System.out.println("Subject: " + printTree(subject));

				Tree[] verbPhrases = VerbParser.getVerbPhrases(clause);
				if (verbPhrases != null)
					for (Tree verbPhrase : verbPhrases)
						System.out.println("Verb Phrase: " + printTree(verbPhrase));

				Tree dobj = DOBJParser.getDirectObjects(verbPhrases);
				System.out.println("DOBJ: " + printTree(dobj));
				// get subject.
				// get verb and DOBJ pairs
				// get By NP
				// get To NP
				// get From NP

				// Using example at index 27, NP Of personnel.
				// personnel is considered as the jurisdiction
			}
			System.out.println();
		}
	}

	private static String printTree(Tree t) {
		if (t == null)
			return null;

		String text = "";
		for (int i = 0; i < t.yield().size(); i++) {
			Label l = t.yield().get(i);
			text += l.value();
			if (i != t.yield().size() - 1)
				text += " ";

		}
		return text;
	}

}
