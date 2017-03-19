package org.usfirst.frc.team3070.robot;

import org.usfirst.frc.team3070.robot.BNO055;

/*
methods:
public ProntoGyro() - Constructs the class
public double getOffsetHeading() - Gets the heading in relation to the offset
public void reset() - resets the angle offset
public double getRawHeading() - Gets the raw heading
public double getOffset() - Gets the offset
public double normalizeHeadingVal(double heading) - Normalizes the heading value for program and readability purposes
 */

public class ProntoGyro {
	// defines the variable imu from the class BNO055
	public static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	
	// defines the variable for the angle offset.
	// Leave as a class variable so that each instance can have it's own angleOffset
	private double angleOffset;
	
	// Constructs the class
	public ProntoGyro() {
		// Resets the gyro
		reset();
	}
	
	// Gets the heading in relation to the offset
	public double getOffsetHeading() {	
		// Defines a double for angle
		double angle;

		// Normalizes the heading of the Raw Value minus the angle offset
		angle = normalizeHeadingVal(getRawHeading() - angleOffset);
				
		// Return the angle
		return angle;
	}
	
	// Resets the angle offset
	public void reset() {
		// Resets the angleOffset to the current heading
		angleOffset = getRawHeading();
		// Checks if the angle offset is 360 degrees
		if (angleOffset == 360.0) {
			// If so, set the angle offset to zero
			// This accounts for a problem we saw on the field
			angleOffset = 0;
		}
	}
	
	// Gets the raw heading of the gyro
	public double getRawHeading() {
		return normalizeHeadingVal(imu.getVector()[0]);
	
	}
	
	// Gets the angle offset
	public double getOffset() {
		return angleOffset;
	}
	
	// Normalizes the heading value for the rest of the code/reads easier
	private double normalizeHeadingVal(double heading) {
		// Normalize a heading value to the range of (-180, 180]
		if( heading % 360 >  180.0 )
		{
			heading = ( heading % 360.0 ) - 360.0;
		} else if ( ( heading % 360 ) <= -180.0 )
		{
			heading = (heading % 360.0) + 360.0;
		}
		return heading;
	}

}