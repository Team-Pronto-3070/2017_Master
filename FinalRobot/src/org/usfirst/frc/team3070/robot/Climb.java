package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

public class Climb {
	static CANTalon talC1, talC2;
	public Climb()
	{
		//defines the talon variables
		talC1 = new CANTalon(Pronstants.TALON_CLIMBER_1_PORT);
		talC2 = new CANTalon(Pronstants.TALON_CLIMBER_2_PORT);
		//sets a voltage ramp rate on the talons
		talC1.setVoltageRampRate(Pronstants.RAMP_RATE);
		talC2.setVoltageRampRate(Pronstants.RAMP_RATE);
		//sets a current limit on the talons
		talC1.setCurrentLimit(Pronstants.CLIMB_CURRENT_LIMIT);
		talC2.setCurrentLimit(Pronstants.CLIMB_CURRENT_LIMIT);
	}
	public void checkClimbInput(boolean button1, boolean button2) {
		if (button1 && !button2) {
			talC1.set(Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(Pronstants.AUTO_CLIMB_SPEED);
		}
		if (button2 && !button1) {
			talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
		}
		if (button1 && button2) {
			talC1.set(0);
			talC2.set(0);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
	}
}