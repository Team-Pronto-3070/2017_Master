package org.usfirst.frc.team3070.robot;

public class Climb extends Robot implements Pronstants {
	public void thingDoer() {
		if (joystick.getRawButton(1)) {
			talC1.set(AUTO_CLIMB_SPEED);
			talC2.set(AUTO_CLIMB_SPEED);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
		if (joystick.getRawButton(2)) {
			talC1.set(-AUTO_CLIMB_SPEED);
			talC2.set(-AUTO_CLIMB_SPEED);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
	}
}
