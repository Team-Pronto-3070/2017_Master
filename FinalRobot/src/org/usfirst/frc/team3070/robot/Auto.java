package org.usfirst.frc.team3070.robot;

public class Auto {
	private Drive drive;
	double gyroValue = 0.1;
	public Auto(Drive drive)
	{
		this.drive = drive;
	}
// Things done during Autonomous
	public void autoGeneric() {
		//generic autonomous code
		drive.drive(Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED);
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