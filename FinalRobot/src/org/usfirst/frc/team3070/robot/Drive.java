package org.usfirst.frc.team3070.robot;

public class Drive extends Robot implements Pronstants {
	public void joystickDrive() {
		if (joystick.getRawAxis(5) > DEAD_ZONE || joystick.getRawAxis(5) < -DEAD_ZONE) {
			talFR.set(joystick.getRawAxis(5));
			talBR.set(-joystick.getRawAxis(5));
		}
		else {
			talFR.set(0);
			talBR.set(0);
		}
		if (joystick.getRawAxis(1) > DEAD_ZONE || joystick.getRawAxis(1) < -DEAD_ZONE) {
			talFL.set(joystick.getRawAxis(1));
			talBL.set(joystick.getRawAxis(1));
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