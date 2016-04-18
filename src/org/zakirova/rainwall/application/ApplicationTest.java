package org.zakirova.rainwall.application;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;

import org.zakirova.rainwall.visualization.Factory;
import org.zakirova.rainwall.visualization.SceneRenderer;
import org.zakirova.rainywall.algorithm.Calculation;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;


public class ApplicationTest extends JPanel implements ActionListener{
	
	final JFileChooser fc = new JFileChooser();
	SceneRenderer renderer;
	GLCanvas glcanvas;
	JFrame frame;
	JTextField jtfPath;
	
	ApplicationTest(){
		frame = new JFrame ( "RainWall" );
	    frame.setSize( 800, 800);//frame.getContentPane().getPreferredSize() );
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    JButton jbtnBrowse = new JButton("Browse...");
	    jtfPath = new JTextField(50);
	    JButton jbtnLoad = new JButton("Load");
	    jbtnBrowse.addActionListener(this);
	    jbtnLoad.addActionListener(this);
	    
	    final GLProfile profile = GLProfile.get( GLProfile.GL2 );
	    GLCapabilities capabilities = new GLCapabilities( profile );
	    
	    glcanvas = new GLCanvas( capabilities );
	    glcanvas.setSize( 700, 700 );    
	    renderer = new SceneRenderer(null);
	    glcanvas.addMouseMotionListener(new RotationProceed(renderer, glcanvas));
	    
	    
	    frame.add(jbtnBrowse);
	    frame.add(jtfPath);
	    frame.add(jbtnLoad);
	    frame.add( glcanvas );
	    
	    frame.setVisible( true );
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new ApplicationTest();	
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Browse...")){
			int returnValue = fc.showOpenDialog(null);
			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
		        File file = fc.getSelectedFile();
		        jtfPath.setText(file.getAbsolutePath());
			}
		}
		if (e.getActionCommand().equals("Load")){
			FileLoader fl = new FileLoader();						
			InputStream in = fl.getDataStream(jtfPath.getText());		//load file
			if (in == null)
				return;
			DataLoader dl = new DataLoader();
			List<Integer> wall = dl.read(in);							//load inf about wall
			Factory factory = createObjects(wall);						//solve problem and create objects
			showResult(factory);
		}
	}
	private Factory createObjects(List<Integer> wall){
		Calculation calc = new Calculation();
		List<Integer> water = calc.run(wall);
		Factory factory = new Factory (2, 0, 0, 0);
		factory.generate(wall, water);
			return factory;
	}
	private void showResult(Factory factory){

		if (factory != null){
			renderer.setFactory(factory);
			glcanvas.addGLEventListener( renderer );
			glcanvas.repaint();		
		}
	}
}
