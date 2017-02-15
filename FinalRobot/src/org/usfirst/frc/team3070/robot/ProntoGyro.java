package org.usfirst.frc.team3070.robot;
import org.usfirst.frc.team3070.robot.BNO055;

public class ProntoGyro {
	private static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	private double angleOffset;
	
	public ProntoGyro()
	{
		reset();
	}
	
	public double calculateHeading(double sh) {
		double angle = (imu.getHeading()-angleOffset) % 360;
		if (angle > 180) {
			angle = angle-360;
		}
		return angle;
	}
	
	public void reset()
	{
		angleOffset = imu.getHeading();
	}
}
