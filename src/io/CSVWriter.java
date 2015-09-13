package io;

import java.io.FileWriter;
import java.io.IOException;

import models.NamedEntity;

public class CSVWriter {

	public static final String outputFolder = "/output/";

	public static void write(String fileName, NamedEntity[] namedEntityList) {
		String toWrite = generateStringToWrite(namedEntityList);
		try {
			FileWriter fw = new FileWriter(outputFolder + fileName);
			fw.append(toWrite);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String generateStringToWrite(NamedEntity[] namedEntityList) {
		StringBuilder sb = new StringBuilder();
		for (NamedEntity namedEntity : namedEntityList)
			sb.append(namedEntity.toString()).append("\n");

		return sb.toString();
	}
}
