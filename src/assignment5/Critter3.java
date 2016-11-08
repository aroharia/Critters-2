/* CRITTERS Critter3.java
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
 * This Critter only likes to move diagonally or in standard up, down, left or right, as long as look returns no string. Opposite of how parent moved
 * low confidence Critter. It assumes it will lose any fight and attempts to shoot out a baby before even deciding to fight
 * has a 50/50 chance to fight
 * 
 */
public class Critter3 extends Critter {
	int moveType;
	
	public Critter3(){
		moveType = 1;
	}
	public Critter3(int mt){
		if (mt == 0) moveType = 0;
		else moveType = 1;
	}
	@Override
	public String toString(){ return "3"; }
	@Override
	public void doTimeStep() {
		int decision = (2 * Critter.getRandomInt(4)) + moveType;
		if(look(decision, false)==null){
			walk(decision);
		}
	}

	@Override
	public boolean fight(String oponent) {
		reproduce(new Critter3(this.moveType % 1), Critter.getRandomInt(8));
		
		if(Critter.getRandomInt(2) == 1) return true;
		return false;
	}
	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BROWN; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.GREEN;}
	
}
