package org.zakirova.rainwall.application;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.zakirova.rainwall.visualization.Factory;
import org.zakirova.rainwall.visualization.SceneRenderer;
import org.zakirova.rainywall.algorithm.Calculation;
import org.zakirova.rainywall.algorithm.Cube;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Application {
	public static void main(String[] args) {
		Factory factory = new Factory(2, 0, 0, 0);
		Calculation calc = new Calculation();
		
		FileLoader fl=new FileLoader();
		InputStream in = fl.getDataStream("C:\\Politech\\geoscan\\data.txt");
		DataLoader dl = new DataLoader();
		
		List<Integer> wall = dl.read(in);
		
		List<Integer> water = calc.run(wall);
		
		factory.generate(wall, water);
		
		createShowGUI(factory);     
	}
	private static void createShowGUI(Factory factory){
		final GLProfile profile = GLProfile.get( GLProfile.GL2 );
	      GLCapabilities capabilities = new GLCapabilities( profile );
	      
	      // The canvas
	      final GLCanvas glcanvas = new GLCanvas( capabilities );
	      SceneRenderer renderer = new SceneRenderer(factory);
			
	      glcanvas.addGLEventListener( renderer );
	      glcanvas.setSize( 800, 800 );
		
	      final JFrame frame = new JFrame ( "RainWall" );
	      frame.getContentPane().add( glcanvas );
	      frame.setSize( frame.getContentPane().getPreferredSize() );
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible( true );
	      
	      final FPSAnimator animator = new FPSAnimator(glcanvas, 700,true);
			
	      animator.start();	 
	}
}
