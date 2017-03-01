package org.usfirst.frc.team3070.robot;
import org.usfirst.frc.team3070.robot.BNO055;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ProntoGyro {
	// defines the variable imu from the class BNO055
	public static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	
	// defines the variable for the angle offset.
	// Leave as a class variable so that each instance can have it's own angleOffset
	private double angleOffset;
	
	public ProntoGyro()
	{
		// ProntoGyro constructor
		// runs the reset function (see below)
		reset();
	}
	
	public double getOffsetHeading() {	
		// calculates the heading of the gyro
		// puts the value of imu.getHeading on the SmartDash string 6 (see BNO055 class)
		SmartDashboard.putString("DB/String 6", " "+ imu.getVector()[0]);
		
		// defines a variable for angle
		double angle;

		// set the angle to the remainder of the current
		// angle divided by 180
		angle = normalizeHeadingVal(getRawHeading() - angleOffset);
		
		// print the angle offset and the current heading in the dashboard
		SmartDashboard.putString("DB/String 2", String.format("Offset = %f", angleOffset));
		SmartDashboard.putString("DB/String 3", String.format("Heading = %f", angle));
		
		// return the angle
		return angle;
	}
	
	public void reset() {
		// resets the angleOffset to the current heading
		angleOffset = getRawHeading();
	}
	
	public double getRawHeading() {
		return normalizeHeadingVal(imu.getVector()[0]);
	}
	
	public double getOffset() {
		return angleOffset;
	}
	
	private double normalizeHeadingVal( double heading )
	{
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
