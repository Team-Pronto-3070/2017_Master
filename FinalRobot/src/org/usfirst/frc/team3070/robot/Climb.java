package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
methods:
public Climb() - constructs the class
public void checkClimbInput()
public void printClimblimValue()
 */

public class Climb {
	// Defines the climber talons
	static CANTalon talC1, talC2;
	// Defines the limit switches
	AnalogInput climbLim1, climbLim2;

	public Climb()
	{
		//Constructs the climber
		
		// Initializes the climber talons
		talC1 = new CANTalon(Pronstants.TALON_CLIMBER_1_PORT);
		talC2 = new CANTalon(Pronstants.TALON_CLIMBER_2_PORT);
		
		// Sets a voltage ramp rate on the talons
		talC1.setVoltageRampRate(Pronstants.RAMP_RATE);
		talC2.setVoltageRampRate(Pronstants.RAMP_RATE);
		
		// Sets a current amperage limit on the talons
		talC1.setCurrentLimit(Pronstants.CLIMB_CURRENT_LIMIT);
		talC2.setCurrentLimit(Pronstants.CLIMB_CURRENT_LIMIT);
		
		// Initializes limit switches
		climbLim1 = new AnalogInput(Pronstants.LIMIT_SWITCH_1_PORT);
		climbLim2 = new AnalogInput(Pronstants.LIMIT_SWITCH_2_PORT);
	}

	public void checkClimbInput(boolean button1, boolean button2) {
		// Handles the climber buttons on the joysticks
		
		// Creates variables for the limit switch voltages
		double limit1 = climbLim1.getVoltage();
		double limit2 = climbLim2.getVoltage();
		
		// Checks if the limit switches are not pressed
		if (limit1 < 3 && limit2 < 3) {
			// Checks if the first button and not the second button are pressed
			if (button1 && !button2) {
				// If so, climb up
				talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
				talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
			}
		}
		
		// Checks if the second button and not the first button are pressed
		else if (button2 && !button1) {
			// If so, climb down
			// This method is for after the round so we can undo the rope,
			// or if there is a potential error when we climb up
			talC1.set(Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(Pronstants.AUTO_CLIMB_SPEED);
		}
		
		// checks if both buttons are pressed
		else if (button1 && button2) {
			// If so, don't climb up or down
			// This is to protect against both the buttons getting pressed
			// and the talons trying to go both directions as a result
			talC1.set(0);
			talC2.set(0);
		}
		
		else {
			// If none of the above cases are true, don't climb up or down
			talC1.set(0);
			talC2.set(0);
		}
	}
	
	public void printClimblimValue() {
		// Prints the value of the limit switches
		// This method is here in case we want the driver to see how far the robot has climbed up
		SmartDashboard.putString("DB/String 7", "adj = %d" + climbLim1.getVoltage());
		SmartDashboard.putString("DB/String 8", "adj = %d" + climbLim2.getVoltage());
	}
}