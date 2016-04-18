package org.zakirova.rainwall.visualization;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class SceneRenderer implements GLEventListener {
	
	private GLU glu = new GLU();
	float rquad=0;
	private int textureWall, textureWater;
	Factory factory = null; 
	final static float coverageRad = 120.0f;
	private int xRot=0, yRot=0, zRot=0;
	private float angleRotX=0;
	private float angleRotY=0;
	
	
	public SceneRenderer(Factory factory) {
		this.factory=factory;
	}
	public void setFactory(Factory factory){
		this.factory = factory;
	}
	public void updateRot(int x,  int y, int z, float angleX,  float angleY){
		this.xRot+=x;
		this.yRot+=y;
		this.zRot+=z;
		this.angleRotX+=angleX;
		this.angleRotY+=angleY;
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		if (factory == null) return;
		final GL2 gl = drawable.getGL().getGL2();
	    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
	    gl.glLoadIdentity();
	    gl.glTranslated( 0f, 0f, -getCamerasZ() ); 

	      // Rotate The Cube On X, Y & Z
	    gl.glRotatef(angleRotX, xRot, 0, 0); 
	    gl.glRotatef(angleRotY, 0, yRot, 0); 
	    /*  gl.glEnable(GL2.GL_ALPHA);
	      gl.glEnable (GL2.GL_BLEND);
	      gl.glDisable(GL2.GL_CULL_FACE); 
	      gl.glBlendFunc(1, GL2.GL_ONE_MINUS_SRC_ALPHA);*/
	      
	      
	     gl.glBindTexture(GL2.GL_TEXTURE_2D, textureWall);
	     for(Cube cube: factory.getWall()){
	    	 SceneRenderer.drawCube(cube, gl);
	     }
	     gl.glBindTexture(GL2.GL_TEXTURE_2D, textureWater);
	     for(Cube cube: factory.getWater()){
	    	 SceneRenderer.drawCube(cube, gl);
	     }
    	 gl.glFlush();		
	}
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
	      gl.glShadeModel( GL2.GL_SMOOTH );
	      gl.glClearColor( 0f, 0f, 0f, 0f );
	      gl.glClearDepth( 1.0f );
	      gl.glEnable( GL2.GL_DEPTH_TEST );
	      gl.glDepthFunc( GL2.GL_LEQUAL );
	      gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
	      gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);

	      
	      gl.glEnable(GL2.GL_TEXTURE_2D);
	      gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_MAG_FILTER,GL2.GL_LINEAR);
	  	  gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_MIN_FILTER,GL2.GL_LINEAR);
	  	  gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
	  //	  
	      
	      try{			
	         File im = new File("C:\\Politech\\geoscan\\wallTexture.jpg");
	         Texture t = TextureIO.newTexture(im, true);
	         textureWall = t.getTextureObject(gl);
	         
	         im = new File("C:\\Politech\\geoscan\\waterTexture.jpg");
	         t = TextureIO.newTexture(im, true);
	         textureWater = t.getTextureObject(gl);
	      }catch(IOException e){
	         e.printStackTrace();
	      }
	  	 // CreateTexture(gl);
		
	}
	
/*	public void CreateTexture(GL2 gl)
    {
        URL imageURL= this.getClass().getResource("resources/waterTexture.png");
        
        TextureData data=null;
        try {
             data = TextureIO.newTextureData(gl.getGLProfile(),
                imageURL.openStream(),false, "png");
             Texture t = TextureIO.newTexture(data);
          //   textureWater = t.getTextureObject(gl);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }       
        
            gl.glEnable(GL2.GL_TEXTURE_2D);
 
            gl.glGenTextures(1,textures,0);
            gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]);
            
            gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0,GL2.GL_RGBA,data.getWidth(),data.getHeight(),0,GL2.GL_RGBA,
                    GL2.GL_UNSIGNED_BYTE,data.getBuffer());
    }
	
	*/
	
	@Override
	public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height) {
		 final GL2 gl = drawable.getGL().getGL2();
	      if( height == 0 )
	         height = 1;
				
	      final float h = ( float ) width / ( float ) height;
	      gl.glViewport( 0, 0, width, height );
	      gl.glMatrixMode( GL2.GL_PROJECTION );
	      gl.glLoadIdentity();
			
	      glu.gluPerspective( coverageRad, h, 1.0, 20.0 );
	      gl.glMatrixMode( GL2.GL_MODELVIEW );
	      gl.glLoadIdentity();
		
	}
	private double getCamerasZ(){
		double zForLength = factory.getLength()/2/Math.tan(Math.toRadians(coverageRad/2));
		double zForHeight = factory.getMaxHeight()/2/Math.tan(Math.toRadians(coverageRad/2));
		return Math.max(zForHeight+2,zForLength+2);
	}
	public boolean drawWallWater(GL2 gl, List<Cube> wall, List<Cube> water ){
		for (Cube cube :wall){
			drawCube(cube, gl);
		}
		
		for (Cube cube :water){
			drawCube(cube, gl);
		}
		return true;
	}
	public static void drawCube(Cube cube, GL2 gl){
		double x = cube.getX();
		double y = cube.getY();
		double z = cube.getZ();
		double h = cube.getH();
		
		
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		
		//gl.glColor3f(1f,0f,0f); //red color
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d(x, y+h, z); // Top Left Of The Quad (Top)
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d( x+h, y+h, z); // Top Right Of The Quad (Top)
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d( x+h, y+h, z+h ); // Bottom Right Of The Quad (Top)
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d( x, y+h, z+h ); // Bottom Left Of The Quad (Top)
			
	    
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d( x, y, z ); // Top Left Of The Quad (Bottom)
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d( x+h, y, z ); // Top Right Of The Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d( x+h, y, z+h ); // Bottom Right Of The Quad
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d( x, y, z+h ); // Bottom Left Of The Quad 
	      
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d( x, y+h, z+h ); // Top Left Of The Quad (Front)
	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d( x+h, y+h, z+h ); // Top Right Of The Quad (Front)
	    gl.glTexCoord2f(1.0f, 0.0f);  gl.glVertex3d( x+h, y, z+h ); // Bottom Right Of The Quad
	    gl.glTexCoord2f(0.0f, 0.0f);  gl.glVertex3d( x, y, z+h ); // Bottom Left Of The Quad 

	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d( x, y+h, z ); // Bottom Left Of The Quad
	    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d( x+h, y+h, z ); // Bottom Right Of The Quad
	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d( x+h, y, z ); // Top Right Of The Quad (Back)
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3d( x, y, z ); // Top Left Of The Quad (Back)
	      
	     //gl.glColor3f(0f,0f,1f); //red color
	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d( x, y+h, z ); // Top Left Of The Quad (Left)
	    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d( x, y+h, z+h ); // Top Right Of The Quad (Left)
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3d( x, y, z+h ); // Bottom Right Of The Quad
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3d( x, y, z ); // Bottom Left Of The Quad 

	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d( x+h, y+h, z+h ); // Top Left Of The Quad (Right)
	    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d( x+h, y+h, z ); // Top Right Of The Quad
	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d( x+h, y, z ); // Bottom Right Of The Quad
	    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d( x+h, y, z+h ); // Bottom Left Of The Quad
	    gl.glEnd();
	}
	
/*	public static void main(String[] args) {
		final GLProfile profile = GLProfile.get( GLProfile.GL2 );
	      GLCapabilities capabilities = new GLCapabilities( profile );
	      Factory fact = new Factory(2,  -5,  -5,  0);
	      // The canvas
	      final GLCanvas glcanvas = new GLCanvas( capabilities );
	      SceneRenderer renderer = new SceneRenderer(fact);
			
	      glcanvas.addGLEventListener( renderer );
	      glcanvas.setSize( 400, 400 );
			
	      final JFrame frame = new JFrame ( " Multicolored cube" );
	      frame.getContentPane().add( glcanvas );
	      frame.setSize( frame.getContentPane().getPreferredSize() );
	      frame.setVisible( true );
	      final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true);
			
	      animator.start();
	      
	}
	*/
	
}
