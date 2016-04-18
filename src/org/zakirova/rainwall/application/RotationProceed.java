package org.zakirova.rainwall.application;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.zakirova.rainwall.visualization.SceneRenderer;

import com.jogamp.opengl.awt.GLCanvas;
/**
 * Class provides rotation of picture according to dragging of Mouse.
 * 
 * @author Irina Zakirova
 *
 */
public class RotationProceed implements MouseListener, MouseMotionListener {
	int oldX=0, oldY=0;
	int newX=0, newY=0;
	int perpX=0; 
	int perpY=0;
	int perpZ=0;
	SceneRenderer renderer;
	GLCanvas glcanvas;
	
	public RotationProceed(SceneRenderer renderer, GLCanvas glcanvas){
		this.renderer=renderer;
		this.glcanvas=glcanvas;
	}
	
	public void init(int x, int y){
		oldX=x;
		oldY=y;
		newX=x;
		newY=y;
	}
	
	public void reset(){
		oldX=0;
		oldY=0;
		newX=0;
		newY=0;
	}
	public void update(int x, int y){
		oldX=newX;
		oldY=newY;
		newX=x;
		newY=y;
		calcPerpend();
	}
	private void calcPerpend(){
		perpX = newY - oldY;
		perpY = newX - oldX;
		perpZ = 0;//oldX*newY-oldY*newX;
	}
	public int getPerpendX(){
		return perpX;
	}
	public int getPerpendY(){
		return perpY;
	}
	public int getPerpendZ(){
		return perpZ;
	}
	public float getAngleY(){
		double sum = Math.abs(newX-oldX)+Math.abs(newY-oldY);
		if (Math.abs(sum)<0.00001) sum=1; 
		double angle = (newX-oldX)/sum;
		return (float) (angle*3.0f);
	}
	public float getAngleX(){
		double sum = Math.abs(newX-oldX)+Math.abs(newY-oldY);
		if (Math.abs(sum)<0.00001) sum=1;
		double angle = (newY-oldY)/sum;
		return (float) (angle*2.0f);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		init(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		reset();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		//jtfmouse.setText("x="+e.getX()+"; y="+e.getY());
		if (e.getX()<0 || e.getY()<0)
			return;
		update(e.getX(), e.getY());
		if(renderer!=null){
			renderer.updateRot(perpX, perpY, 
					perpZ, getAngleX(), getAngleY());
			glcanvas.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
