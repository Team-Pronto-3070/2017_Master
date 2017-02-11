package org.usfirst.frc.team3070.robot;
import edu.wpi.first.wpilibj.Timer;

public class Auto extends Robot implements Pronstants {
	static Timer timer;
		
// Things done during Autonomous
	public void skeleton() {
		//skeleton Autonomous Code
		Sensors.calculateHeading(startHeading);
		talFR.set(AUTO_DRIVE_SPEED);
		talFL.set(AUTO_DRIVE_SPEED);
		talBR.set(-AUTO_DRIVE_SPEED);
		talBL.set(AUTO_DRIVE_SPEED);
	}
	
	public void autoL() {
		//autonomous code for the left side (driver POV)
	}
	
	public static void autoC() {
		//autonomous code for the center
		timer = new Timer();
		timer.start();
	    timer.reset();
	    double i = timer.get();
	    double p = Sensors.calculateHeading(0);
		//Goes to the left to unload
		if(i < 8){
			talFR.set(AUTO_DRIVE_SPEED/2);
			talFL.set(AUTO_DRIVE_SPEED/2);
			talBR.set(AUTO_DRIVE_SPEED/2);
			talBL.set(AUTO_DRIVE_SPEED/2);
		}
		if(i > 8){
			talFR.set(0);
			talFL.set(0);
			talBR.set(0);
			talBL.set(0);
			do{
				talFR.set(AUTO_DRIVE_SPEED/2);
				talFL.set(-AUTO_DRIVE_SPEED/2);
				talBR.set(AUTO_DRIVE_SPEED/2);
				talBL.set(-AUTO_DRIVE_SPEED/2);
			}while(p < 90);
			
		}
	}
	
	public void autoR() {
		//autonomous code for the right side (driver POV)
	}
}
