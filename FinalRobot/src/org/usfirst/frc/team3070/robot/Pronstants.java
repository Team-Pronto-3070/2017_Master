package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	//The modifier of the negative acceleration the robot undergoes at the end of a movement.
	static final double RAMP_RATE = 0.5;
	//The speed at which the robot travels in autonomous.
	static final double AUTO_DRIVE_SPEED = 0.3;
	//The speed at which the robot climbs the rope.
	static final double AUTO_CLIMB_SPEED = 0.8;
	//The speed at which the robot shoots the balls.
	static final double AUTO_SHOOT_SPEED = 1;
	//The value which the robot doesn't move if the controller value is under.
	static final double DEAD_ZONE = 0.1;
	//The value which limits the amp usage of the drive talons.
	static final int DRIVE_CURRENT_LIMIT = 39;
	//The value which limits the amp usage of the climber talons.
	static final int CLIMB_CURRENT_LIMIT = 29;
	//The value which limits the amp usage of the shooter talons.
	static final int SHOOT_CURRENT_LIMIT = 29;
	//Port declarations should be self-explanatory.
	//NOTE: Encoders are on Ports 3 and 5
	static final int TALON_FRONT_RIGHT_PORT = 0;
	static final int TALON_FRONT_LEFT_PORT = 1;
	static final int TALON_BACK_RIGHT_PORT = 2; 
	static final int TALON_BACK_LEFT_PORT = 3;
	static final int TALON_CLIMBER_1_PORT = 4;
	static final int TALON_CLIMBER_2_PORT = 5;
	static final int TALON_SHOOTER_PORT = 6;
	static final int LIMIT_SWITCH_PORT = 7;
	static final int LEFT_JOYSTICK_PORT = 0;
	static final int RIGHT_JOYSTICK_PORT = 1;
	//Conversion from encoder values to feet, which is 162.97 ticks per foot.
	static final double TICK_COEFFICIENT = 162.97;
	//sensors variables
	static final int IMG_WIDTH = 320;
	static final int IMG_HEIGHT = 240;
	static final double ADJUSTING_CONSTANT = .016;
	static final double ANGLE_VARIANCE = .5;
}
