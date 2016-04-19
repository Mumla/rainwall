package org.zakirova.rainwall.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class DataLoader generates List with hights of wall.
 * 
 * @author Irina Zakirova
 *
 */
public class DataLoader {
	private static Scanner scanner;

	public static List<Integer> read(InputStream in){
		List<Integer> data = new ArrayList<Integer>();
		if (in!=null){
			scanner = new Scanner(in);
			while(scanner.hasNextInt()){
				int h=scanner.nextInt();
				if (h<0) {
					JOptionPane.showMessageDialog(null,
						    "Negative value of heiht "+h,
						    "File Error",
						    JOptionPane.ERROR_MESSAGE);
					break;
				}
				data.add(new Integer(h));
			}
			try {
				if (scanner.hasNext()){
					JOptionPane.showMessageDialog(null,
						    "File has incorrect format. Next word is: "+scanner.next()+
						    "\nPrevious correct data will be proceed",
						    "File Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return data;
	}
}
