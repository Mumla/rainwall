package org.zakirova.rainwall.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JOptionPane;
/**
 * Class file loader provides generation of input stream from file.
 *  
 * @author Irina Zakirova
 *
 */
public class FileLoader {
	public InputStream getDataStream(String path){
		try{
			InputStream myInput = new FileInputStream(path);
			return myInput;
		}
		catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null,
				    "File does not exist",
				    "File Error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}
