/* CRITTERS Critter2.java
 * EE422C Project 4 submission by
 * <Ashvin Roharia>
 * <ar34426>
 * <16475>
 * <Ram Muthukumar>
 * <rm48763>
 * <16470>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5;

import assignment5.Critter.CritterShape;

/*
 * will just walk around
 * every time-step makes it more belligerent (has a higher chance of fighting)
 * eventually if it hasn't fought, it will always choose to fight and it will reset its belligerence 
 */
public class Critter2 extends Critter {
	private int belligerence;
	
	public Critter2(){
		belligerence = 10;
	}
	
	@Override
	public String toString(){return "2";}
	
	@Override
	public void doTimeStep() {
		walk(Critter.getRandomInt(8));
		if(getEnergy() > 200) reproduce(new Critter2(), Critter.getRandomInt(8));
		if(belligerence != 1) belligerence--;
	}

	@Override
	public boolean fight(String oponent) {
		if(Critter.getRandomInt(belligerence) == 1){
			belligerence = 10;
			return true;
		}
		else return false;
	}

	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BROWN; }

}
