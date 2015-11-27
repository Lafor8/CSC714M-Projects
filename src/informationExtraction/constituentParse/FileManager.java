package informationExtraction.constituentParse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

	public static String[] read(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}

		String[] arr = lines.toArray(new String[0]);
		return arr;
	}

}
