package org.usfirst.frc.team3070.robot;
import edu.wpi.first.wpilibj.Timer;

public class Auto extends Robot implements Pronstants {
	static Timer timer;
		
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
		timer = new Timer();
		timer.start();
	    timer.reset();
	}
	
	public void autoC() {
		//autonomous code for the center
		Sensors.calculateHeading(startHeading);
	    double i = timer.get();
	    double p = Sensors.calculateHeading(0);
	    int a = 0;
		//Goes to the left to unload
		if(i < 1){
			talFR.set(AUTO_DRIVE_SPEED/2);
			talBR.set(AUTO_DRIVE_SPEED/2);
			talFL.set(AUTO_DRIVE_SPEED/2);
			talBL.set(AUTO_DRIVE_SPEED/2);
			System.out.println(i);
		} else if(i > 1){
			if(a != 1){
				talFR.set(0);
				talFL.set(0);
				talBR.set(0);
				talBL.set(0);
			}
			a = 1;
			if(p < 90){
				talFR.set(AUTO_DRIVE_SPEED/2);
				talBR.set(AUTO_DRIVE_SPEED/2);
				talFL.set((-AUTO_DRIVE_SPEED)/2);
				talBL.set((-AUTO_DRIVE_SPEED)/2);
			}else if(p >= 90){
				talFR.set(0);
				talFL.set(0);
				talBR.set(0);
				talBL.set(0);
			}
				
			
			
		}
	}
	
	public void autoR() {
		//autonomous code for the right side (driver POV)
	}
}
