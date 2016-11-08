/* CRITTERS Critter.java
 * EE422C Project 5 submission by
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

import java.util.List;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import assignment5.Params;

public abstract class Critter {

	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	static List<Integer> moved = new java.util.ArrayList<Integer>();
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected String look(int direction, boolean steps) {
		//subtract look energy cost
		this.energy = this.energy - Params.look_energy_cost;
		//initializations
		int distance;
		int x = this.x_coord;
		int y = this.y_coord;
		
		//if steps is true, distance = 2
		if (steps)
			distance = 2;
		else 
			distance = 1;
		
		switch (direction) {
		//right
		case 0: x = x + distance;
		break;
		
		//up-right
		case 1: x = x + distance;
		y = y - distance;
		break;
		
		//up
		case 2: y = y - distance;
		break;
		
		//up-left
		case 3: x = x - distance;
		y = y - distance;
		break;
		
		//left
		case 4: x = x - distance;
		break;
		
		//down-left
		case 5: x = x - distance;
		y = y + distance;
		break;
		
		//down
		case 6: y = y + distance;
		break;
		
		//down-right
		case 7: x = x + distance;
		y = y + distance;
		break;
		
		//error
		default:
			break;
		}

		//____x -> x____
		if (x > Params.world_width - 1)
			x = x - Params.world_width;
		//x____ -> ____x
		else if (x < 0)
			x = x + Params.world_width;
		//move to top of the grid
		if (y> Params.world_height - 1)
			y = y - Params.world_height;
		//move to bottom of the grid
		else if (y < 0)
			y = y + Params.world_height;

		for (Critter c : population) {
			if (c.x_coord == x && c.y_coord == y)
				return c.toString();
		}
		return null;
	
	}
	
	//to keep track if critter is alive
	private boolean alive = true;
	public boolean isAlive(){
		return alive;
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean hasMoved; // true if Critter has moved in this time step
	
	private final void move(int direction, int distance) {
		switch (direction) {
			//right
			case 0: this.x_coord = this.x_coord + distance;
			break;
			
			//up-right
			case 1: this.x_coord = this.x_coord + distance;
			this.y_coord = this.y_coord - distance;
			break;
			
			//up
			case 2: this.y_coord = this.y_coord - distance;
			break;
			
			//up-left
			case 3: this.x_coord = this.x_coord - distance;
			this.y_coord = this.y_coord - distance;
			break;
			
			//left
			case 4: this.x_coord = this.x_coord - distance;
			break;
			
			//down-left
			case 5: this.x_coord = this.x_coord - distance;
			this.y_coord = this.y_coord + distance;
			break;
			
			//down
			case 6: this.y_coord = this.y_coord + distance;
			break;
			
			//down-right
			case 7: this.x_coord = this.x_coord + distance;
			this.y_coord = this.y_coord + distance;
			break;
			
			//error
			default: break;
		}
		
		if (this.x_coord > Params.world_width - 1) { // relocate to left side
			this.x_coord = this.x_coord - Params.world_width;
		}
		else if (this.x_coord < 0) { // relocate to right side
			this.x_coord = this.x_coord + Params.world_width;
		}
		if (this.y_coord > Params.world_height - 1) { // relocate to top
			this.y_coord = this.y_coord - Params.world_height;
		}
		else if (this.y_coord < 0) { // relocate to bottom
			this.y_coord = this.y_coord + Params.world_height;
		}
	}
	
	/**
	 * Critters moves a distance of one in the given direction
	 * @param direction is the direction the critter will walk
	 */
	protected final void walk(int direction) {
		this.energy = this.energy - Params.walk_energy_cost;
		if (hasMoved)
			return;
		this.move(direction, 1);
		hasMoved = true;
	}
	
	/**
	 * Critters moves a distance of one in the given direction
	 * @param direction is the direction the critter will run
	 */
	protected final void run(int direction) {
		this.energy = this.energy - Params.run_energy_cost;
		if (hasMoved)
			return;
		this.move(direction, 2);
		hasMoved = true;
		}
	
	protected final void reproduce(Critter offspring, int direction) {
		
		if (this.energy < Params.min_reproduce_energy)
			return;
		
		offspring.energy = this.energy / 2;
		this.energy = (int) Math.ceil(this.energy /2);
		
		//puts baby next to parent
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.move(direction, 1);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		try{
			//adds input to the world if its valid
			Critter c = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
			Critter.population.add(c);
			
			//initializations
			c.energy=Params.start_energy;
			c.x_coord = Critter.getRandomInt(Params.world_width);
			c.y_coord = Critter.getRandomInt(Params.world_height);
		}
		catch(ClassNotFoundException|InstantiationException|IllegalAccessException|NoClassDefFoundError e){
//			Main.hasDisplayedError = true; //so that error isn't displayed multiple times
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances() {
		List<Critter> result = new java.util.ArrayList<Critter>();
//		Class<?> critterClass = null;

/*		try {
			critterClass = Class.forName(myPackage + "." + critter_class_name);
		} 
		catch (ClassNotFoundException|NoClassDefFoundError e) {
			throw new InvalidCritterException(critter_class_name);
//			Main.hasDisplayedError = true;
		}*/

			for (Critter critter : population) {
					result.add(critter);
			}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats() {
		List<Critter> crits = null;
		crits = Critter.getInstances();
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : crits) {
			String crit_string = crit.getClass().getSimpleName();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
//		String prefix = "";
		Main.stat.clear();
		for (String s : critter_count.keySet()) {
			Main.stat.appendText(""+ s + ": " + critter_count.get(s) + " Critters\n");
//			prefix = ", ";
		}
//		System.out.println();	
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	/**
	 * Returns true if there'a critter on the grid location 
	 */
	private boolean isNotFree() {
		for (Critter critter : population) {
			if (critter == this)
				continue;
			if ((critter.x_coord == this.x_coord) && (critter.y_coord == this.y_coord))
				return true;
		}
		return false;
	}
	
	public static void critter1Wins(Critter critter1, Critter critter2){
		critter1.energy = critter1.energy + (critter2.energy)/2;
		critter2.energy = 0;
		critter2.alive = false;
	}
	
	public static void critter2Wins(Critter critter1, Critter critter2){
		critter2.energy = critter2.energy + (critter1.energy)/2;
		critter1.energy = 0;
		critter1.alive = false;
	}
	
	public static void worldTimeStep() {
		java.util.ArrayList<Critter> removeList = new java.util.ArrayList<Critter>();
		
		//doTimeStep for current critters in the grid
		for (Critter critter: population) {
			critter.hasMoved = false;
			critter.doTimeStep();
			if (critter.energy <= 0) 
				critter.alive = false; 
			//energy decreases
			critter.energy = critter.energy - Params.rest_energy_cost;
			//add to remove list if dead or energy reaches 0
			if (critter.energy <= 0 || !critter.isAlive()) 
				removeList.add(critter);	
		}
		
		//fights
		//iterate through each potential critter interaction
		for (Critter critter1: population) {
			for (Critter critter2: population) {
				if (critter1 == critter2) 
					continue; //ignore if comparing the same critters
				//if the 2 critters occupy the same grid location
				if ((critter1.x_coord == critter2.x_coord ) && (critter1.y_coord == critter2.y_coord)) {
					//ignore if one is dead, it will be removed later
					if (critter1.isAlive() && critter2.isAlive()){
						int C1tempx = critter1.x_coord;
						int C1tempy = critter1.y_coord;
						int C2tempx = critter2.x_coord;
						int C2tempy = critter2.y_coord;
						boolean critter1_fight = critter1.fight(critter2.toString());
						boolean critter2_fight = critter2.fight(critter1.toString());
						
						//critter1 
						
						//critter is dead if it runs out of energy
						if (critter1.energy <= 0) 
							critter1.alive = false; 
						if (critter1.isNotFree()) {
							critter1.x_coord = C1tempx;
							critter1.y_coord = C1tempy; 
						}
						
						//critter2
						
						//critter is dead if it runs out of energy
						if (critter2.energy <= 0)
							critter2.alive = false;
						if (critter2.isNotFree()) { 
							critter2.x_coord = C2tempx; 
							critter2.y_coord = C2tempy; 
						}
						
						//if the 2 critters occupy the same grid location
						if ((critter1.x_coord == critter2.x_coord ) && (critter1.y_coord == critter2.y_coord)) {
							//ignore if one is dead, it will be removed later
							if (critter1.isAlive() && critter2.isAlive()){
							
								int critter1Rand = 0;
								int critter2Rand = 0;
								if (critter1_fight)
									critter1Rand = Critter.getRandomInt(critter1.energy);
								if (critter2_fight)
									critter2Rand = Critter.getRandomInt(critter2.energy);
								
								//if critter 1 is winner
								if (critter1Rand > critter2Rand)
									critter1Wins(critter1, critter2);		

								//if critter2 is winner
								else if (critter2Rand > critter1Rand)
									critter2Wins(critter1, critter2);		
								
								//if tie, randomize winner
								else {
									int oneOrZero = Critter.getRandomInt(2); //int will be 0 or 1
									if (oneOrZero == 1) 
										critter1Wins(critter1, critter2);
									else 
										critter2Wins(critter1, critter2);		
								}
							}
						}
					}
				}
			}
		} 
		
		
		//new algae
		try {
			for (int i  = 0; i < Params.refresh_algae_count; i++) {
				Critter.makeCritter("Algae");
			}
		} 
		catch (InvalidCritterException|NoClassDefFoundError e) {
			//System.out.println("error processing: " + Main.in);
		}
		
		//new babies
		for (Critter critter : babies) {
			critter.alive = true;
			population.add(critter);
		}
		java.util.ArrayList<Critter> babiesList = new java.util.ArrayList<Critter>(babies);
		babies.removeAll(babiesList);
		
		//remove dead
		for(Critter critter: removeList){			
			population.remove(critter);
		}
	}
	
	public static void displayWorld() {
		Painter.paint();
		for (Critter crit : population) {
			Painter.paintCritter(crit.x_coord, crit.y_coord, crit.viewShape(), crit.viewColor(), crit.viewOutlineColor(), crit.viewFillColor());
		}
		runStats();
	}
}
