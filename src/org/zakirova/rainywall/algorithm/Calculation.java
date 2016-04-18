package org.zakirova.rainywall.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Calculation {
	
	public List<Integer> run(List<Integer> wall){
		if (wall==null) 
			return null;
		List<Integer> water;
		if (wall.size()<3){
			water= new ArrayList<>();
			for(int i=0; i<wall.size(); i++){
				water.add(new Integer(0));
			}
		}
		else{
			List<Integer> waterLevel = generateWaterLevel(wall);
			water = generateWater(wall, waterLevel);
		}
			return water;
	}
		
	private ArrayList<Integer> generateWaterLevel(List<Integer> wall){
		ArrayList<Integer> waterLevel = new ArrayList<Integer>(wall.size());
		for (int i=0; i<wall.size(); i++){
			waterLevel.add(new Integer(0));
		}
		LinkedList<Maximum> maximums= new LinkedList<Maximum>();
		//for(int i=0; i<wall.size(); i++){
		//	if(isFallingEdge)
		ListIterator<Integer> it = wall.listIterator();
		Integer hight=it.next();
		Integer nextHight=it.next();
		while(it.hasNext()){
			//add Maximums
			if(hight.compareTo(nextHight)>=0){
				while(!maximums.isEmpty()&&hight.compareTo(maximums.getLast().getHight())>=0){
					maximums.removeLast();
				}
				maximums.add(new Maximum(it.previousIndex()-1, hight));
			}
			//Going though falling edge
			while(it.hasNext()&&(hight.compareTo(nextHight)>=0)){
				if((hight.intValue()==0)||(nextHight.intValue()==0))
					maximums.clear();
				hight=nextHight;
				nextHight=it.next();
			}
			//Going through rising edge
			while(it.hasNext()&&(hight.compareTo(nextHight)<=0)){
				if ((!maximums.isEmpty())&&hight.compareTo(maximums.getLast().getHight())>=0){
					int level=0, pos=0;
					while((!maximums.isEmpty())&&hight.compareTo(maximums.getLast().getHight())>=0){
						level = Math.min(hight, maximums.getLast().getHight());
						pos =maximums.getLast().getPosition();
						maximums.removeLast();
					}
					fillWater(waterLevel, level , pos , it.previousIndex()-1 );
				}
				hight=nextHight;
				nextHight=it.next();
			}
			if (!maximums.isEmpty()){
				int endLevel, endPos;
				if(hight>=nextHight){
					endLevel = hight;
					endPos = it.previousIndex()-1; 
				}
				else{
					endLevel = nextHight;
					endPos = it.previousIndex();
				}
				int level = Math.min(endLevel, maximums.getLast().getHight());
				int startPos =maximums.getLast().getPosition();
				fillWater(waterLevel, level, startPos, endPos);
			}
		}
		return waterLevel;	
	}
	private List<Integer> generateWater(List<Integer> wall, List<Integer> waterLevel){
		if((wall==null)||(waterLevel==null)||wall.size()!=waterLevel.size()){
			return null;
		}
		List<Integer> water = new ArrayList<>(wall.size());
		for(int i=0; i<wall.size(); i++){
			if(wall.get(i)>=waterLevel.get(i))
				water.add(new Integer(0));
			else
				water.add(new Integer(waterLevel.get(i)-wall.get(i)));
		}
		return water;
	}
	private boolean fillWater(ArrayList<Integer> arr, Integer level,  Integer start, Integer end){
		if ((start>end)||(end>arr.size()-1)){
			return false;
		}
		for (int i=start+1; i<end;i++){
			arr.set(i, level);
		}
		return true;
	}
	
	/*private boolean isRisingEdge(int ind, ArrayList<Integer> array){
		if(ind+1>array.size()){
			return false;
		}
		
		if (array.get(ind)<array.get(ind+1)){
			return true;
		}
		else return false;
	}
	private boolean isFallingEdge(int ind, ArrayList<Integer> array){
		if(ind+1>array.size()){
			return true;
		}
		
		if (array.get(ind)>array.get(ind+1)){
			return true;
		}
		else return false;
	}
	private boolean isFlat(int ind, ArrayList<Integer> array){
		
	}*/
	private class Maximum{
		int position, hight;
		private Maximum(int pos, int hight){
			this.position=pos;
			this.hight=hight;
		}
		int getPosition(){
			return position;
		}
		Integer getHight(){
			return new Integer(hight);
		}
	}
	
	public static void main(String[] args) {
		Calculation calc = new Calculation();
		ArrayList<Integer> wall = new ArrayList<Integer>(7);
		wall.add(new Integer(1));
		wall.add(new Integer(3));
		wall.add(new Integer(2));
		wall.add(new Integer(2));
		wall.add(new Integer(5));
		wall.add(new Integer(2));
		wall.add(new Integer(5));
		
		List<Integer> water = calc.run(wall);
		System.out.println(water);
		System.out.println(wall);
	}
}


