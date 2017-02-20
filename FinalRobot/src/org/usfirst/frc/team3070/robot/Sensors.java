package org.usfirst.frc.team3070.robot;

public class Sensors {
	public static double[] findCamOut(String camIn) {
		camIn = camIn.substring(14);
		String[] camSplit = camIn.split(",");
		String[] strOut = {camSplit[0],camSplit[1].substring(1, camSplit[1].length()-1),camSplit[2].substring(6)};
		double[] out = {Double.parseDouble(strOut[0]), Double.parseDouble(strOut[1]), Double.parseDouble(strOut[2])};
		return out;
	}
}
