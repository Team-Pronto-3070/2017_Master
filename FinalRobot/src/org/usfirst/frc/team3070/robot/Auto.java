package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Timer;

/* methods: 
public void run(int mode, boolean shoot) - Runs the autonomous based on a path and if we want to shoot or not
public double getSelected(boolean button1, boolean button2, boolean button3) - Selects the autonomous based on 3 buttons
*/

public class Auto {
	// Initializes classes
	private Drive drive;
	private Shooter shooter;
	
	// Constructs the class
	public Auto(Drive drive, Shooter shooter) {
		this.drive = drive;
		this.shooter = shooter;
	}

	// Turning distances for the different autonomous programs
	double firstTurn[] = {0, -60, 60};
	
	// Driving distances for the different autonomous programs
	double firstDist[] = {5.755, 6.68, 6.68};
	double secondDist[] = {0, 3.21, 3.21};

	// Keeps track of what part of the autonomous code the robot is on
	int step = 1;
	
	// Creates a double to keep track of the distance traveled
	double distTraveled = 0;

	// Runs the autonomous based on a path and if we want to shoot or not
	public void run(int mode, boolean shoot) {
		// Sets distTraveled to the distance traveled by the robot in feet
		distTraveled = drive.getDistanceTraveled()[2];
		
		// Creates a state engine for the value of step
		// Note to Pronto devs: state engines are a fun way to make your code look nice.
		// Y'all should use them more often
		switch(step) {
		// Checks if the robot is on step 1
		case 1:
			// Checks if the robot has traveled the first distance or further
			if (distTraveled >= firstDist[mode]) {
				// If so, go to the next step,
				step ++;
				// reset the gyro,
				drive.resetGyro();
				// reset the distance traveled,
				drive.resetDistanceTraveled();
				// and don't read the rest of this program
				break;
			}
			
			// Otherwise, drive the robot forward and don't read the rest of this program
			drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			break;

		// Checks if the robot is on step 2
		case 2:
			// If so, wait one tenth of a second, then go to the next step and don't read the rest of this program
			Timer.delay(0.1);
			step ++;
			break;
		
		// Checks if the robot is on step 3
		case 3:
			// Starts turning the robot, then checks if the robot is done turning
			if (drive.turn(firstTurn[mode], Pronstants.AUTO_TURN_SPEED)) {
				// If so, go to the next step,
				step ++;
				// reset the gyro,
				drive.resetGyro();
				// reset the distance traveled,
				drive.resetDistanceTraveled();
				// and don't read the rest of this program
				break;
			}
			// Otherwise, don't read the rest of this program
			break;
		
		// Checks if the robot is on step 4
		case 4:
			// If so, wait one tenth of second, go to the next step and don't read the rest of this program
			Timer.delay(0.1);
			step ++;
			break;
		
		// Checks if the robot is on step 5
		case 5:
			// Checks if the robot has traveled the second distance or further
			if (distTraveled >= secondDist[mode]) {
				// If so, go to the next step,
				step ++;
				// reset the gyro,
				drive.resetGyro();
				// reset the distance traveled,
				drive.resetDistanceTraveled();
				// and don't read the rest of this program
				break;
			}
			// Otherwise, drive the robot straight and don't read the rest of this program
			drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			break;
		
		// Checks if the robot is on step 6
		case 6:
			// If so, wait a tenth of a second, go to the next step and don't read the rest of this program
			Timer.delay(0.1);
			step ++;
			break;
		
		// Checks if the robot is on step 7
		case 7:
			// TODO: Put vision here
			step ++;
			break;
		
		// Checks if the robot is on step 8
		case 8:
			// If so, wait one tenth of a second, go to the next step and don't read the rest of this program
			Timer.delay(0.1);
			step ++;
			break;
		
		// Checks if the robot is on step 9
		case 9:
			// Checks if the program wants the robot to shoot
			if (shoot) {
				// If so, run the shooter
				shooter.shoot();
			}
		}
	}

	// Selects the autonomous based on 3 buttons
	public double getSelected(boolean button1, boolean button2, boolean button3) {
		// If the first button is pressed, return the integer representing the center autonomous code
		if (button1) return Pronstants.AUTO_CENTER;
		
		// Otherwise, if the second button is pressed,
		// return the integer representing the right autonomous code
		else if (button2) return Pronstants.AUTO_RIGHT;
		
		// Otherwise, if the third button is pressed,
		// return the integer representing the left autonomous code
		else if (button3) return Pronstants.AUTO_LEFT;
		
		// If none of the above cases are true, return the integer
		// representing the center autonomous
		// This is here as a failsafe since no situation could be thought of
		// where you wouldn't want 5 extra points
		else return Pronstants.AUTO_CENTER;
	}
}