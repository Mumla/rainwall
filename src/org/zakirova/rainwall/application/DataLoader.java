package org.zakirova.rainwall.application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
	public static List<Integer> read(InputStream in){
		List<Integer> data = new ArrayList<Integer>();
		if (in!=null){
			Scanner scanner = new Scanner(in);
			while(scanner.hasNextInt()){
				data.add(new Integer(scanner.nextInt()));
			}
		}
		return data;
	}
	
	public static void main(String[] args) {
		FileLoader fl=new FileLoader();
		InputStream in = fl.getDataStream("C:\\Politech\\geoscan\\data.txt");
		DataLoader dl = new DataLoader();
		System.out.println(dl.read(in));
	}
}
