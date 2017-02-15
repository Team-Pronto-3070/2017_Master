package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

public class Climb {
	//Defines talons for the climber
	static CANTalon talC1, talC2;
	//climber constructor
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
		//maps climber to joystick buttons
		//checks if button1 is pressed and button2 is not
		if (button1 && !button2) {
			//If true, climb up
			talC1.set(Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(Pronstants.AUTO_CLIMB_SPEED);
		}
		//checks if button2 is pressed and button1 is not
		if (button2 && !button1) {
			//If true, climb down
			talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
		}
		//checks if button1 and button2 are pressed
		if (button1 && button2) {
			//If true, set climber to 0
			//This prevents the climber from trying to go in 2 directions at once
			talC1.set(0);
			talC2.set(0);
		}
		//In any other case, set climber to 0
		else {
			talC1.set(0);
			talC2.set(0);
		}
	}
}