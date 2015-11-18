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

		String text = sentencesArrayToString();
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,depparse,natlog");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Annotate an example document.
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);

		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree rootTree = sentence.get(TreeAnnotation.class);
			// System.out.println(rootTree.pennString());
			Tree[] clauses = ClauseParser.getIndependentClauses(sentence);
			System.out.println("Sentence: " + printTree(rootTree));
			for (Tree clause : clauses) {
				System.out.println("Clause: " + printTree(clause));

				Tree subject = SubjectParser.getSubject(clause);
				System.out.println("Subject: " + printTree(subject));
				// may also need to get the prepositional phrases in the subject
				// phrases.
				Tree[] verbPhrases = VerbParser.getVerbPhrases(clause);
				if (verbPhrases != null)
					for (Tree verbPhrase : verbPhrases) {
						System.out.println("Verb Phrase: " + printTree(verbPhrase));
						Tree dobj = DOBJParser.getDirectObject(verbPhrase);
						System.out.println("DOBJ: " + printTree(dobj));
						Tree ofPhrase = PrepositionParser.getNounPhrase(verbPhrase, "of");
						System.out.println("Of Prep: " + printTree(ofPhrase));
						Tree withPhrase = PrepositionParser.getNounPhrase(verbPhrase, "with");
						System.out.println("With Prep: " + printTree(withPhrase));
						Tree byPhrase = PrepositionParser.getNounPhrase(verbPhrase, "by");
						System.out.println("By Prep: " + printTree(byPhrase));
						Tree toPhrase = PrepositionParser.getNounPhrase(verbPhrase, "to");
						System.out.println("To Prep: " + printTree(toPhrase));
						Tree fromPhrase = PrepositionParser.getNounPhrase(verbPhrase, "from");
						System.out.println("From Prep: " + printTree(fromPhrase));
						Tree forPhrase = PrepositionParser.getNounPhrase(verbPhrase, "for");
						System.out.println("For Prep: " + printTree(forPhrase));
						Tree uponPhrase = PrepositionParser.getNounPhrase(verbPhrase, "upon");
						System.out.println("Upon Prep: " + printTree(uponPhrase));

						System.out.println();
					}
				// get subject.
				// get verb and DOBJ pairs
				// get Of NP (can be used as scope or subject)
				// get With NP (can be used as scope)
				// get By NP (can be used as subject)
				// get To NP (can be used as jurisdiction)
				// get From NP (may be used as constraint)
				// get For NP (may be used as scope or subject)

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
