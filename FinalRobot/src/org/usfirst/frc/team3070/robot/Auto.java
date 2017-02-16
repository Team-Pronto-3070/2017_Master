package org.usfirst.frc.team3070.robot;

public class Auto {
	private Drive drive = new Drive();
	double gyroValue = 0.1;
	double[] rotations = drive.getDistanceTraveled();
	boolean turn1;
	public Auto(Drive drive)
	{
		this.drive = drive;
	}
// Things done during Autonomous
	public void autoSkeleton() {
		//generic autonomous code
		//checks if the robot has not gone 5 feet
		if (rotations[2] < 5) {
			//if not, drive straight forward and first turn has not happened yet
			drive.driveRobotStraight();
			turn1 = false;
		}else if (rotations[2] > 5) {
			
		}
			/*
		
		//checks if the robot has gone 5 feet
		if (rotations[2] >= 5 && turn1 == false && drive.turn(45, Pronstants.AUTO_DRIVE_SPEED) == false) {
			//if it has, turn it (Placeholder) degrees and the first turn has now happened
			drive.turn(45, Pronstants.AUTO_DRIVE_SPEED);
			turn1 = true;
		}
		//checks if the first turn has happened
		if (turn1) {
			//if so, start vision here
			//TODO: finish vision and implement here
		}*/
	}
	
	public void autoL1() {
		//autonomous code for the left side to left gearloading station (driver POV)
	}
	
	public void autoL2() {
		//autonomous code for the left side to the center gearloading station (driver POV)
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
}