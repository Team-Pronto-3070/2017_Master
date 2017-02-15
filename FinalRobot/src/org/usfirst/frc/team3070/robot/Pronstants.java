package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	static final double RAMP_RATE = 0.5; //The modifier of the negative acceleration the robot undergoes at the end of a movement.
	static final double AUTO_DRIVE_SPEED = 0.5; //The speed in which the robot travels in autonomous.
	static final double AUTO_CLIMB_SPEED = 0.5; //The speed in which the robot climbs the rope.
	static final double DEAD_ZONE = 0.1; //The value which the robot doesn't move if the controller value is under.
	static final int DRIVE_CURRENT_LIMIT = 39; //The value which limits the amp usage of the drive talons.
	static final int CLIMB_CURRENT_LIMIT = 29; //The value which limits the amp usage of the climber talons.
	//Port declarations should be self-explanatory.
	static final int TALON_FRONT_RIGHT_PORT = 0;
	static final int TALON_FRONT_LEFT_PORT = 1;
	static final int TALON_BACK_RIGHT_PORT = 2; 
	static final int TALON_BACK_LEFT_PORT = 3;
	static final int TALON_CLIMBER_1_PORT = 4;
	static final int TALON_CLIMBER_2_PORT = 5;
	static final int JOYSTICK_PORT = 0; 
	static final double TICK_COEFFICIENT = 162.97; //162.97 ticks per foot.
}
