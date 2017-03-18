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

	//Constructs the class
	public Climb() {
		// Initializes the climber talons
		talC1 = new CANTalon(Pronstants.TALON_CLIMBER_1_PORT);
		talC2 = new CANTalon(Pronstants.TALON_CLIMBER_2_PORT);
		
		// Sets a voltage ramp rate on the talons
		talC1.setVoltageRampRate(Pronstants.RAMP_RATE);
		talC2.setVoltageRampRate(Pronstants.RAMP_RATE);
		
		// Sets a current amperage limit on the talons
		talC1.setCurrentLimit(Pronstants.CLIMB_CURRENT_LIMIT);
		talC2.setCurrentLimit(Pronstants.CLIMB_CURRENT_LIMIT);
	}

	// Handles the climber buttons on the joysticks
	public void checkClimbInput(boolean button1, boolean button2) {		
		
		// Checks if the limit switches are not pressed
			if (button1 && !button2) {
				// If so, climb up
				talC1.set(-Pronstants.AUTO_CLIMB_SPEED);
				talC2.set(-Pronstants.AUTO_CLIMB_SPEED);
			}
		
		
		// Checks if the second button and not the first button are pressed
		else if (button2 && !button1) {
			// If so, climb down
			// This method is for after the round so we can undo the rope,
			// or if there is a potential error when we climb up
			talC1.set(Pronstants.AUTO_CLIMB_SPEED);
			talC2.set(Pronstants.AUTO_CLIMB_SPEED);
		}
		
		else {
			// If none of the above cases are true, don't climb up or down
			talC1.set(0);
			talC2.set(0);
		}
	}
}