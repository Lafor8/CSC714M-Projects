package common.util;

import java.util.Scanner;

public class ConsoleInput {
	public static String promptUserForInput(String promptText){
		Scanner in = new Scanner(System.in);
		String inputText = "";
		
		System.out.println(promptText);
		inputText = in.nextLine();
		
		return inputText;
	}
}
