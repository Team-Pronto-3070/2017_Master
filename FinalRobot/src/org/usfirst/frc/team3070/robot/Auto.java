package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/* methods:
public void autoC()
public void autoOutside(int side)
public void autoOutsideCenter(int side)
*/
public class Auto {
	// initializes drive class
	private Drive drive = new Drive();
	// initializes shooter class
	private Shooter shooter = new Shooter();
	
	public Auto(Drive drive) {
		// Auto constructor
		this.drive = drive;
	}

	// Things done during Autonomous
	public void autoSkeleton() {
		autoC();
	}

	public void autoC() {
		// autonomous code for the center to the center gearloading station
		// creates a variable for the array "getDistanceTraveled" (see drive)
		double firstDistance = 3;
		// creates an variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		// checks if the robot has not gone 5 feet
		if (rotations[2] < firstDistance) {
			// if not, drive straight forward
			drive.driveRobotStraight();
		}
		// checks if the robot has gone 5 feet
		else {
			// TODO: implement vision here
			drive.drive(0, 0);
		}
	}

	public void autoOutside(int side) {
		// autonomous code for going to an outside gearloader
		// from the same side starting position
		// creates a double for the value of the first turn
		double firstTurn = 0;
		// creates an array of doubles for the value
		// "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		
		// creates a case statement for the value of "side"
		switch(side) {
		// checks if the value of side is equal to
		// the pronstants variable "AUTO_SIDE_LEFT"
		case Pronstants.AUTO_SIDE_LEFT:
			// if it is, set firstTurn to 60
			firstTurn = 60;
			break;
		// checks if the value of the side equal to
		// the pronstants variable "AUTO_SIDE_RIGHT"
		case Pronstants.AUTO_SIDE_RIGHT:
			// if so, set firstTurn to -60
			firstTurn = -60;
			break;
			default:
			break;
		}
		//creates a boolean for shooter
		boolean robotShoot;
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			// if so, drive straight forward
			drive.driveRobotStraight();
			robotShoot = false;
		}
		// checks if the robot has gone 5 feet and the first turn is not finished
		else if (drive.turn(firstTurn, Pronstants.AUTO_DRIVE_SPEED) == false) {
			// if so, turn it a number of degrees equal to "firstTurn",
			// reset the distance traveled, and set robotShoot to false
			drive.turn(firstTurn, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
			robotShoot = false;
		}
		// checks if the first turn has finished
		else if (drive.turn(firstTurn, Pronstants.AUTO_DRIVE_SPEED) == true) {
			// if so, start vision here and set robotShoot to true
			drive.drive(0, 0);
			robotShoot = true;
			// TODO: finish vision and implement here
		}
		// checks if robotShoot is true
		if (robotShoot = true) {
			// if so, start shooting
//			shooter.autoShoot();
		}
	}

	public void autoOutsideCenter(int side) {
		// autonomous code for a non-center side
		//to the center gearloading station
		
		//creates a double for the first and second turns
		double firstTurn = 0;
		double secondTurn = 0;
		//creates a variable equal to the array "getDistanceTraveled"
		// (see drive)
		double[] rotations = drive.getDistanceTraveled();
		
		// creates a case statement for the value of "side"
		switch(side) {
			// checks if side is equal to the Pronstants variable
			// "AUTO_SIDE_LEFT"
			case Pronstants.AUTO_SIDE_LEFT:
				// if so, set firstTurn to 90 and secondTurn to -90
				firstTurn = 90;
				secondTurn = -90;
				break;
			// checks if side is equal to the Pronstants variable
			// "AUTO_SIDE_RIGHT"
			case Pronstants.AUTO_SIDE_RIGHT:
				// if so, set firstTurn to -90 and secondTurn to 90
				firstTurn = -90;
				secondTurn = 90;
				break;
			default:
				break;
		}
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			//if so, drive straight forward
			drive.driveRobotStraight();
		}
		//checks if the robot has gone 5 feet and the first is not finished
		else if (drive.turn(firstTurn, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if so, turn it an amount of degrees equal to firstTurn
			// and reset the distance traveled
			drive.turn(firstTurn, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		//checks if the first turn has finished
		//and the robot has not traveled an additional 2 feet
		else if (drive.turn(firstTurn, Pronstants.AUTO_DRIVE_SPEED) == true && rotations[2] < 2) {
			//if so, drive the robot straight
			drive.driveRobotStraight();
		}
		//checks if the robot has traveled an additional 2 feet
		//and the second has not finished
		else if (drive.turn(secondTurn, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if so, turn the robot to face the center gearloader (aka an amount
			// of degrees equal to secondTurn)
			drive.turn(secondTurn, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		//checks if the second turn has finished
		else if (drive.turn(secondTurn, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			//TODO: implement vision here
		}
	}
//	int counter = 0;
//	 counter ++;
//	 SmartDashboard.putString("DB/String 1", String.format("counter = %d",
//	 counter));
//	 double dashData = SmartDashboard.getNumber("DB/Slider 0", 0.0);
//	 SmartDashboard.putString("DB/String 2", String.format("dashData =
//	 %f", dashData));
//	 return;
	
	public void dummy1() {
		SmartDashboard.putString("DB/String 1", "Almost Got there, boys!");
	}

	public void dummy2() {
		SmartDashboard.putString("DB/String 2", "Diggity dun did it, boys!");
	}
	
	public void dummy3() {
		if (drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			drive.turn(90, Pronstants.AUTO_DRIVE_SPEED);
		}
		else {
			drive.drive(0, 0);
		}
	}
}