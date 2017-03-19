package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;

/* Methods:
public Drive(ProntoGyro gyro) - Constructs the class
public void resetGyro() - reset the gyro
public void joystickDrive(double joyR, double joyL) - Drives the robot according to joystick values
public double balanceSpeed(double joy) - Adjust the joystickDrive speeds to a parabolic drive speed
public void drive(double right, double left) - Adjust the joystickDrive speeds to a parabolic drive speed
public void drive(double right, double left) - Sets the talons according to a right and left speed
public double[] getDistanceTraveled() - Returns an array with the values from the encoders
public void resetDistanceTraveled() - Resets the encoder values to 0
public boolean turn(double angle, double maxSpeed) - Turns the robot until the heading reaches an amount of degrees
and returns a boolean for if the robot is done turning
public void driveRobotStraight(double speed) - Drives the robot straight a set speed using the gyro
public void toggleDriveTrain(boolean brake) - Toggles brake mode on the drive talons
public void setDriveRampRate(double rate) - Sets the Voltage Ramp Rate on the drive talons

*/

public class Drive {
	// Defines the talons
	public CANTalon talFR, talFL, talBR, talBL;
	
	// Defines the gyro
	private ProntoGyro gyro;

	// Constructs the class
	public Drive(ProntoGyro gyro) {
		// Tells the class to use this gyro, and not any other gyro
		this.gyro = gyro;
		// Initializes the talons
		talFR = new CANTalon(Pronstants.TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(Pronstants.TALON_FRONT_LEFT_PORT);
		talBR = new CANTalon(Pronstants.TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(Pronstants.TALON_BACK_LEFT_PORT);
		// Sets an amperage limit on the talons
		talFR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talFL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		// Sets feedback device on the talons to encoders
		talFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talBL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		// Resets the distance traveled
		resetDistanceTraveled();
	}
	
	// Resets the gyro
	public void resetGyro() {
		gyro.reset();
	}
	
	// Drives the robot according to joystick values
	public void joystickDrive(double joyR, double joyL) {
		// Defines the variables for the speed of left and right sides of the robot
		double speedR, speedL;
		
		// Checks if the right joystick is in the deadzone
		if (Math.abs(joyR) > Pronstants.DEAD_ZONE) {
			// If it isn't, set speedR to the right joystick value
			speedR = joyR;
		}
		
		else {
			// Otherwise , set speedR to 0
			speedR = 0;
		}
		
		// Checks if the left joystick is in the deadzone
		if (Math.abs(joyL) > Pronstants.DEAD_ZONE) {
			// If it isn't, set speedL to the left joystick value
			speedL = joyL;
		}
		
		else {
			// Otherwise, set speedL to 0
			speedL = 0;
		}
		
		// Drive the robot using a parabolic drive according to speedR and speedL
		drive(-(balanceSpeed(speedR)), -(balanceSpeed(speedL)));
	}
	
	// Adjust the joystickDrive speeds to a parabolic drive speed
	public double balanceSpeed(double joy) {
		// Sets values for a, b, and c
		double a = 0.926;
		double b = -0.185;
		double c = 0.159;
		
		// Sets a variable to +1 or -1 depending on the sign of the speed
		double sign = joy/Math.abs(joy);
		
		// Returns a quadratic equation which is equal to
		// a value which will drive the robot
		return sign*(a*(Math.pow(joy, 2)) + b*joy + c);
	}

	// Sets the talons according to a right and left speed
	public void drive(double right, double left) {
		// Checks if the left value is greater than 0.9
		if (left > 0.9) {
			// If so, set the left value to 0.9
			// This stops us from burning out the motors
			left = 0.9;
		}
		
		// Checks if the left value is less than 0.9
		else if (left < -0.9) {
			// If so, set the left value to -0.9
			// This stops us from burning out the motors
			left = -0.9;
		}
		
		if (right > 0.9) {
			// If so, set the left value to 0.9
			// This stops us from burning out the motors
			right = 0.9;
		}
		
		// Checks if the left value is less than 0.9
		else if (right < -0.9) {
			// If so, set the left value to -0.9
			// This stops us from burning out the motors
			right = -0.9;
		}
		
		// Set the talons to the (possibly adjusted) input values
		// NOTE: Right motors configured so negative is forward
		talFR.set(-right);
		talBR.set(-right);
		talFL.set(left);
		talBL.set(left);
	}

	// Returns an array with the values from the encoders
	public double[] getDistanceTraveled() {
		// Creates an array with 3 doubles
		double ar[] = new double[3];
		
		// Creates a double in the array for each encoder value converted into feet
		ar[0] = talFR.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		ar[1] = -talBL.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		
		// Creates a double in the array for the average of the two encoder values in feet
		ar[2] = (ar[1] + ar[0]) / 2;

		return ar;
	}

	// Resets the encoder values to 0
	public void resetDistanceTraveled() {
		talFR.setEncPosition(0);
		talBL.setEncPosition(0);
	}

	// Turns the robot until the heading reaches an amount of degrees and
	// returns a boolean for if the robot is done turning
	public boolean turn(double angle, double maxSpeed) {
		// turns the robot until it aligns with an angle on the gyro
		// gets the heading and makes it the value of a variable
		double currentHeading = gyro.getOffsetHeading();
		// Default to maximum speed
		double speed = maxSpeed;
		
		// Adjust speed linearly when within 10 degrees of expected value
		double delta =  Math.abs(currentHeading - angle);

		// Checks if delta is less than or equal to the maximum degrees for full speed
		if(delta <= Pronstants.MAX_DEGREES_FULL_SPEED )
		{
			// Simple linear regression
			//     m = (Y2 - Y1) / ( X2 - X1 )
			double m = ( ( maxSpeed - Pronstants.MIN_TURN_SPEED )  / ( Pronstants.MAX_DEGREES_FULL_SPEED - Pronstants.TURN_OFFSET ) );
			// y = m*x + b => b = y - m*x. Choosing Y1 and X1: 
			double b = Pronstants.MIN_TURN_SPEED - ( m * Pronstants.TURN_OFFSET );
			
			// Substitute in the angle delta for x
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
			// If the gyro angle is aligned with the desired angle, tell the
			// source that called the method that turning is done
			return true;
		}
		
		// Otherwise, tell the source that called the function that turning is not done
		return false;
	}
	
	// Drives the robot straight a set speed using the gyro
	public void driveRobotStraight(double speed) {
		// Creates a variable for adjSpeed
		double adjSpeed = 0.0;
		
		// Sets adjSpeed to the heading in relation to the offset times the adjusting constance
		adjSpeed = gyro.getOffsetHeading() * Pronstants.ADJUSTING_CONSTANT;
		
		// Drives the robot according to the adjSpeed constant
		drive(speed + adjSpeed, speed - adjSpeed);
	}
	
	// Toggles brake mode on the drive talons
	public void toggleDriveTrain(boolean brake) {
		talFR.enableBrakeMode(brake);
		talFL.enableBrakeMode(brake);
		talBR.enableBrakeMode(brake);
		talBL.enableBrakeMode(brake);
	}
	
	// Sets the Voltage Ramp Rate on the drive talons
	public void setDriveRampRate(double rate) {
		talFR.setVoltageRampRate(rate);
		talFL.setVoltageRampRate(rate);
		talBR.setVoltageRampRate(rate);
		talBL.setVoltageRampRate(rate);
	}
}