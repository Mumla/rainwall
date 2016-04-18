package org.zakirova.rainwall.visualization;
/**
 * Cube class. It is used as primitive element of scene.
 * 
 * @author Irina Zakirova
 * @version 1.0.0 
 * 16.04.16
 *
 */

public class Cube {
	double h, x, y, z;
	
	public Cube(double height, double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = height;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	

}
