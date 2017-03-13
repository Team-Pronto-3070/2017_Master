package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Timer;

/* methods: 
 * public Auto(Drive drive, Shooter shooter) - constructs the class
 * public void auto(int program) - runs the autonomous code for a given path
*/

public class Auto extends Drive {

	// Defines Pronto classes
	private Drive drive;
	private Shooter shooter;
	// Defines the timer
	private Timer timer;

	public Auto(Drive drive, Shooter shooter) {
		// Constructs the class

		// Tells the class which Pronto Classes to use
		this.drive = drive;
		this.shooter = shooter;

		// Initializes the timer
		timer = new Timer();
	}

	// Creates a variable for the step the autonomous is going through
	int step = 1;

	// NOTE: The corresponding ints for autonomous are as follows:
	// Center -> Center - 0
	// Left -> Left - 1
	// Right -> Right - 2
	// Center -> Left - 3
	// Center -> Right - 4

	// Creates an array for the first distance traveled by the robot in
	// autonomous
	double firstDist[] = { 6.5 - Pronstants.DISTANCE_OFFSET, 6.5 - Pronstants.DISTANCE_OFFSET,
			6.5 - Pronstants.DISTANCE_OFFSET, 6.5 - Pronstants.DISTANCE_OFFSET, 6.5 - Pronstants.DISTANCE_OFFSET };

	// Creates an array for the second distance traveled by the robot in
	// autonomous
	double secondDist[] = { 0, 1.5 - Pronstants.DISTANCE_OFFSET, 1.5 - Pronstants.DISTANCE_OFFSET,
			3 - Pronstants.DISTANCE_OFFSET, 3 - Pronstants.DISTANCE_OFFSET };

	// Creates an array for the third distance traveled by the robot in
	// autonomous
	double thirdDist[] = { 0, 0, 0, 1.5 - Pronstants.DISTANCE_OFFSET, 1.5 - Pronstants.DISTANCE_OFFSET };

	// Creates an array for the angle of the first turn during autonomous
	double firstTurn[] = { 0, 60, -60, -90, 90 };

	// Creates an array for the angle of the second turn during autonomous
	double secondTurn[] = { 0, 0, 0, 90, -90 };

	public void auto(int program) {
		// Runs the autonomous program for a given path

		// Creates an array for the distance traveled by the robot
		double realDist[] = drive.getDistanceTraveled();

		// Creates a variable equal to the current time according to the timer
		double time = timer.get();

		// Creates a state machine for the steps to go through for each
		// autonomous program
		switch (step) {

		// Checks if the code is on step one
		case 1:
			// Checks if the robot has traveled the first required distance
			if (realDist[2] > firstDist[program]) {
				// If so, move on to the next step and reset the gyro
				step++;
				drive.resetGyro();
			}

			else {
				// If not, drive the robot forward
				drive.encDrive(Pronstants.AUTO_DRIVE_SPEED);
			}

			break;

		// Checks if the robot is on the second step
		case 2:
			// Starts turning the robot the amount for the first turn, then
			// checks if it's done turning
			if (drive.turn(firstTurn[program], Pronstants.AUTO_TURN_SPEED)) {
				// If so, go on to the next step and reset the gyro
				step++;
				drive.resetGyro();
			}

			break;

		// Checks if the robot is on the third step
		case 3:
			// Checks if the robot has traveled the second required distance
			if (realDist[2] > secondDist[program]) {
				// If so, move on to the next step and reset the gyro
				step++;
				drive.resetGyro();
			}

			else {
				// If not, drive the robot forward
				drive.encDrive(Pronstants.AUTO_DRIVE_SPEED);
			}

			break;

		// Checks if the robot is on the fourth step
		case 4:
			// Starts turning the robot the amount for the second turn, then
			// checks if it's done turning
			if (drive.turn(secondTurn[program], Pronstants.AUTO_TURN_SPEED)) {
				// If so, move on to the next step and reset the gyro
				step++;
				drive.resetGyro();
			}

			break;

		// Checks if the robot is on the fifth step
		case 5:
			// Checks if the robot has traveled the third required distance
			if (realDist[2] > thirdDist[program]) {
				// If so, move on to the next step and reset and start the timer
				step++;
				timer.reset();
				timer.start();
			}

			else {
				// If not, drive the robot forward
				encDrive(Pronstants.AUTO_DRIVE_SPEED);
			}

			break;

		// Checks if the robot is on the sixth step
		case 6:
			// Checks if one second has passed
			if (time > 1) {
				// If so, go to the next step
				step++;
			}

			else {
				// If not, don't move the robot
				drive(0, 0);
			}

			break;

		// Checks if the robot is on the seventh step
		case 7:
			// Checks if we are on the side with the fuel hopper on it
			if (program == 2 || program == 3) {
				// If so, shoot at the high goal
				shooter.shoot();
			}

			else {
				// If not, don't move the robot
				drive(0, 0);
			}

			break;
		}
	}
}