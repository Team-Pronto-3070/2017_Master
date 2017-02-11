package org.usfirst.frc.team3070.robot;

public class Drive extends Robot implements Pronstants {
	public void joystickDrive() {
		double joyR = joystick.getRawAxis(5);
		double joyL = joystick.getRawAxis(1);
		if (joyR > DEAD_ZONE || joyR < -DEAD_ZONE) {
			talFR.set(joyR);
			talBR.set(-joyR);
		}
		else {
			talFR.set(0);
			talBR.set(0);
		}
		if (joyL > DEAD_ZONE || joyL < -DEAD_ZONE) {
			talFL.set(joyL);
			talBL.set(joyL);
		}
		else {
			talFL.set(0);
			talBL.set(0);
		}
	}
	public void driveForward() {
		talFR.set(AUTO_DRIVE_SPEED);
		talFL.set(AUTO_DRIVE_SPEED);
		talBR.set(-AUTO_DRIVE_SPEED);
		talBL.set(AUTO_DRIVE_SPEED);
	}
	public void driveBackward() {
		talFR.set(-AUTO_DRIVE_SPEED);
		talFL.set(-AUTO_DRIVE_SPEED);
		talBR.set(AUTO_DRIVE_SPEED);
		talBL.set(-AUTO_DRIVE_SPEED);
	}
	public void turnRight() {
		talFR.set(AUTO_DRIVE_SPEED);
		talBR.set(-AUTO_DRIVE_SPEED);
	}
	public void turnLeft() {
		talFL.set(AUTO_DRIVE_SPEED);
		talBL.set(AUTO_DRIVE_SPEED);
	}
	//edit
}