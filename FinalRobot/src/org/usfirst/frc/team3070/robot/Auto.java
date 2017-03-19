package org.usfirst.frc.team3070.robot;

/* methods: 
public void run(int program) - Runs the autonomous code according to an inputted program
public int chooseAuto(boolean button1, boolean button2) - Returns an integer representing an autonomous program based on 2 buttons
*/

public class Auto {
	// Defines classes
	private ProntoGyro gyro;
	private Drive drive;
	
	// Constructs the class
	public Auto(ProntoGyro gyro, Drive drive) {
		// Tells the class to use these objects,
		// not duplicate versions of these objects
		this.gyro = gyro;
		this.drive = drive;
	}

	// Defines the array for rotations
	double[] rotations;

	// Defines a variable for the distance traveled
	double distanceTraveled;

	// initial heading
	double initHeading = 0;

	// Turning distance
	double firstTurn[] = { -60, 60, 0 };

	double firstDist[] = { 5.755, 6.68, 6.68 };
	
	double secondDist = 3.21;

	// Defines the variable for what step the code is on with a starting value of 1
	int step = 1;
	
	// Runs the autonomous code according to an inputted program
	public void run(int program) {
		// Sets rotations to the array "getDistanceTraveled" (see drive)
		rotations = drive.getDistanceTraveled();
		
		// Sets distanceTraveled to the average of the two encoder values in feet
		distanceTraveled = rotations[2];
		
		// Creates a state engine for the integer "step"
		switch(step) {
		
		// Checks if the robot is on step 1
		case 1:
			
			// Checks if the robot has traveled the first distance or further
			if (distanceTraveled >= firstDist[program]) {
				
				// If so, tell the console to go to the next step,
				step ++;
				// reset the distance traveled by the drive talons,
				drive.resetDistanceTraveled();
				// reset the gyro,
				drive.resetGyro();
				// and don't read the rest of this program
				break;
			}
			
			// Otherwise, drive the robot straight
			drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			
		// Checks if the robot is on step 2
		case 2:
			step++;
			break;
		case 3:
			// Start turning the robot, then check if the robot is done turning
			if (drive.turn(firstTurn[program], Pronstants.AUTO_TURN_SPEED)) {
				
				// If so, tell the console to go to the next step,
				step ++;
				// reset the distance traveled by the drive talons,
				drive.resetDistanceTraveled();
				// reset the gyro,
				drive.resetGyro();
				// and don't read the rest of this program
				break;
			}
		
		// Checks if the robot is on step 3
		case 4:
			step++;
			break;
		case 5:
			// Checks if the robot is in the center position,
			// or if the robot has traveled the second distance or further
			if (program == Pronstants.AUTO_CENTER || distanceTraveled >= secondDist) {
				
				// If so, tell the console to go to the next step,
				step ++;
				// reset the distance traveled by the drive talons,
				drive.resetDistanceTraveled();
				// reset the gyro,
				drive.resetGyro();
				// and don't read the rest of this program
				break;
			}
			
			// Otherwise, drive the robot straight
			drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
		
		// Checks if the robot is on step 4
		case 6:
			// If so, don't do anything
			break;
		}
	}
	
	// Returns an integer representing an autonomous program based on 2 buttons
	public int chooseAuto(boolean button1, boolean button2) {
		// Checks if button1 is pressed
		if (button1) {
			// If so, return the integer representing the right autonomous program
			return Pronstants.AUTO_RIGHT;
		}
		
		// Checks if button2 is pressed
		else if (button2) {
			// If so, return the integer representing the left autonomous program
			return Pronstants.AUTO_LEFT;
		}
		
		else {
			// Otherwise, run the center autonomous program
			// The reason there isn't a null option is
			// that there is no situation where we would not want 5 extra points,
			// and the center autonomous program only drives straight and then stops
			return Pronstants.AUTO_CENTER;
		}
	}
}