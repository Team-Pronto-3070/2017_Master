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
	public void checkClimbInput(boolean button1, boolean button2) {
		if (climbLim1.getVoltage() > .5) {
			//SmartDashboard.putString("DB/Lim", );
		}
		if (button1 && !button2) {
			talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
		} 
		else if (button2 && !button1){
			talC1.set(Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(Pronstants.AUTO_CLIMB_SPEED);
		}
		else {
			talC1.set(0);
			talC2.set(0);
		}
	}
}