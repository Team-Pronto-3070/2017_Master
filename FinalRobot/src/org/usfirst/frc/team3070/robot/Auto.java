package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	private Drive drive = new Drive();
	private Shooter shooter = new Shooter();
	double gyroValue = 0.1;
	boolean turn1;
	double rotations[] = drive.getDistanceTraveled();
	int counter = 0;

	public Auto(Drive drive) {
		this.drive = drive;
	}

	// Things done during Autonomous
	public void autoSkeleton() {
		// counter ++;
		// SmartDashboard.putString("DB/String 1", String.format("counter = %d",
		// counter));
		// double dashData = SmartDashboard.getNumber("DB/Slider 0", 0.0);
		// SmartDashboard.putString("DB/String 2", String.format("dashData =
		// %f", dashData));
		// return;
		// generic autonomous code
		// creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			// if not, drive straight forward and first turn has not happened
			// yet
			drive.driveRobotStraight();
		}
		// checks if the robot has gone 5 feet
		else if (rotations[2] >= 5 && drive.turn(45, Pronstants.AUTO_DRIVE_SPEED) == false) {
			// if it has, turn it (Placeholder) degrees and reset the distance traveled
			drive.turn(45, Pronstants.AUTO_DRIVE_SPEED);
		}
		// checks if the first turn has finished
		else if (drive.turn(45, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			// if so, start vision here
			// TODO: finish vision and implement here
		}
	}

	public void autoBlue() {
		// autonomous code for the left side to left gearloading station (driver
		// POV)
		// creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			// if not, drive straight forward
			drive.driveRobotStraight();
		}
		// checks if the robot has gone 5 feet and if the first turn has not finished yet
		else if (drive.turn(60, Pronstants.AUTO_DRIVE_SPEED) == false) {
			// if so, turn it (Placeholder) degrees and reset the distance traveled
			drive.turn(60, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		// checks if the first turn has finished
		else if (drive.turn(60, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			// TODO: finish vision and implement here
		}
	}

	public void autoL2() {
		// autonomous code for the left side to the center gearloading station
		// (driver POV)
		// generic autonomous code
		// creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			// if not, drive straight forward and first turn has not happened
			// yet
			drive.driveRobotStraight();
		}
		// checks if the robot has gone 5 feet and the first turn as not
		// finished yet
		else if (drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			// if it has, turn it 90 degrees to the right and reset the distance
			// traveled
			drive.turn(90, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		// checks if the first turn has finished and the robot has not traveled
		// a additional 2 feet yet
		else if (drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == true && rotations[2] < 2) {
			drive.driveRobotStraight();
		}
		// checks if the robot has driven 2 feet and the first turn has not finished yet
		else if (drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			// if so, turn the robot 90 degrees to the left and reset the distance traveled
			drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		else if (drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			// TODO: implement vision here
		}
	}

	public void autoC() {
		// autonomous code for the center to the center gearloading station
		// creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			// if not, drive straight forward
			drive.driveRobotStraight();
		}
		// checks if the robot has gone 5 feet
		else {
			// if it has, start vision here
			// TODO: implement vision here
			drive.drive(0, 0);
		}
	}

	public void autoRed() {
		// autonomous code for the red side of the field
		// creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		//creates a boolean for shooter
		boolean robotShoot;
		// checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			// if so, drive straight forward
			drive.driveRobotStraight();
			robotShoot = false;
		}
		// checks if the robot has gone 5 feet and the first turn is not finished
		else if (drive.turn(60, Pronstants.AUTO_DRIVE_SPEED) == false) {
			// if so, turn it (Placeholder) degrees and reset the distance traveled
			drive.turn(60, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
			robotShoot = false;
		}
		// checks if the first turn has finished
		else if (drive.turn(60, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			robotShoot = true;
			// TODO: finish vision and implement here
		}
		if (robotShoot = true) {
			shooter.autoShoot();
		}
	}

	public void autoR2() {
		// autonomous code for the right side to the center gearloading station (driver POV)
		//creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		//checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			//if so, drive straight forward
			drive.driveRobotStraight();
		}
		//checks if the robot has gone 5 feet and the first is not finished
		else if (drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if so, turn it 90 degrees to the right and reset the distance traveled
			drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		//checks if the first turn has finished and the robot has not traveled an additional 2 feet
		else if (drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED) == true && rotations[2] < 2) {
			//if so, drive the robot straight
			drive.driveRobotStraight();
		}
		//checks if the robot has traveled an additional 2 feet and the second has not finished
		else if (drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if so, turn the robot 90 degrees to the left and reset the distance traveled
			drive.turn(90, Pronstants.AUTO_DRIVE_SPEED);
			drive.resetDistanceTraveled();
		}
		//checks if the second turn has finished
		else if (drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			//TODO: implement vision here
		}
	}

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