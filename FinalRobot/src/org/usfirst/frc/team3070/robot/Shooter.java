package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

public class Shooter { 
//Defines talons for the shooter 
static CANTalon talHopper = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
static CANTalon talShooter = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
	//shooter constructor
	public Shooter()
	{
		//defines the talon variables
		talHopper = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
		talShooter = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
// 		//sets a voltage ramp rate on the talons
		talHopper.setVoltageRampRate(Pronstants.RAMP_RATE);
		talShooter.setVoltageRampRate(Pronstants.RAMP_RATE);
// 		//sets a current limit on the talons
		talHopper.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
		talShooter.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
	}

	public void checkShootInput(boolean button1, boolean button2) {
 		//maps shooter to joystick buttons
 		//checks if button1 is pressed and button2 is not
		if (button1 && !button2) {
 			//If true, shoot up
			talHopper.set(Pronstants.AUTO_SHOOT_SPEED);
			talShooter.set(Pronstants.AUTO_SHOOT_SPEED);
		}
 		//checks if button2 is pressed and button1 is not
		if (button2 && !button1) {
			//If true, shoot down
			talHopper.set(-Pronstants.AUTO_SHOOT_SPEED);
			talShooter.set(-Pronstants.AUTO_SHOOT_SPEED);
		} 
 		//checks if button1 and button2 are pressed
		if (button1 && button2) {
 			//If true, set shooter to 0
 			//This prevents the shooter from trying to go in 2 directions at once
			talHopper.set(0);
			talShooter.set(0);
	}
 		//In any other case, set shooter to 0
		else {
			talHopper.set(0);
			talShooter.set(0);
		}
	}

	public void stopShooter(){
		talShooter.set(0);
		talHopper.set(0);
	}

	public void shoot(){
		talShooter.set(Pronstants.AUTO_SHOOT_SPEED);
		talHopper.set(Pronstants.AUTO_HOPPER_SPEED);
	}
}