package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;

public class Drive {
	private static CANTalon talFR, talFL, talBR, talBL;
	double startHeading;
	private ProntoGyro gyro;
	public Drive()
	{
		
		gyro = new ProntoGyro();
		//defines the talon variables
		talFR = new CANTalon(Pronstants.TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(Pronstants.TALON_FRONT_LEFT_PORT);
		talBR = new CANTalon(Pronstants.TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(Pronstants.TALON_BACK_LEFT_PORT);
		//sets a voltage ramp rate on the talons
		talFR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talFL.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBL.setVoltageRampRate(Pronstants.RAMP_RATE);
		//sets a current limit on the talons
		talFR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talFL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		//sets feedback device to encoders
		talFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talFL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	}

	public void joystickDrive(double joyR, double joyL) {
		//makes the joystick control the talons
		//defines the variables for the speed of left and right sides of the robot
		double speedR, speedL;
		//checks if the right joystick is in the deadzone
		if (Math.abs(joyR) > Pronstants.DEAD_ZONE) {
			//If it isn't, set the speed of the right side to the joystick value
			speedR = joyR;
		}
		else {
			//If the joystick is in the deadzone, set the speed of the right side to 0
			speedR = 0;
		}
		//checks if the left joystick is in the deadzone
		if (Math.abs(joyL) > Pronstants.DEAD_ZONE) {
			//If it isn't, set the speed of the right side to the joystick value
			speedL = joyL;
		}
		else {
			//If the joystick is in the deadzone, set the speed of the right side to 0
			speedL = 0;
		}
		//drives the robot forward at the speeds set earlier for left and right
		drive(speedR, speedL);
	}
	public void drive(double right, double left)
	{
		//drives the robot based on two different input values for left and right
		talFR.set(-right);
		talBR.set(-right);
		talFL.set(left);
		talBL.set(left);
	}
	public double[] getDistanceTraveled()
	{
		//gets the distance traveled from the encoders
		//creates a string with 2 doubles
		double ar[] = new double[3];
		//creates a double for each encoder value converted into feet
		ar[0] = talFR.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		ar[1] = talFL.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		//creates a double for the average of the two encoder values (in feet)
		ar[2] = (ar[1] + ar[0]) / 2;
		//returns the encoder values
		return ar;
	}
	public void resetDistanceTraveled() {
		//resets the encoder values to 0
		talFR.setEncPosition(0);
		talFL.setEncPosition(0);
	}
	public boolean turn(double angle, double speed) {  
		//turns the robot until it aligns with an angle on the gyro
		//TODO test on robot and see if should use time instead
		//checks if the gyro angle is less than the desired angle
		if (gyro.calculateHeading() < angle) { 
			//If it is, turn left
			drive(speed, -speed);
		}
		//checks if the gyro angle is greater than the desired angle
		else if (gyro.calculateHeading() > angle) {
			//if it is, turn right
			drive(-speed, speed);
		}
		else {
			//if the gyro angle is aligned with the desired angle, tell the source that called the method that turning is not done
			return true;
		}
		//otherwise, tell the source that called the function that turning is done
		return false;
	}
	public void driveRobotStraight() {
		if (gyro.calculateHeading() > startHeading + 5) {
			drive(Pronstants.AUTO_DRIVE_SPEED + 0.1, Pronstants.AUTO_DRIVE_SPEED);
		}
		else if (gyro.calculateHeading() > (startHeading - 5)) {
			drive (Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED + 0.1);
		}
		else {
			drive(Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED);
		}
	}
	public void setStraightValue() {
		startHeading = gyro.calculateHeading();
	}
}