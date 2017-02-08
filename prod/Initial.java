package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3070.robot.*;
//import com.ctre.TalonSRX;


public class Initial extends Robot implements Pronstants {		
	

	public void initialize() {	
		talBR = new TalonSRX(0);// 0 or 1
	talFR = new TalonSRX(5);// 5 or 3
	talFL = new TalonSRX(2);// 2 or 4
	talBL = new TalonSRX(1);// 1 or 2
       talBR.set(0);
		talFR.set(0);
		talFL.set(0);
		talBL.set(0);

	//TalonSRX talBR, talFR, talFL, talBL, TalC1, TalC2;
//	xbox = new XboxController(0);
	// gyro = new AnalogGyro(0);
	//ultra = new  (0,0);

		/*
		talBR.setCurrentLimit(CURRENT_LIMIT);
		talFR.setCurrentLimit(CURRENT_LIMIT);
		talBL.setCurrentLimit(CURRENT_LIMIT);
		talFL.setCurrentLimit(CURRENT_LIMIT);
*/
}
	  /* talBR.setVoltageRampRate(RAMP_RATE);
	talFR.setVoltageRampRate(RAMP_RATE);
	talBL.setVoltageRampRate(RAMP_RATE);
	talFL.setVoltageRampRate(RAMP_RATE);
	*/
}

