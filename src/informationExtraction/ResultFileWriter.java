package informationExtraction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultFileWriter {

	public static void writeToFile(String filePath, List<ExtractionResult> results) throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("Section,Goal,Scope,Subject,Constraint,Jurisdiction,Clause,Original Sentence\n");

		for (ExtractionResult result : results)
			sb.append(result.getAsCSVFormat());

		FileWriter fw = new FileWriter(filePath);
		fw.write(sb.toString());
		fw.close();
	}

}
