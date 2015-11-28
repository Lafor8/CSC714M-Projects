package informationExtraction.constituentParse;

import informationExtraction.ExtractionResult;
import informationExtraction.inputParser.Sentence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class InformationExtractor {

	public static String[] SUBJECT_WORDS = { "office", "personnel", "management", "staff", "officer", "organization",
			"administration", "users", "doer", "owner" };

	private static String sentencesArrayToString(List<Sentence> sentences) {
		String s = "";
		for (Sentence sentence : sentences)
			s += sentence.getText() + " ";
		return s;
	}

	public static List<ExtractionResult> parse(List<Sentence> sentences) throws IOException {

		List<ExtractionResult> results = new ArrayList<ExtractionResult>();

		String text = sentencesArrayToString(sentences);

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Annotate an example document.
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);
		List<CoreMap> coreMaps = doc.get(CoreAnnotations.SentencesAnnotation.class);
		System.out.println("Core Maps Size: " + coreMaps.size() + " Sentences Size: " + sentences.size());
		for (int i = 0; i < coreMaps.size(); i++) {
			CoreMap sentence = coreMaps.get(i);
			Tree rootTree = sentence.get(TreeAnnotation.class);
			Tree[] clauses = ClauseParser.getIndependentClauses(sentence);
			System.out.println("Sentence: " + printTree(rootTree));
			System.out.println(rootTree.pennString());
			for (Tree clause : clauses) {
				System.out.println("Clause: " + printTree(clause));
				Tree subject = SubjectParser.getSubject(clause);
				// System.out.println("Subject: " + printTree(subject));
				// may also need to get the prepositional phrases in the subject
				// phrases.
				Tree sBar = SBARParser.getSBAR(clause);
				Tree[] verbPhrases = VerbParser.getVerbPhrases(clause);
				if (verbPhrases != null)
					for (Tree verbPhrase : verbPhrases) {
						String eSubject = "", eGoal = "", eScope = "", eConstraint = "", eJurisdiction = "";
						// System.out.println(verbPhrase.pennString());
						// System.out.println("Verb Phrase: " +
						// printTree(verbPhrase));
						String verbs = VerbParser.getVerbs(verbPhrase);
						// System.out.println("Verbs: " + verbs);

						boolean hasBeInVerbPhrase = VerbParser.hasBeInVerbPhrase(VerbParser.getWholeVerbPhrase(clause));
						System.out.println("Has Be: " + hasBeInVerbPhrase);
						Tree dobj = DOBJParser.getDirectObject(verbPhrase);
						// System.out.println("DOBJ: " + printTree(dobj));
						Tree prepOfDobj = DOBJParser.getPrepositionalPhraseOfDirectObject(verbPhrase);

						Tree ofPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "of");
						if (ofPhrase == null)
							ofPhrase = PrepositionParser.getPrepositionalPhrase(subject, "of");
						// System.out.println("Of Prep: " +
						// printTree(ofPhrase));
						Tree withPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "with");
						// System.out.println("With Prep: " +
						// printTree(withPhrase));
						Tree byPhrase = PrepositionParser.getNounPhrase(verbPhrase, "by", true);
						// System.out.println("By Prep: " +
						// printTree(byPhrase));
						Tree fromPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "from");
						// System.out.println("From Prep: " +
						// printTree(fromPhrase));
						Tree forPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "for");
						if (forPhrase == null)
							forPhrase = PrepositionParser.getPrepositionalPhrase(subject, "for");
						// System.out.println("For Prep: " +
						// printTree(forPhrase));
						Tree uponPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "upon");
						// System.out.println("Upon Prep: " +
						// printTree(uponPhrase));
						Tree onPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "on");
						Tree inPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "in");
						Tree toPhrase = PrepositionParser.getPrepositionalPhrase(verbPhrase, "to");
						// System.out.println("To Prep: " +
						// printTree(toPhrase));
						eGoal = verbs;

						if (byPhrase != null) {
							eSubject = printTree(byPhrase);
							eScope = printTree(subject);
						} else if (!hasBeInVerbPhrase) {
							eSubject = printTree(subject);
							eScope = printTree(dobj);
							// if (prepOfDobj != null)
							// eConstraint = printTree(prepOfDobj) + " | ";
						} else {
							eScope = printTree(subject);
						}

						if (withPhrase != null)
							eConstraint += printTree(withPhrase) + " | ";
						if (fromPhrase != null)
							eConstraint += printTree(fromPhrase) + " | ";
						if (uponPhrase != null)
							eConstraint += printTree(uponPhrase) + " | ";
						if (onPhrase != null)
							eConstraint += printTree(onPhrase) + " | ";
						if (inPhrase != null)
							eConstraint += printTree(inPhrase) + " | ";
						if (sBar != null) {
							List<Tree> toCheck = new ArrayList<Tree>();
							toCheck.add(withPhrase);
							toCheck.add(fromPhrase);
							toCheck.add(uponPhrase);
							toCheck.add(onPhrase);
							toCheck.add(inPhrase);
							boolean isChild = false;
							for (Tree tree : toCheck) {
								if (ParentChecker.isChildOfParent(tree, sBar))
									isChild = true;
							}
							if (isChild == false)
								eConstraint += printTree(sBar) + " | ";
						}
						if (ofPhrase != null)
							eJurisdiction += printTree(ofPhrase) + " | ";
						if (forPhrase != null)
							eJurisdiction += printTree(forPhrase) + " | ";
						if (toPhrase != null)
							eJurisdiction += printTree(toPhrase) + " | ";

						// if (eScope == null && withPhrase != null) {
						// eScope = printTree(withPhrase);
						// if (eConstraint.equals(eScope))
						// eConstraint = null;
						// }

						eConstraint = eConstraint.trim();
						eJurisdiction = eJurisdiction.trim();

						if (eConstraint.length() > 0 && eConstraint.charAt(eConstraint.length() - 1) == '|')
							eConstraint = eConstraint.substring(0, eConstraint.length() - 1).trim();

						if (eJurisdiction.length() > 0 && eJurisdiction.charAt(eJurisdiction.length() - 1) == '|')
							eJurisdiction = eJurisdiction.substring(0, eJurisdiction.length() - 1).trim();

						ExtractionResult result = new ExtractionResult();
						result.setSubject(eSubject);
						result.setScope(eScope);
						result.setGoal(eGoal);
						result.setConstraint(eConstraint);
						result.setJurisdiction(eJurisdiction);
						result.setOriginalSentence(printTree(rootTree));
						result.setClause(printTree(clause));
						result.setSection(sentences.get(i).getSection());
						results.add(result);

						// System.out.println("eSubject: " + eSubject);
						// System.out.println("eScope: " + eScope);
						// System.out.println("eGoal: " + eGoal);
						// System.out.println("eConstraint: " + eConstraint);
						// System.out.println("eJurisdiction: " +
						// eJurisdiction);

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

		return results;
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
