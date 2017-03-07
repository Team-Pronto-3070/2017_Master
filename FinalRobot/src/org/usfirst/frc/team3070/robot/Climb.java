package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
methods:
public void checkClimbInput()
public void printClimblimValue()
 */

public class Climb {
	//Defines talons for the climber
	static CANTalon talC1, talC2;
//	static AnalogTrigger limitSwitch;
//	AnalogInput climbLim;
	AnalogInput climbLim1;
	AnalogInput climbLim2;

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
		//TODO: Implement analog switch with boolean output that triggers when at top, stop climber motors.
		//defines limit switch
		climbLim1 = new AnalogInput(Pronstants.LIMIT_SWITCH_1_PORT);
		climbLim2 = new AnalogInput(Pronstants.LIMIT_SWITCH_2_PORT);
//		limitSwitch = new AnalogTrigger(Pronstants.LIMIT_SWITCH_PORT);
	}
//	public void checkClimbInput(boolean button1, boolean button2) {
//		//maps climber to joystick buttons
//		//checks if button1 is pressed and button2 is not
//		if (button1 && !button2) {
//			if (!limitSwitch.getTriggerState()) {
//				//If true, climb up
//				talC1.set(Pronstants.AUTO_CLIMB_SPEED);
//				talC2.set(Pronstants.AUTO_CLIMB_SPEED);
//			}
//			//checks if the limit switch is pressed
//			if (limitSwitch.getTriggerState()) {
//				//If true, climb down
//				talC1.set(0);
//				talC2.set(0);
//			}
//		}
//		//checks if button2 is pressed and button1 is not
//		else if (button2 && !button1) {
//			talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
//			talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
//		}
//		//checks if button1 and button2 are pressed
//		else if (button1 && button2) {
//			//If true, set climber to 0
//			//This prevents the climber from trying to go in 2 directions at once
//			talC1.set(0);
//			talC2.set(0);
//		}
//		//In any other case, set climber to 0
//		else {
//			talC1.set(0);
//			talC2.set(0);
//		}
//	}

	public void checkClimbInput(boolean button1, boolean button2) {
		double limit1 = climbLim1.getVoltage();
		double limit2 = climbLim2.getVoltage();
		SmartDashboard.putNumber("DB/String 7", limit1);
		SmartDashboard.putNumber("DB/String 8", limit2);
		if (limit1 < 3 && limit2 < 3) {
			if (button1 && !button2) {
				//these should BOTH BE NEGATIVE, ALEX >:(
				talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
				talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
			}
		}
		else if (button2 && !button1){
			talC1.set(Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(Pronstants.AUTO_CLIMB_SPEED);
		}
		else if (button1 && button2) {
			talC1.set(0);
			talC2.set(0);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
	}
	
	public void printClimblimValue() {
		SmartDashboard.putString("DB/String 7", "adj = %d" + climbLim1.getVoltage());
		SmartDashboard.putString("DB/String 8", "adj = %d" + climbLim2.getVoltage());
	}
}