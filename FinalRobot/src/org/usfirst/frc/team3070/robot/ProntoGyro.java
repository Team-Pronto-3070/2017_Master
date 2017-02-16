package org.usfirst.frc.team3070.robot;
import org.usfirst.frc.team3070.robot.BNO055;

public class ProntoGyro {
	private static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	private static double angleOffset;
	
	public ProntoGyro()
	{
		reset();
	}
	
	public static double calculateHeading() {
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