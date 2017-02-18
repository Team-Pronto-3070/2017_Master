package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	private Drive drive = new Drive();
	double gyroValue = 0.1;
	boolean turn1;
	double rotations[] = drive.getDistanceTraveled();
	int counter = 0;
	public Auto(Drive drive)
	{
		this.drive = drive;
	}
// Things done during Autonomous
	public void autoSkeleton() {
//		counter ++;
//		SmartDashboard.putString("DB/String 1", String.format("counter = %d", counter));
//		double dashData = SmartDashboard.getNumber("DB/Slider 0", 0.0);
//		SmartDashboard.putString("DB/String 2", String.format("dashData = %f", dashData));
//		return;
		//generic autonomous code
		//creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		//checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			//if not, drive straight forward and first turn has not happened yet
			drive.driveRobotStraight();
		}
		//checks if the robot has gone 5 feet
		else if (rotations[2] >= 5 && drive.turn(45, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if it has, turn it (Placeholder) degrees and the first turn has now happened
			drive.turn(45, Pronstants.AUTO_DRIVE_SPEED);
		}
		//checks if the first turn has happened
		else if (drive.turn(45, Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0, 0);
			//if so, start vision here
			//TODO: finish vision and implement here
		}
	}
	
	public void autoL1() {
		//autonomous code for the left side to left gearloading station (driver POV)
		drive.drive(Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED);
	}
	
	public void autoL2() {
		//autonomous code for the left side to the center gearloading station (driver POV)
		//generic autonomous code
		//creates a variable for the array "getDistanceTraveled" (see drive)
		double[] rotations = drive.getDistanceTraveled();
		//checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			//if not, drive straight forward and first turn has not happened yet
			drive.driveRobotStraight();
		}
		//checks if the robot has gone 5 feet
		else if (rotations[2] >= 5 && drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if it has, turn it (Placeholder) degrees and the first turn has now happened
			drive.turn(90, Pronstants.AUTO_DRIVE_SPEED);
		}
		//checks if the first turn has happened
		else if (drive.turn(90, Pronstants.AUTO_DRIVE_SPEED) == true && rotations[2] < 2) {
			drive.driveRobotStraight();
			//if so, start vision here
			//TODO: finish vision and implement here
		}
		else if (rotations[2] >= 2 && drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED) == false) {
			drive.turn(-90, Pronstants.AUTO_DRIVE_SPEED);
		}
		else if (drive.turn(-90,  Pronstants.AUTO_DRIVE_SPEED) == true) {
			drive.drive(0,0);
			//TODO: implement vision here
		}
	}
	
	public void autoC() {
		//autonomous code for the center to the center gearloading station
	}
	
	public void autoR1() {
		//autonomous code for the right side to the right gearloading station (driver POV)
	}
	
	public void autoR2() {
		//autonomous code for the right side to the center gearloading station (driver POV)
		
	}
	
	public void dummy1() {
		SmartDashboard.putString("DB/String 1", "Almost Got there, boys!");
	}
	
	public void dummy2() {
		SmartDashboard.putString("DB/String 2", "Diggity dun did it, boys!");
	}
}