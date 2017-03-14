package org.usfirst.frc.team3070.robot;

import org.usfirst.frc.team3070.robot.BNO055;

/*
methods:
public ProntoGyro() - constructs the class
public double getOffsetHeading() - returns the heading in relation to the offset
public void reset() - resets the angle offset to the current heading
public double getRawHeading() - returns the heading without factoring in the angle offset
public double getOffset() - returns the angle offset
public double normalizeHeadingVal(double heading) - returns a heading in the range of 180 to -180
 */

public class ProntoGyro {
	// defines the variable imu from the class BNO055
	public static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	
	// Defines the variable for the angle offset
	// Leave as a class variable so that each instance can have it's own angleOffset
	private double angleOffset;
	
	// Constructs the class
	public ProntoGyro() {
		// Resets the angle offset
		reset();
	}
	
	// Calculates the heading of the gyro in relation to the angle offset
	public double getOffsetHeading() {	
		// defines a variable for angle
		double angle;

		// set the angle to the remainder of the current angle divided by 180
		angle = normalizeHeadingVal(getRawHeading() - angleOffset);
		
		return angle;
	}
	
	// Resets the angleOffset to the current heading
	public void reset() {
		angleOffset = getRawHeading();
	}
	
	// Gets the Raw Heading
	public double getRawHeading() {
		return normalizeHeadingVal(imu.getVector()[0]);
	}
	
	// Gets the angle offset
	public double getOffset() {
		return angleOffset;
	}
	
	// Normalizes a heading value to the range of (-180, 180)
	private double normalizeHeadingVal( double heading ) {
		// Checks if the remainder of the heading and 360 is greater than 180
		if ( heading % 360 >  180.0 ) {
			// If so, make the value negative
			heading = ( heading % 360.0 ) - 360.0;
		}
		
		// Checks if the remainder of the heading and 360 is less than or equal to -180
		else if (( heading % 360 ) <= -180.0) {
			// If so, make the value positive
			heading = (heading % 360.0) + 360.0;
		}
		
		return heading;
	}

}
