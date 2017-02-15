package org.usfirst.frc.team3070.robot;

public class Auto {
	private Drive drive;
	public Auto(Drive drive)
	{
		this.drive = drive;
	}
// Things done during Autonomous
	public void autoGeneric() {
		//generic autonomous code
		drive.drive(Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED);
	}
	
	public void autoL() {
		//autonomous code for the left side (driver POV)
	}
	
	public void autoC() {
		//autonomous code for the center
	}
	
	public void autoR() {
		//autonomous code for the right side (driver POV)
	}
}