package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DocumentParser {
	public static List<String> getIds(File file) {
		ArrayList<String> ids = new ArrayList<String>();
		Scanner reader;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			return ids;
		}

		while (reader.hasNextLine()) {
			ids.add(reader.nextLine());
		}
		
		reader.close();

		return ids;
	}
}
