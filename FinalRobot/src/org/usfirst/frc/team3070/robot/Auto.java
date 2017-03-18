package org.usfirst.frc.team3070.robot;

/* methods: 
 * public void autoC() - drive center
 * public void autoOutside(int side) - drive from outer start position to outer lift
 * public void autoOutsideCenter(int side) - drive from outer start position to center
*/

public class Auto {
	// initializes classes
	private Drive drive = new Drive();
	private ProntoGyro gyro = new ProntoGyro();

	public Auto() {
	}

	// initial distance
	double[] rotations = drive.getDistanceTraveled();
	double initDist = 0;
	// Difference in distance
	double diffDist;

	// initial heading
	double initHeading = 0;

	// Flags for if we're turning
	boolean firstTurning = false;
	boolean secondTurning = false;

	// Turning distance
	double firstTurn = 0;
	double secondTurn = 0;

	// When it's time to shoot
	boolean robotShoot = false;
	int state = 1;
	// Things done during Autonomous

	public void autoC() {
		// autonomous code for the center to the center gearloading station
		// Drives straight for 5 meters, then stops

		// updates distance
		rotations = drive.getDistanceTraveled();
		// difference in distance
		diffDist = rotations[2] - initDist;

		// If the robot has not gone 5 feet, drive straight forward
		if (diffDist < Pronstants.AUTO_CENTER_DIST) {
			drive.driveRobotStraight();
		}
		// If the robot has gone 5 feet, let vision take over
		else {
			// TODO: implement vision here
			drive.drive(0, 0);
		}
	}

	static boolean turnStarted = false;

	public void autoOutsideRight() {
		// autonomous code for going to an outside gearloader
		// from the same side starting position
		initHeading = gyro.getOffset();
		// updates distance
		rotations = drive.getDistanceTraveled();
		// difference in distance
		diffDist = rotations[2] - initDist;
		// difference in angle
		// double diffAngle = gyro.getHeading()- initHeading;
		firstTurn = -60;

		switch(state) {
		case 1:
			if (diffDist < 6.68) {
				drive.driveRobotStraight();
			} else {
				state = 2;
			}
			break;
		case 2:
			if(!drive.turn(-60, Pronstants.AUTO_TURN_SPEED)){
				break;
			}
			else {
				state = 3;
				drive.resetDistanceTraveled();
			}
			break;
		case 3:
			if(diffDist < 3.21) {
				drive.driveRobotStraight();
			} else {
				drive.drive(0, 0);
				
			}
			break;
		}
	}
	public void autoOutsideLeft() {
		rotations = drive.getDistanceTraveled();
		diffDist = rotations[2] - initDist;
		initHeading = gyro.getOffset();
		switch(state) {
		case 1:
			if (diffDist < 5) {
				drive.driveRobotStraight();
			} else {
				state = 2;
			}
			break;
		case 2:
			if(!drive.turn(-60, Pronstants.AUTO_TURN_SPEED)){
				break;
			}
			else {
				state = 3;
				drive.resetDistanceTraveled();
			}
			break;
		case 3:
			if(diffDist < 2) {
				drive.driveRobotStraight();
			} else {
				drive.drive(0, 0);
				
			}
			break;
		}

	}
	
	
	
}