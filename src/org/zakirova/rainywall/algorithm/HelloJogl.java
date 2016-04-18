package org.zakirova.rainywall.algorithm;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

//import com.jogamp.opengl.GLCapabilities;

public class HelloJogl implements GLEventListener {
	  private float rtri;  //for angle of rotation
	   @Override
	   public void display(GLAutoDrawable drawable) {
		   //gl.glScalef( 0.50f,0.25f,0.50f ); 
		   //gl.glColor3f( 1f,0f,0f ); //applying red  
		   final GL2 gl = drawable.getGL().getGL2(); 
		      gl.glClear (GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT );  
		      
		      // Clear The Screen And The Depth Buffer 
		      gl.glLoadIdentity();  // Reset The View     
		              
		      //triangle rotation      
		      gl.glRotatef( rtri, 0.0f, 1.0f, 0.0f );  
		              
		      // Drawing Using Triangles 
		      /*gl.glBegin( GL2.GL_TRIANGLES );          
		      
		      gl.glColor3f( 1.0f, 0.0f, 0.0f );   // Red 
		      gl.glVertex3f( 0.5f,0.7f,0.0f );    // Top 
		      gl.glColor3f( 0.0f,1.0f,0.0f );     // blue 
		      gl.glVertex3f( -0.2f,-0.50f,0.0f ); // Bottom Left 
		      gl.glColor3f( 0.0f,0.0f,1.0f );     // green 
		      gl.glVertex3f( 0.5f,-0.5f,0.0f );   // Bottom Right 
		      
		      gl.glEnd();    
		      gl.glFlush(); 
		      */
		      //giving different colors to different sides
		      gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		      gl.glColor3f(1f,0f,0f); //red color
		      gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		      gl.glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		      gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
		      gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
				
		      gl.glColor3f( 0f,1f,0f ); //green color
		      gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad
		      gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad
		      gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
		      gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad 

		      gl.glColor3f( 0f,0f,1f ); //blue color
		      gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
		      gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
		      gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
		      gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 

		      gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
		      gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
		      gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		      gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
		      gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)

		      gl.glColor3f( 1f,0f,1f ); //purple (red + green)
		      gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
		      gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
		      gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
		      gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 

		      gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
		      gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
		      gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad
		      gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
		      gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		      gl.glEnd(); // Done Drawing The Quad
		      rtri += 0.2f;  //assigning the angle  
		      
		      gl.glEnable( GL2.GL_LIGHTING );  
		      gl.glEnable( GL2.GL_LIGHT0 );  
		      gl.glEnable( GL2.GL_NORMALIZE );  

		      // weak RED ambient 
		      float[] ambientLight = { 0.1f, 0.f, 0.f,0f };  
		      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);  

		      // multicolor diffuse 
		      float[] diffuseLight = { 1f,2f,1f,0f };  
		      gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0 ); 
	   }
		
	   @Override
	   public void dispose(GLAutoDrawable arg0) {
	      //method body
	   }
		
	   @Override
	   public void init(GLAutoDrawable arg0) {
	      // method body
	   } 
		
	   @Override
	   public void reshape(GLAutoDrawable drawable, int x,  int y, int width, int height) {
		// TODO Auto-generated method stub 
		/*   final GL2 gl = drawable.getGL().getGL2();  
		            
		   // get the OpenGL 2 graphics object   
		   if(height <= 0) height = 1; 
		       
		   //preventing devided by 0 exception height = 1; 
		   final float h = (float) width / (float) height; 
		            
		   // display area to cover the entire window 
		   gl.glViewport(0, 0, width, height); 
		            
		   //transforming projection matrix 
		   gl.glMatrixMode(GL2.GL_PROJECTION); 
		   gl.glLoadIdentity(); 
		   //gl.gluPerspective(45.0f, h, 1.0, 20.0); 
		      
		   //transforming model view gl.glLoadIdentity(); 
		   gl.glMatrixMode(GL2.GL_MODELVIEW); 
		   gl.glLoadIdentity(); */
	   }
		
	   public static void main(String[] args) {
	   
	      //getting the capabilities object of GL2 profile
	      final GLProfile profile = GLProfile.get(GLProfile.GL2);
	      GLCapabilities capabilities = new GLCapabilities(profile);
	               
	   // The GLJpanel class
	      GLJPanel gljpanel = new GLJPanel( capabilities ); 
	      HelloJogl b = new HelloJogl();
	      gljpanel.addGLEventListener(b);
	      gljpanel.setSize(400, 400);
			
	      //creating frame
	      final JFrame frame = new JFrame (" Basic Frame");
	      
			
	      //adding canvas to it
	      frame.getContentPane().add(gljpanel);
	      frame.setSize(400, 400);
	      //frame.setSize(frame.getContentPane().getPreferredSize());
	      frame.setVisible(true);
	      
	    //Instantiating and Initiating Animator 
	         final FPSAnimator animator = new FPSAnimator(gljpanel, 600,true); 
	         animator.start(); 

	      
	      
	   }//end of main
		
	}//end of classimport  