package openIE;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.naturalli.OpenIE;
import edu.stanford.nlp.naturalli.SentenceFragment;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StanfordOpenIE {
	private boolean isTreePrinted;
	private StanfordCoreNLP pipeline;
	private Properties props;

	public StanfordOpenIE() {
		// Create the Stanford CoreNLP pipeline
		props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
		pipeline = new StanfordCoreNLP(props);

		isTreePrinted = true;
	}

	public static void main(String args[]) throws IOException {
		StanfordOpenIE openIE = new StanfordOpenIE();

		openIE.annotateSampleText("in.txt", "outTree.txt", true);
		//openIE.annotateSampleText("policy.txt", "policyOut.txt", false);
	}

	public void annotateSampleText(String input, String output, boolean isTreePrinted) throws IOException {

		this.setIsTreePrinted(isTreePrinted);

		File inputFile = new File("data\\openIE\\" + input);
		File outputFile = new File("output\\openIE\\" + output);

		Scanner in = new Scanner(inputFile);
		FileWriter fw = new FileWriter(outputFile);

		while (in.hasNextLine()) {
			String line = in.nextLine();

			if (Pattern.matches("//.{0,}", line) || line.trim().length() == 0) {
				continue;
			}
			System.out.println(line);

			fw.append(this.annotate(line));
		}

		fw.close();
		in.close();
	}

	public String annotate(String text) {

		StringBuilder sb = new StringBuilder();
		sb.append(text + "\n\n");

		// Annotate an example document.
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);

		// Loop over sentences in the document
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {

			// Get the OpenIE triples for the sentence
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);

			// Print the triples
			for (RelationTriple triple : triples) {
				sb.append(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t" + triple.relationLemmaGloss() + "\t" + triple.objectLemmaGloss() + "\n");
			}

			// Alternately, to only run e.g., the clause splitter:
			List<SentenceFragment> clauses = new OpenIE(props).clausesInSentence(sentence);
			for (SentenceFragment clause : clauses) {
				if (this.isTreePrinted) {
					sb.append("\n" + clause.parseTree + "\n");
					sb.append("\n" + clause.toString() + "\n");
				}
			}
		}
		sb.append("\n\n");
		return sb.toString();
	}

	public void setIsTreePrinted(boolean val) {
		this.isTreePrinted = val;
	}

}