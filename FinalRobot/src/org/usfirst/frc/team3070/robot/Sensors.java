package org.usfirst.frc.team3070.robot;

import org.usfirst.frc.team3070.robot.BNO055;

public class Sensors {
	public static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	public static double calculateHeading(double sh) {
		return imu.getHeading()-sh;
	}
	public static double[] findCamOut(String camIn) {
		camIn = camIn.substring(14);
		String[] camSplit = camIn.split(",");
		String[] strOut = {camSplit[0],camSplit[1].substring(1, camSplit[1].length()-1),camSplit[2].substring(6)};
		double[] out = {Double.parseDouble(strOut[0]), Double.parseDouble(strOut[1]), Double.parseDouble(strOut[2])};
		return out;
	}
	public static double adjVoltage(double inpVolt, double sh) {
		double adjVolt = 0;
		if (Math.abs(Sensors.calculateHeading(sh) - sh) > Pronstants.ANGLE_VARIANCE) {
			 adjVolt =  Sensors.calculateHeading(sh) * Pronstants.ADJUSTING_CONSTANT;
		}
		return adjVolt;
	}
}
