package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;

/*
methods:
public Drive() - constructs the class
public void toggleDriveMode(boolean brake) - toggles the drive talons between brake and coast mode
public void startPID() - starts the PID controller
public void resetGyro() - resets the gyro
public void setDriveRampRate(double rate) - sets the voltage ramp rate on the drive talons
public void joystickDrive() - drives the robot according to joystick values
public void drive() - sets the talons to speeds which will drive the robot
public double[] getDistanceTraveled() - gets an array for the distance traveled by each side in feet and the average of those two distances
public double[] getEncoderVelocity() - gets an array for the velocity of each side of the robot
public void resetDistanceTraveled() - resets the encoder values to 0
public double getGyroOffset() - gets the angle offset from the ProntoGyro class (see ProntoGyro)
public boolean turn() - turns the robot autonomously
public void encDrive() - drives the robot autonomously based off of encoder velocities and PID values
public void pidDrive() - drives the robot based off of PID values
public double[] getDriveValues() - returns an array with the drive values for the smartDash
 */

public class Drive {
	// Defines the drive talons
	public CANTalon talFR, talFL, talBR, talBL;
	
	// Defines the gyro
	private ProntoGyro gyro;
	
	// Defines the PID controller
	HeadingController pid;
	
	// Constructs the class
	public Drive() {
		// Defines the drive talons
		talFR = new CANTalon(Pronstants.TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(Pronstants.TALON_FRONT_LEFT_PORT);
		talBR = new CANTalon(Pronstants.TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(Pronstants.TALON_BACK_LEFT_PORT);
		
		// Sets a voltage ramp rate on the talons
		talFR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talFL.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBL.setVoltageRampRate(Pronstants.RAMP_RATE);
		
		// Sets a current amperage limit on the talons
		talFR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talFL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		
		// Sets the feedback device on the talons to encoders
		talFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talBL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		// Initializes the gyro
		gyro = new ProntoGyro();
		
		// Initializes the PID controller
		pid = new HeadingController(gyro);
		
		// Resets the distance traveled on the encoders
		resetDistanceTraveled();
	}
	
	// Toggles brake mode on the drive
	public void toggleDriveMode(boolean brake) {
		talFR.enableBrakeMode(brake);
		talFL.enableBrakeMode(brake);
		talBR.enableBrakeMode(brake);
		talBL.enableBrakeMode(brake);
	}
	
	// Starts the PID controller
	public void startPID() {
		pid.start();
	}
	
	// Resets the gyro
	public void resetGyro() {
		gyro.reset();
	}
	
	// Sets the Ramp Rate on the drive talons to a given rate
	public void setDriveRampRate(double rate) {
		talFR.setVoltageRampRate(rate);
		talFL.setVoltageRampRate(rate);
		talBR.setVoltageRampRate(rate);
		talBL.setVoltageRampRate(rate);
	}
	
	// Makes the joystick control the talons
	public void joystickDrive(double joyR, double joyL, double z) {
		// Defines the variables for the speed of left and right sides of the robot
		double speedR, speedL;
		
		// Checks if the right joystick is in the deadzone
		if (Math.abs(joyR) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick value
			speedR = joyR;
		}
		
		else {
			// If the joystick is in the deadzone, set the speed of the right side to 0
			speedR = 0;
		}
		
		// Checks if the left joystick is in the deadzone
		if (Math.abs(joyL) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick value
			speedL = joyL;
		}
		
		else {
			// If the joystick is in the deadzone, set the speed of the right side to 0
			speedL = 0;
		}
		
		// z is the z axis input on the right joyStick, it is used to switch the direction the robot drives
		// Checks if the z axis is less than 0.5
			if (z > .5) {
				// If so, drive the robot with the sides swapped according to the joystick values
				pidDrive(-Math.copySign(Math.sqrt(Math.abs(speedR)), speedR), -Math.copySign(Math.sqrt(Math.abs(speedL)), speedL));
			  } 
			else {
				// Otherwise, drive the robot with the sides in their regular orientation
			  	pidDrive(Math.pow(speedL, 3), Math.pow(speedR, 3));
			}
		// Drives the robot forward at the speeds set earlier for left and right
		// This speed is cubed so that the controls are less sensitive
		// (all speed values are between 0 and 1)
	}

	// Drives the robot based on two different input values for left and right
	public void drive(double right, double left) {
		talFR.set(-right);
		talBR.set(-right);
		talFL.set(left);
		talBL.set(left);
	}

	// Gets the distance traveled from the encoders
	public double[] getDistanceTraveled() {
		// Creates an array with 3 doubles
		double ar[] = new double[3];
		
		// Creates a double for each encoder value converted into feet
		ar[0] = talFR.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		ar[1] = -talBL.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		
		// Creates a double for the average of the two encoder values (in feet)
		ar[2] = (ar[1] + ar[0]) / 2;

		return ar;
	}
	
	// Gets an array with the encoder velocities (0 is right, 1 is left)
	public double[] getEncoderVelocity() {
		// Creates an array with 2 doubles
		double ar[] = new double[2];
		
		// Creates a variable for the right and left speeds of the sides of the robot
		ar[0] = talFR.getEncVelocity();
		ar[1] = -talBL.getEncVelocity();
		
		return ar;
	}

	// Resets the encoder values to 0
	public void resetDistanceTraveled() {
		talFR.setEncPosition(0);
		talBL.setEncPosition(0);
	}

	// Returns the angle offset from the gyro
	public double getGyroOffset() {
		return gyro.getOffset();
	}
	
	// Turns the robot until it aligns with the gyro and returns a boolean of whether it's done turning or not
	public boolean turn(double angle, double maxSpeed) {
		// Gets the heading in relation to the offset from the gyro and makes it the value of a variable
		double currentHeading = gyro.getOffsetHeading();
		
		// Default to maximum speed
		double speed = maxSpeed;
		
		// Adjust speed linearly when within 10 degrees of expected value
		double delta =  Math.abs(currentHeading - angle);
		if(delta <= Pronstants.MAX_DEGREES_FULL_SPEED )
		{
			// Simple linear regression
			//     m =         (Y2      -     Y1)           /             ( X2        -          X1 )
			double m = ( ( maxSpeed - Pronstants.MIN_TURN_SPEED )  / ( Pronstants.MAX_DEGREES_FULL_SPEED - Pronstants.TURN_OFFSET ) );
			// y = m*x + b => b = y - m*x. Choosing Y1 and X1: 
			double b = Pronstants.MIN_TURN_SPEED - ( m * Pronstants.TURN_OFFSET );
			
			// Substitute in the angle delta for x and 
			speed = m * delta + b;
		}
		
		// Checks if the gyro angle is less than the desired angle
		 if(currentHeading < angle - Pronstants.TURN_OFFSET) {
			// If it is, turn left
			drive(-speed, speed);
		}
		
		// Checks if the gyro angle is greater than the desired angle
		else if (currentHeading > angle + Pronstants.TURN_OFFSET) {
			// If it is, turn right
			drive(speed, -speed);
		}
		
		else {
			// If the gyro angle is aligned with the desired angle, tell the source that called the method that turning is done
			drive(0,0);
			return true;
		}
		
		// Otherwise, tell the source that called the function that turning is not done
		return false;
	}
	
	// Drives the robot at a speed based off the encoders and the PID controller
	public void encDrive(double speed) {
		// Creates an array equal to the encoder velocities
		// NOTE: 0 is right and 1 is left
		double[] velocity = getEncoderVelocity();
		
		// Creates a variable equal to the velocity of the right side minus the velocity of the left side
		double veldiff = velocity[0] - velocity[1];
		
		// Checks if the difference in velocities is greater than the speed variance
		if (veldiff > Pronstants.SPEED_VARIANCE) {
			// If so, drive with an increased amount on the left side to compensate
			drive(speed, speed + pid.getAdjFactor());
		}
		
		// Checks if the difference in velocities is less than the negative speed variance
		else if (veldiff < -Pronstants.SPEED_VARIANCE) {
			// If so, drive with an increased amount on the right side to compensate
			drive(speed, speed + pid.getAdjFactor());
		}
		
		else {
			// Otherwise, drive the robot with no compensation factor
			drive(speed, speed);
		}
	}
	
	// Drives the robot at two speed which are adjusted according to PID values to compensate
	public void pidDrive(double right, double left) {
		drive(right - pid.getAdjFactor(), left + pid.getAdjFactor());
	}
	
	public double[] getDriveValues() {
		double[] ar = new double[7];
		ar[0] = getDistanceTraveled()[0];
		ar[1] = getDistanceTraveled()[1];
		ar[2] = talFR.getOutputCurrent();
		ar[3] = talFL.getOutputCurrent();
		ar[4] = talBR.getOutputCurrent();
		ar[5] = talBL.getOutputCurrent();
		ar[6] = gyro.getOffsetHeading();
		return ar;
	}
}