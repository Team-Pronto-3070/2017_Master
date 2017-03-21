package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;

/*
methods:
public void checkShootInput()
public void stopShooter()
public void shoot()
 */

public class Shooter { 
//Defines talons for the shooter 
 CANTalon talHopper = new CANTalon(Pronstants.TALON_SHOOTER_2_PORT);
 CANTalon talShooter = new CANTalon(Pronstants.TALON_SHOOTER_1_PORT);
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
		if (button1) {
			talHopper.set(-Pronstants.AUTO_SHOOT_SPEED);
		} else {
			talHopper.set(0);
	
		}
		if (button2) {
			talShooter.set(Pronstants.AUTO_HOPPER_SPEED);
		} else {
			talShooter.set(0);

		}
	}

	public void stopShooter(){
		talShooter.set(0);
		talHopper.set(0);
	}

	public void shoot(){
		talHopper.set(Pronstants.AUTO_HOPPER_SPEED);
		Timer.delay(2);
		talShooter.set(Pronstants.AUTO_SHOOT_SPEED);
		System.out.println("waiting");
		Timer.delay(5);
		System.out.println("done");
		stopShooter();
	}
}