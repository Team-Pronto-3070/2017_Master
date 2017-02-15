package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;

public class Drive extends ProntoGyro {
	private static CANTalon talFR, talFL, talBR, talBL;
	public Drive()
	{
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
		double ar[] = new double[2];
		//creates a double for each encoder value
		ar[0] = talFR.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		ar[1] = talFL.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		//returns the encoder values
		return ar;
	}
	public void resetDistanceTraveled() {
		//resets the encoder values to 0
		talFR.setEncPosition(0);
		talFL.setEncPosition(0);
	}
	public void turnRight(double angle, double speed) {
		//turns the robot right until it aligns with an angle on the gyro
		//checks if the gyro is aligned with the desired angle
		if (ProntoGyro.calculateHeading() < angle) {  //consider implementing some compensation for the robot taking a bit to stop 
			//If it isn't, turn right
			drive(-speed, speed);
		}
		//If it is, stop turning
		drive(0,0);
	}
	public void turnLeft(double angle, double speed) {  
		//turns the robot right until it aligns with an angle on the gyro
		//TODO implement some compensation for the robot taking a bit to stop 
		//checks if the gyro is aligned with the desired angle
		if (Math.abs(ProntoGyro.calculateHeading()) < angle) { 
			//If it isn't, turn left
			drive(speed, -speed);
		}
		//If it is, stop turning
		drive(0,0);
	}
}