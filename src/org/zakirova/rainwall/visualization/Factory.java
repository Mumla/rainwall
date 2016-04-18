package org.zakirova.rainwall.visualization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Factory. It is used to create elements for wall and water.
 * Elements are composed along the X - axis.
 * 
 * @author Irina Zakirova
 * @version 1.0.0 16.04.16
 */
public class Factory {
	
	double elementHight=0;
	double maxHight=0;
	double lenght=0;
	double centerX, centerY, centerZ;
	LinkedList<Cube> wall = new LinkedList<>();
	LinkedList<Cube> water = new LinkedList<>();
	
	public Factory(int elementHight, double centerX, double centerY, double centerZ){
		this.elementHight = elementHight;
		this.centerX = centerX;
		this.centerY = centerY;
		this.centerZ = centerZ;
	}	
	
	public LinkedList<Cube> getWall() {
		return wall;
	}
	public LinkedList<Cube> getWater() {
		return water;
	}
	public double getLength(){
		return lenght;
	}
	public double getMaxHeight(){
		return maxHight;
	}
	public double initMaxHeight(List<Integer> wall, List<Integer> water){
		int maxElem=0;
		if((wall!=null)&&(water!=null)&&(wall.size()==water.size())){
			for(int i=0; i<wall.size(); i++){
				if (water.get(i)+wall.get(i)>maxElem)
					maxElem=water.get(i)+wall.get(i);
			}
			return maxElem*elementHight;
		}
		else
			return 0;
	}
	
	public boolean generate(List<Integer> wall, List<Integer> water){
		if((wall==null)||(water==null)||(wall.size()!=water.size()))
			return false;
		this.maxHight = initMaxHeight(wall, water);
		this.lenght = wall.size()*elementHight;
		this.wall = generateWall(wall);
		this.water = generateWater(water, wall);
		
		return true;
	}
	private LinkedList<Cube> generateWall(List<Integer> wall){
		LinkedList<Cube> wallCubes = new LinkedList<Cube>();
		double x=centerX-lenght/2;
		double z=centerZ-elementHight/2;
		for (Integer columnH:wall){
			double y=centerY-maxHight/2;
			for(int i=0; i<columnH; i++){
				Cube cube = new Cube(elementHight, x, y, z);
				wallCubes.add(cube);
				y+=elementHight;
			}
			x+=elementHight;
		}
		return wallCubes;
	}
	
	private LinkedList<Cube> generateWater(List<Integer> water, List<Integer> wall){
		LinkedList<Cube> waterCubes = new LinkedList<Cube>();
		double x=centerX-lenght/2;
		double z=centerZ-elementHight/2;
		double startY=centerY-maxHight/2;
		for (int j=0; j<water.size(); j++){
			double y=startY+wall.get(j)*elementHight;
			for(int i=0; i<water.get(j); i++){
				Cube cube = new Cube(elementHight, x, y, z);
				waterCubes.add(cube);
				y+=elementHight;
			}
			x+=elementHight;
		}
		return waterCubes;
	}
}
