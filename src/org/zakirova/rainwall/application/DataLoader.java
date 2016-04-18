package org.zakirova.rainwall.application;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
	private static Scanner scanner;

	public static List<Integer> read(InputStream in){
		List<Integer> data = new ArrayList<Integer>();
		if (in!=null){
			scanner = new Scanner(in);
			while(scanner.hasNextInt()){
				data.add(new Integer(scanner.nextInt()));
			}
		}
		return data;
	}
}
