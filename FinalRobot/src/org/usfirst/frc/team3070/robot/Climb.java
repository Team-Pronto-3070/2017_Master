package org.usfirst.frc.team3070.robot;

public class Climb extends Robot implements Pronstants {
	public void checkClimbInput(boolean button1, boolean button2) {
		if (button1) {
			talC1.set(AUTO_CLIMB_SPEED);
			talC2.set(AUTO_CLIMB_SPEED);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
		//TODO should this button1 and button2?
		if (button2) {
			talC1.set(-AUTO_CLIMB_SPEED);
			talC2.set(-AUTO_CLIMB_SPEED);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
	}
}
