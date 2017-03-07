package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
methods:
public static double[] findCamOut(String camIn)
public int[] ultrasonicOutput()
 */

public class Sensors {
	AnalogInput ultra1, ultra2;
	public Sensors() {
		ultra1 = new AnalogInput(2);
		ultra2 = new AnalogInput(3);
	}
	public static double[] findCamOut(String camIn) {
		camIn = camIn.substring(14);
		String[] camSplit = camIn.split(",");
		String[] strOut = {camSplit[0],camSplit[1].substring(1, camSplit[1].length()-1),camSplit[2].substring(6)};
		double[] out = {Double.parseDouble(strOut[0]), Double.parseDouble(strOut[1]), Double.parseDouble(strOut[2])};
		return out;
	}
	
	public int[] ultrasonicOutput() {
		int[] ar = new int[2];
		ar[0] = ultra1.getAverageValue();
		ar[1] = ultra2.getAverageValue();
		
		SmartDashboard.putString("DB/String 4", "leftUltra = %d" + ar[0]);
		SmartDashboard.putString("DB/String 5", "rightUltra = %d" + ar[1]);
		
		return ar;
	}

}
