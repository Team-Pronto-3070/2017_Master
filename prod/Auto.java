package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3070.robot.*;

public class Auto extends Robot implements Pronstants {

	// All autonomous directions are based off of the driver's POV
	
	public void moveFoward() { // making voids for everything so it's easier to read #thuglife
		talBR.set(AUTO_SPEED); 
		talFR.set(-AUTO_SPEED);
		talFL.set(-AUTO_SPEED);  // TODO: We need to figure out which Talons are reversed
		talBL.set(AUTO_SPEED); 
	}
	public void moveBackward() {
		talBR.set(-AUTO_SPEED); 
		talFR.set(AUTO_SPEED);
		talFL.set(AUTO_SPEED); 
		talBL.set(-AUTO_SPEED); 
	}
	public void turnNinetyDegrees() {
		talBR.set(AUTO_SPEED); 
		talFR.set(-AUTO_SPEED);
		talFL.set(0); 
		talBL.set(0); 

	}
	
	public void turnNinetyDegreesLeft() {
		talBR.set(0); 
		talFR.set(0);
		talFL.set(-AUTO_SPEED); // We still need to figure out which talons are backward 
		talBL.set(AUTO_SPEED); 

	}
	
	/* Autonomous for left side (same side as ball retrieval). The robot will move foward for 4 seconds at a predetermined
	 * speed, once it gets close to the lift, it will turn 90 degrees, move foward to place gear on lift. Total:  11 seconds
	 */
	public void AutoL(){
		if(timer.get() < 5){ //5 seconds
			moveFoward();
			if(timer.get() > 5 && timer.get() > 8 ) { // between 5 and 9 (3 secs).
			turnNinetyDegrees(); 
			moveFoward();
			if(timer.get() > 9 && timer.get() < 12) { // between 9 and 11 (3 secs)
				moveBackward();
			}
				
			}
			
		}
	}
	/* Autonomous for right side (side opposite as ball retrieval). Essentially the same thing as the code above but flipped
	 * for the right side. Total: 11.5 seconds.
	 */
	public void AutoR(){
		if(timer.get() < 5){ //5 seconds
			moveFoward(); // waits for .5 seconds because Michael was insistent
			if(timer.get() > 5.5 && timer.get() > 8 ) { // between 5 and 9 (3 secs).
			turnNinetyDegreesLeft(); 
			moveFoward();
			if(timer.get() > 9 && timer.get() < 12) { // between 9 and 11 (3 secs)
				moveBackward();
			}
				
			}
		}
		}
	/*Autonomous for center. This assumes the robot will be placed directly in line with the lift each time
	 * we may have to add some slight adjustments later if this is not the case.  The robot will simply
	 * go forward, reach the lift, then move back. Total: 6.5 seconds.
	 * 
	 */
	
			public void AutoC(){ 
				if (Timer.get() < 4 ){ // 4 seconds
			        moveFoward();
					if(timer.get() > 4.5 && timer.get() < 7) { // 2.5 seconds, the gear will be placed here
						talBR.set(-AUTO_SPEED);
						talFR.set(AUTO_SPEED); 
						talFL.set(AUTO_SPEED); 
						talBL.set(-AUTO_SPEED);
						
					}
				}

			}
}
