package org.usfirst.frc.team3070.robot;

public class Auto {
	private Drive drive;
	public Auto(Drive drive)
	{
		this.drive = drive;
	}
// Things done during Autonomous
	public void stuff() {
		//skeleton Autonomous Code
		double startHeading = 0;
		Sensors.calculateHeading(startHeading);
	}
	
	public void autoGeneric() {
		
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