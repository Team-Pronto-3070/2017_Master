package org.usfirst.frc.team3070.robot;
import org.usfirst.frc.team3070.robot.BNO055;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ProntoGyro {
	// defines the variable imu from the class BNO055
	private static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	// defines the variable for the angle offset
	private static double angleOffset;
	
	public ProntoGyro()
	{
		// ProntoGyro constructor
		// runs the reset function (see below)
		reset();
	}
	
	public double calculateHeading() {	
		// calculates the heading of the gyro
		// puts the value of imu.getHeading on the SmartDash string 6 (see BNO055 class)
		SmartDashboard.putString("DB/String 6", " "+ imu.getHeading());
		// defines a variable for angle
		double angle;
		// checks if the current angle is negative
		if (imu.getHeading()-angleOffset < 0) {
			// if so, set angle to the remainder of the current angle
			// divided by 180 times -1 (a negative angle)
			angle = ((imu.getHeading() - angleOffset) % 180) * -1;
		}
		
		else {
			// if not, set the angle to the remainder of the current
			// angle divided by 180 (a positive angle)
			angle = (imu.getHeading()-angleOffset) % 180;
		}
		// print the angle offset and the current heading in the dashboard
		SmartDashboard.putString("DB/String 2", String.format("Offset = %f", angleOffset));
		SmartDashboard.putString("DB/String 3", String.format("Heading = %f", angle));
		// return the angle
		return angle;
	}
	
	public void reset() {
		// resets the angleOffset to the current heading
		angleOffset = calculateHeading();
	}
	
	public double adjSpeed() {
		// adjusts the speed of the talons for the driveRobotStraight function
		// defines a double for adjSpeed
		double adjSpeed = 0;
		// checks if the current Heading isn't within the value of
		// ANGLE_VARIANCE (see pronstants) of the angle offset
		if (Math.abs(calculateHeading() - angleOffset) > Pronstants.ANGLE_VARIANCE) {
			// if so, set adjSpeed to the current Heading times the value of
			// ADJUSTING_CONSTANT (see pronstants)
			 adjSpeed =  calculateHeading() * Pronstants.ADJUSTING_CONSTANT;
		}
		// returns the value of adjSpeed
		return adjSpeed;
	}
}
