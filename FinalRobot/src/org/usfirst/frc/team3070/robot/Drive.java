package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Drive {
	//defines the talons
	public CANTalon talFR, talFL, talBR, talBL;
//	double startHeading;
	//defines the gyro
	private ProntoGyro gyro;
	
	public static final double MAX_DEGREES_FULL_SPEED = 10.0;
	public static final double MIN_TURN_SPEED = 0.18;

	public Drive() {
		// drive constructor
		// initializes the gyro
		gyro = new ProntoGyro();
		// defines the talon variables
		talFR = new CANTalon(Pronstants.TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(Pronstants.TALON_FRONT_LEFT_PORT);
		talBR = new CANTalon(Pronstants.TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(Pronstants.TALON_BACK_LEFT_PORT);
		// sets a voltage ramp rate on the talons
		talFR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talFL.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBL.setVoltageRampRate(Pronstants.RAMP_RATE);
		// sets a current amperage limit on the talons
		talFR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talFL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		// sets feedback device on the talons to encoders
		talFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talBL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		resetDistanceTraveled();
	}
	
	public void resetGyro()
	{
		gyro.reset();
	}
	
	public void joystickDrive(double joyR, double joyL) {
		// makes the joystick control the talons
		// defines the variables for the speed of left and right sides of the robot
		double speedR, speedL;
		// checks if the right joystick is in the deadzone
		if (Math.abs(joyR) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick
			// value
			speedR = joyR;
		}
		else {
			// If the joystick is in the deadzone, set the speed of the right
			// side to 0
			speedR = 0;
		}
		
		// checks if the left joystick is in the deadzone
		if (Math.abs(joyL) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick
			// value
			speedL = joyL;
		}
		else {
			// If the joystick is in the deadzone, set the speed of the right
			// side to 0
			speedL = 0;
		}
		
		// drives the robot forward at the speeds set earlier for left and right
		// this speed is cubed so that the controls are less sensitives
		// (all speed values are between 0 and 1)
		drive(Math.pow(speedR, 3), Math.pow(speedL, 3));
	}

	public void drive(double right, double left) {
		// drives the robot based on two different input values for left and right
		talFR.set(-right);
		talBR.set(-right);
		talFL.set(left);
		talBL.set(left);
	}
	
	public void driveSwitch(double joyR, double joyL) {
		// makes the joystick control the talons (with front and back switched)
		// defines the variables for the speed of left and right sides of the robot
		double speedR, speedL;
		
		// checks if the right joystick is in the deadzone
		if (Math.abs(joyR) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick value
			speedR = joyR;
		}
		else {
			// If the joystick is in the deadzone, set the speed of the right
			// side to 0
			speedR = 0;
		}
		
		// checks if the left joystick is in the deadzone
		if (Math.abs(joyL) > Pronstants.DEAD_ZONE) {
			// If it isn't, set the speed of the right side to the joystick value
			speedL = joyL;
		}
		else {
			// If the joystick is in the deadzone, set the speed of the right
			// side to 0
			speedL = 0;
		}
		// drives the robot forward at the speeds set earlier for left and right,
		// except the front and back sides are reversed
		// (again, all values cubed for more driving control)
		drive(-Math.pow(speedR, 3), -Math.pow(speedL, 3));
	}

	public double[] getDistanceTraveled() {
		// gets the distance traveled from the encoders
		// creates an array with 3 doubles
		double ar[] = new double[3];
		
		// creates a double for each encoder value converted into feet
		ar[0] = talFR.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		ar[1] = -talBL.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		
		// creates a double for the average of the two encoder values (in feet)
		ar[2] = (ar[1] + ar[0]) / 2;
		
		//prints the left and right encoder values in the SmartDash
		SmartDashboard.putString("DB/String 0", String.format("RightDist = %f", ar[0]));
		SmartDashboard.putString("DB/String 1", String.format("LeftDist = %f", ar[1]));

		//returns the array
		return ar;
	}

	public void resetDistanceTraveled() {
		// resets the encoder values to 0
		talFR.setEncPosition(0);
		talBL.setEncPosition(0);
	}

	public boolean turn(double angle, double maxSpeed) {
		// turns the robot until it aligns with an angle on the gyro
		// gets the heading and makes it the value of a variable
		double currentHeading = gyro.getOffsetHeading();
		
		// Default to maximum speed
		double speed = maxSpeed;
		
		// adjust speed linearly when within 10 degrees of expected value
		double delta =  Math.abs(currentHeading - angle);
		if(delta <= MAX_DEGREES_FULL_SPEED )
		{
			// Simple linear regression
			//     m =         (Y2      -     Y1)           /             ( X2        -          X1 )
			double m = ( ( maxSpeed - MIN_TURN_SPEED )  / ( MAX_DEGREES_FULL_SPEED - Pronstants.TURN_OFFSET ) );
			// y = m*x + b => b = y - m*x. Choosing Y1 and X1: 
			double b = MIN_TURN_SPEED - ( m * Pronstants.TURN_OFFSET );
			
			// Substitute in the angle delta for x and 
			speed = m * delta + b;
			SmartDashboard.putString("DB/String 2", "Speed" +speed);

		}
		
		// checks if the gyro angle is less than the desired angle
		 if(currentHeading < angle - Pronstants.TURN_OFFSET) {
			// If it is, turn left
			drive(-speed, speed);
			SmartDashboard.putString("DB/String 1", "heqq");

		}
		
		// checks if the gyro angle is greater than the desired angle
		else if (currentHeading > angle + Pronstants.TURN_OFFSET) {
			// if it is, turn right
			drive(speed, -speed);
			SmartDashboard.putString("DB/String 1", "heck");

		}
		
		else {
			// if the gyro angle is aligned with the desired angle, tell the
			// source that called the method that turning is done
			drive(0,0);
			SmartDashboard.putString("DB/String 1", "fuck");

			return true;
		}
		
		// otherwise, tell the source that called the function that turning is not done
		return false;
	}

	public void driveRobotStraight(){
		driveRobotStraightSpeed(Pronstants.AUTO_DRIVE_SPEED);
	}
	
	public void driveRobotStraightSpeed( double speed ) {
		
		// Some pseudo code to get you all started:
		
		// Step 1: Reset gyro prior to first call to set the desired angle to drive forward at (this will set the ProntoGyro "offset" to this value.)
		// Step 2: If turning slightly left, decrease the speed on the right side OR increase the speed on the left side.
		// 		   If turning slightly right, decrease the speed on the left side OR increase the speed on the right side.
		// If running at 50 Hz, you can expect that whatever command you send will be active for about 20 ms. Determine how much you want to adjust the speed based on the angle offset.
		// I would recommend targeting a tolerance of less than 1 degree (smaller tolerance is better and the gyro can handle it).
		
//		double currentHeading = gyro.calculateHeading();
//		if (currentHeading > startHeading + 2) {
//			drive(Pronstants.AUTO_DRIVE_SPEED + 0.1, Pronstants.AUTO_DRIVE_SPEED);
//		} else if (currentHeading > (startHeading - 2)) {
//			drive(Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED + 0.1);
//		} else {
//			drive(Pronstants.AUTO_DRIVE_SPEED, Pronstants.AUTO_DRIVE_SPEED);
//		}
		// drives the robot forward at a speed adjusted by the "adjSpeed" function (see ProntoGyro)
		// This isn't going to work. Need to adjust only one side or both sides separately to cause the robot to turn slightly and adjust for not driving straight.
		//drive(Pronstants.AUTO_DRIVE_SPEED + gyro.adjSpeed(), Pronstants.AUTO_DRIVE_SPEED + gyro.adjSpeed());
		double heading = gyro.getOffsetHeading();
		double offset = gyro.getOffset();
		double adjSpeed = 0.0;
		
		if( heading > 0)
		{
			adjSpeed = gyro.getOffsetHeading() * Pronstants.ADJUSTING_CONSTANT;
		}
		else if( heading < 0)
		{
			adjSpeed = gyro.getOffsetHeading() * Pronstants.ADJUSTING_CONSTANT;
		}
		
		SmartDashboard.putString("DB/String 7", ""+ adjSpeed);
		System.out.println(adjSpeed + " " + gyro.getOffsetHeading());
		
		drive(speed + adjSpeed, speed - adjSpeed);
//		if (heading > 0 ) {
//			drive(speed + gyro.adjSpeed(), speed );
//		}
//		else if (heading <= 0 ) {
//			drive(speed, speed + gyro.adjSpeed());
//		}
//		else {
//			drive(speed, speed);
//		}
	}
}