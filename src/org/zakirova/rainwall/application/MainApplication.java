package org.zakirova.rainwall.application;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;

import org.zakirova.rainwall.algorithm.Calculation;
import org.zakirova.rainwall.visualization.Factory;
import org.zakirova.rainwall.visualization.SceneRenderer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;


/**
 * Class contains main method. Provide interaction with user.
 * @author Irina Zakirova
 *
 */
public class MainApplication extends JPanel implements ActionListener{
		
	final private JFileChooser fc = new JFileChooser();
	private SceneRenderer renderer;
	private GLCanvas glcanvas;
	private JFrame frame;
	private JTextField jtfPath;
	
	MainApplication(){
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
				new MainApplication();	
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
		        load();
			}
		}
		if (e.getActionCommand().equals("Load")){
			load();
		}
	}
	private void load(){
		FileLoader fl = new FileLoader();						
		InputStream in = fl.getDataStream(jtfPath.getText());		//load file
		if (in == null)
			return;

		List<Integer> wall = DataLoader.read(in);					//load inf about wall
		Factory factory = createObjects(wall);						//solve problem and create objects
		showResult(factory);
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
