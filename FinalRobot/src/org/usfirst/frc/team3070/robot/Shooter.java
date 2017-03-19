package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

/*
methods:
public void checkShootInput()
public void stopShooter()
public void shoot()
 */

public class Shooter { 

	//Defines and Initialzes talons for the shooter 
static CANTalon talHopper = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
static CANTalon talShooter = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
	
// Constructs the class
	public Shooter()
	{
		// Defines the talon variables
		talHopper = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
		talShooter = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
 		// Sets a voltage ramp rate on the talons
		talHopper.setVoltageRampRate(Pronstants.RAMP_RATE);
		talShooter.setVoltageRampRate(Pronstants.RAMP_RATE);
// 		// Sets a current limit on the talons
		talHopper.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
		talShooter.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
	}

	// Maps shooter to joystick buttons
	public void checkShootInput(boolean button1) {
 		// Checks if button1 is pressed
		if (button1) {
 			// If so, shoot
			shoot(Pronstants.AUTO_HOPPER_SPEED, Pronstants.AUTO_SHOOT_SPEED);
		}
		else {
			// In any other case, set shooter to 0
			shoot(0, 0);
		}
	}

	// Stops the shooter
	public void stopShooter(){
		talShooter.set(0);
		talHopper.set(0);
	}

	// Starts the shooter
	public void shoot(double hopperSpeed, double shootSpeed){
		talShooter.set(hopperSpeed);
		talHopper.set(shootSpeed);
	}
}