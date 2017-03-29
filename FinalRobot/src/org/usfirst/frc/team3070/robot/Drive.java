package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;

/* methods:
public Drive(ProntoGyro gyro) - Constructs the class
public void resetGyro() - Resets the Gyro
public void joystickDrive(double joyR, double joyL, boolean trig) - Drives the robot according to joystick values
public double balanceSpeed(double joy) - Balances the speed of the robot using a quadratic equation
public void drive(double right, double left) - Drives the robot based on an input for the right and left sides
public double[] getDistanceTraveled() - Returns an array with information from the drive encoders in feet
public void resetDistanceTraveled() - Resets the distance traveled by the drive talons
public boolean turn(double angle, double maxSpeed) - Starts turning the robot until it reaches a given angle and returns a boolean saying whether it's done or not
public void driveRobotStraight(double speed) - Drives the robot straight at a given speed
public void toggleDriveTrain(boolean brake) - Toggles brake mode on the drive talons
public void setDriveRampRate(double rate) - Sets the voltage ramp rate on the drive talons
 */

public class Drive {
	// Defines the drive talons
	public CANTalon talFR, talFL, talBR, talBL;

	// Defines the gyro
	private ProntoGyro gyro;

	// Constructs the class
	public Drive(ProntoGyro gyro) {
		// Tells the class to use this gyro, rather than any other gyro
		this.gyro = gyro;
		// Initializes the drive talons
		talFR = new CANTalon(Pronstants.TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(Pronstants.TALON_FRONT_LEFT_PORT);
		talBR = new CANTalon(Pronstants.TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(Pronstants.TALON_BACK_LEFT_PORT);
		
		// Sets a current amperage limit on the drive talons
		talFR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talFL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		
		// Sets feedback device on the drive talons to encoders
		talFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talBL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		resetDistanceTraveled();
	}

	// Resets the gyro
	public void resetGyro() {
		gyro.reset();
	}

	// Drives the robot according to joystick values
	public void joystickDrive(double joyR, double joyL, boolean trig) {
		// Defines the variables for the speed of left and right sides of the robot
		double speedR, speedL;
		
		// Checks if the right joystick is in the deadzone
		if (Math.abs(joyR) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick value
			speedR = joyR;
		}
		
		else {
			// Otherwise, set the speed of the right side to zero
			speedR = 0;
		}

		// Checks if the left joystick is in the deadzone
		if (Math.abs(joyL) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the left side to the joystick value
			speedL = joyL;
		} else {
			// Otherwise, set the speed of the left side to zero
			speedL = 0;
		}

		// Finds the average of the left and right joysticks
		double avg = (speedL + speedR) / 2;
		
		// Checks if the trigger is pulled
		if (trig) {
			// If so and the joystick values are equal, drive straight
			// TODO: This should have an offset range
			if (Math.signum(speedL) == Math.signum(speedR)) {
				driveRobotStraight(-avg);
			}
			
			// Checks if the above case is not true and the trigger is pulled
			else {
				// If the left joystick is greater than the right, then turn left on a point
				if (speedL > speedR) {
					drive(balanceSpeed(speedR), -balanceSpeed(speedR));
					// TODO: Alex, why is the gyro reset here? Wouldn't this prevent the robot from driving straight
					// since it won't ever have to adjust?
					gyro.reset();
				}
				
				// Otherwise, turn right on a point
				else {
					drive(-balanceSpeed(speedL), balanceSpeed(speedL));
					// TODO: See above
					gyro.reset();
				}
			}
		}
		
		else {
			// Otherwise, drive using the parabolic equation in balanceSpeed
			drive(-balanceSpeed(speedR), -balanceSpeed(speedL));
			// This function resets the gyro until we hold the trigger
			// so that the drive straight has an initial heading
			gyro.reset();

		}
	}

	// Balances the speed of the robot using a quadratic equation
	public double balanceSpeed(double joy) {
		// Sets the a, b, and c values of the function
		double a = 0.928;
		double b = -0.185;
		double c = 0.159;

		// Checks if the joystick value is positive or negative
		double sign = joy / Math.abs(joy);

		// Returns the answer to the function for the given x value
		return sign * (a * (Math.pow(joy, 2)) + b * joy + c);
	}

	// Drives the robot based on an input for the right and left sides
	public void drive(double right, double left) {
		// Checks if the speed on the left is greater than 0.9
		if (left > 0.9) {
			// If so, set the speed to 0.9
			// This stops us from accidentally overheating the motors
			left = 0.9;
		}
		
		// Otherwise, check if the speed on the left is less than -0.9
		else if (left < -0.9) {
			// If so, set the speed to -0.9
			// This stops us from accidentally overheating the motors
			left = -0.9;
		}
		
		// Checks if the speed on the right is greater than 0.9
		if (right > 0.9) {
			// If so, set the speed to 0.9
			// This stops us from accidentally overheatng the motors
			right = 0.9;
		}
		
		// Otherwise, check if the speed on the left is less than -0.9
		else if (right < -0.9) {
			// If so, set the speed to -0.9
			// This stops us from accidentally overheating the motors
			right = -0.9;
		}
		
		// Sets the talons according to the correct speed values
		talFR.set(-right);
		talBR.set(-right);
		talFL.set(left);
		talBL.set(left);
	}

	// Returns an array with information from the drive encoders in feet
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

	// Resets the distance traveled by the drive talons
	public void resetDistanceTraveled() {
		talFR.setEncPosition(0);
		talBL.setEncPosition(0);
	}

	// Starts turning the robot until it reaches a given angle and returns a boolean saying whether it's done or not
	public boolean turn(double angle, double maxSpeed) {
		// Defaults the speed to the maximum speed
		double speed = maxSpeed;
		// Adjusts speed linearly when within 10 degrees of expected value
		double delta = Math.abs(gyro.getOffsetHeading() - angle);

		if (delta <= Pronstants.MAX_DEGREES_FULL_SPEED) {
			// Simple linear regression
			// m = (Y2 - Y1) / ( X2 - X1 )
			double m = ((maxSpeed - Pronstants.MIN_TURN_SPEED) / (Pronstants.MAX_DEGREES_FULL_SPEED - Pronstants.TURN_OFFSET));
			// y = m*x + b => b = y - m*x. Choosing Y1 and X1:
			double b = Pronstants.MIN_TURN_SPEED - (m * Pronstants.TURN_OFFSET);

			// Substitute in the angle delta for x and
			speed = m * delta + b;

		}

		// Checks if the gyro angle is less than the desired angle
		if (gyro.getOffsetHeading() < angle - Pronstants.TURN_OFFSET) {
			// If it is, turn left
			drive(-speed, speed);
			// and tell the source that turning is not done
			return false;

		}

		// Otherwise, checks if the gyro angle is greater than the desired angle
		else if (gyro.getOffsetHeading() > angle + Pronstants.TURN_OFFSET) {
			// If it is, turn right
			drive(speed, -speed);
			// and tell the source that turning is not done
			return false;
		}

		else {
			// If the gyro angle is aligned with the desired angle,
			// tell the source that the robot has turned the desired amount
			drive(0, 0);
			return true;
		}
	}

	// Drives the robot straight at a given speed
	public void driveRobotStraight(double speed) {
		// Sets the adjustment factor to the offset heading times the adjusting constant
		// This creates an effective proportional drive
		double adjSpeed = gyro.getOffsetHeading() * Pronstants.ADJUSTING_CONSTANT;

		// Drives the robot at the adjusted speed
		drive(speed + adjSpeed, speed - adjSpeed);
	}

	// Toggles brake mode on the drive talons
	public void toggleDriveTrain(boolean brake) {
		talFR.enableBrakeMode(brake);
		talFL.enableBrakeMode(brake);
		talBR.enableBrakeMode(brake);
		talBL.enableBrakeMode(brake);
	}
	
	
	// Sets the voltage ramp rate on the drive talons
	public void setDriveRampRate(double rate) {
		talFR.setVoltageRampRate(rate);
		talFL.setVoltageRampRate(rate);
		talBR.setVoltageRampRate(rate);
		talBL.setVoltageRampRate(rate);
	}
}