# Critters-2
 - EE422C Project 5 submission by
 - <Ashvin Roharia>
 - <Ram Muthukumar>

 - Fall 2016
This critter GUI has 3 windows:
 - Critter World
Hold the grid where the critters reside in. The length and width of the world can be changed in
the Params file. The grid stays static until a worldTimeStep - the window then shows the
animation of the different shaped/colored critters.

 - Run Stats
Shows the stats of all of the critters that are populating the critter world.

 - Critter Setup
Quit - allows you to exit the program
Critter: - allows you to type the critter’s name that you’re trying to add
Critter # - allows you to specify the number of the specified critter you’ll want to add
Add - adds the specified critters into a queue which will be added in the next time step
Critters to add next step will appear here - displays what critters are in the queue
Time step # - Indicate how many time steps you want to perform
Update World - runs the specified amount of time steps on the actual grid
Speed - slider to indicate how fast you want the simulation to run
Simulate - executes an infinite loop of time steps based on the given speed
Stop - halts the infinite loop of time steps
