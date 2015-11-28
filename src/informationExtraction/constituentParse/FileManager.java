package informationExtraction.constituentParse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public static String[] read(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String currLine;

		List<String> lines = new ArrayList<String>();
		while ((currLine = br.readLine()) != null)
			lines.add(currLine);

		return lines.toArray(new String[lines.size()]);

		// Scanner sc = new Scanner(new File(filename));
		// List<String> lines = new ArrayList<String>();
		// while (sc.hasNext()) {
		// lines.add(sc.nextLine());
		// }
		// sc.close();
		// String[] arr = lines.toArray(new String[0]);
		// return arr;
	}
}
