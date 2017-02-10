package org.usfirst.frc.team3070.robot;
 s
public class Auto extends Robot implements Pronstants {
// Things done during Autonomous
	public void skeleton() {
		//skeleton Autonomous Code
		talFR.set(AUTO_DRIVE_SPEED);
		talFL.set(AUTO_DRIVE_SPEED);
		talBR.set(-AUTO_DRIVE_SPEED);
		talBL.set(AUTO_DRIVE_SPEED);
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
