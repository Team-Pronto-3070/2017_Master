package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

/*
methods:
public Shooter() - constructs the class
public void checkShootInput(boolean button) - manages the shooter button
public void stopShooter() - stops the shooter
public void shoot() - starts the shooter
 */

public class Shooter { 
	// Defines hopper talon
	static CANTalon talHopper = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
	// Defines shooter talon
	static CANTalon talShooter = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);

	public Shooter()
	{
		// Constructs the Shooter
		
		// Initializes hopper talon
		talHopper = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
		// Initializes shooter talon
		talShooter = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
		
 		// Sets a voltage ramp rate on the talons
		talHopper.setVoltageRampRate(Pronstants.RAMP_RATE);
		talShooter.setVoltageRampRate(Pronstants.RAMP_RATE);
 		
		// Sets a current amperage limit on the talons
		talHopper.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
		talShooter.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
	}

	public void checkShootInput(boolean button) {
 		// Handles the Shooter Button on the joysticks
		// While shooting is primarily for autonomous, this function is here
		// in case we have balls left over and extra time to shoot
		
 		// Checks if button1 is pressed and button2 is not
		if (button) {
 			// If so, shoot into the high goal
			shoot();
		}
		
		else {
			// Otherwise, don't shoot
			stopShooter();
		}
	}

	public void stopShooter() {
		// Stops the shooter
		talShooter.set(0);
		talHopper.set(0);
	}

	public void shoot() {
		// Starts the shooter
		talShooter.set(Pronstants.AUTO_SHOOT_SPEED);
		talHopper.set(Pronstants.AUTO_HOPPER_SPEED);
	}
}