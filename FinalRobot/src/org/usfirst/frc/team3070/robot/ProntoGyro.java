package org.usfirst.frc.team3070.robot;
import org.usfirst.frc.team3070.robot.BNO055;

public class ProntoGyro {
	private static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	private static double angleOffset;
	
	public ProntoGyro()
	{
		reset();
	}
	
	public double calculateHeading() {	
		double angle;
		if (imu.getHeading()-angleOffset < 0) {
			angle = ((imu.getHeading() - angleOffset) % 180) * -1;
		}
		else {
		angle = (imu.getHeading()-angleOffset) % 180;
		}
		return angle;
	}
	
	public void reset()
	{
		angleOffset = imu.getHeading();
	}
	
	public double adjSpeed() {
		double adjVolt = 0;
		if (Math.abs(calculateHeading() - angleOffset) > Pronstants.ANGLE_VARIANCE) {
			 adjVolt =  calculateHeading() * Pronstants.ADJUSTING_CONSTANT;
		}
		return adjVolt;
	}
}
