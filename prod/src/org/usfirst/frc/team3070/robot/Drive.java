package org.usfirst.frc.team3070.robot;
import com.ctre
// TODO: install and import can talon classimport com
public class Drive {
	
private  CANTalon talFR;
 private CANTalon talFL;
private	CANTalon talBR;
private CANTalon talBL;
	
	public Drive(int tal1, int tal2, int tal3, int tal4)
	{
		// Initialize talon object
	}
	
	public void drive(double leftSpeed, double rightSpeed)
	{
		talFR.set(rightSpeed);
		talBR.set(rightSpeed);
		talFL.set(leftSpeed);
		talBL.set(leftSpeed);
	}
}
