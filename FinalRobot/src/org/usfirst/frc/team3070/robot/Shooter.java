package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;

/*
methods:
public Shooter() - Constructs the class
public void checkShootInput() - Runs the shooter based off joystick values
public void stopShooter() - Stops the shooter
public void shoot() - Starts the shooter
 */

public class Shooter { 
	// Defines talons for the shooter 
	CANTalon talHopper = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
	CANTalon talShooter = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
	private Timer timer;
	// Constructs the class
	public Shooter() {
		timer = new Timer();
		// Defines the talon variables
		talHopper = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
		talShooter = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
 		// Sets a voltage ramp rate on the talons
		talHopper.setVoltageRampRate(Pronstants.RAMP_RATE);
		talShooter.setVoltageRampRate(Pronstants.RAMP_RATE);
 		// Sets a current limit on the talons
		talHopper.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
		talShooter.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
	}
	// Runs the shooter based off joystick values
	public void checkShootInput(boolean button1, boolean button2) {
 		// Checks if button1 is pressed
		if (button1) {
			// If so, run the hopper
			talHopper.set(-Pronstants.AUTO_SHOOT_SPEED);
		}
		
		else {
			// Otherwise, don't run the hopper
			talHopper.set(0);
		}
		
		// Checks if button2 is pressed
		if (button2) {
			// If so, run the shooter
			talShooter.set(Pronstants.AUTO_HOPPER_SPEED);
		}
		
		else {
			// Otherwise, don't run the shooter
			talShooter.set(0);
		}
	}

	// Stops the shooter
	public void stopShooter(){
		talShooter.set(0);
		talHopper.set(0);
	}

	public void runHopper() {
		talHopper.set(Pronstants.AUTO_HOPPER_SPEED);
	}
	
	public void runShooter() {
		talShooter.set(Pronstants.AUTO_SHOOT_SPEED);
	}
	
	
	// Runs the shooter autonomously
	public void shoot(){
		// Starts the shooter
		talShooter.set(Pronstants.AUTO_SHOOT_SPEED);
		// Waits 1.5 seconds
		// This lets the shooter warm up first
		
		// Runs the hopper
		talHopper.set(Pronstants.AUTO_HOPPER_SPEED);
		// Waits 5 seconds for the balls to empty
		Timer.delay(5);
		// Stops the shooter
		stopShooter();
	}
}