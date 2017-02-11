package org.usfirst.frc.team3070.robot;
import org.usfirst.frc.team3070.robot.BNO055;

public class Sensors {

	public static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	public static double calculateHeading(double sh) {
		return imu.getHeading()-sh;
	}

}
