package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;

public class Shooter {
	//Defines talons for the shooter
	static CANTalon talS;
	//shooter constructor
	public Shooter()
	{
		//defines the talon variables
		talS = new CANTalon(5);
		//sets a voltage ramp rate on the talons
	//	talS.setVoltageRampRate(Pronstants.RAMP_RATE);
		//sets a current limit on the talons
		talS.setCurrentLimit(Pronstants.SHOOT_CURRENT_LIMIT);
	}
	public void checkShootInput(boolean button1, boolean button2) {
		//maps shooter to joystick buttons
		//checks if button1 is pressed and button2 is not
		if (button1 && !button2) {
			//If true, shoot up
			talS.set(Pronstants.AUTO_SHOOT_SPEED);
		}
		//checks if button2 is pressed and button1 is not
		if (button2 && !button1) {
			//If true, shoot down
			talS.set(-Pronstants.AUTO_SHOOT_SPEED);
		}
		//checks if button1 and button2 are pressed
		if (button1 && button2) {
			//If true, set shooter to 0
			//This prevents the shooter from trying to go in 2 directions at once
			talS.set(0);
		}
		//In any other case, set shooter to 0
		else {
			talS.set(0);
		}
	}
	public void shooterStop(){
		talS.set(0);
	}
	public void shooterGo(){
		talS.set(-.965);
		System.out.println(talS.getBusVoltage());
	}
	public void shooterGoNega(){
		talS.set(.965);
	}
}