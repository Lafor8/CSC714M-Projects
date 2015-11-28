package informationExtraction.inputParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TxtFileCleaner {

	public static void main(String[] args) throws IOException {

		File file = new File("isp.txt");

		Scanner scanner = new Scanner(file);

		StringBuilder sb = new StringBuilder();

		while (scanner.hasNext()) {

			String currLine = scanner.nextLine();

			if (currLine.contains("Province of British Columbia")) {
				try {
					scanner.nextLine();
					scanner.nextLine();
					scanner.nextLine();
				} catch (Exception e) {
				}
			} else {
				sb.append(currLine).append("\n");
			}
		}
		scanner.close();

		FileWriter fw = new FileWriter("cleaned-isp.txt");
		fw.write(sb.toString());
		fw.close();
	}

}
