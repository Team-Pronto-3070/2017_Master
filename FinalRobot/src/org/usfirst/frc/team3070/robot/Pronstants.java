package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	
	
	// Ports:
	static final int TALON_FRONT_RIGHT_PORT = 3;	//Run both right motors together ALWAYS
	static final int TALON_BACK_RIGHT_PORT = 6;		//Because of motor placement, the right side is NEGATIVE
	static final int TALON_FRONT_LEFT_PORT = 4;	 	//Run both left motors together ALWAYS
	static final int TALON_BACK_LEFT_PORT = 5;		//The left side is POSITIVE
	
	static final int TALON_CLIMBER_1_PORT = 1;		//ALWAYS run the two climber motors together
	static final int TALON_CLIMBER_2_PORT = 2;		//
	
	static final int TALON_SHOOTER_1_PORT = 8;		//Shooter one is for the shooting mechanism
	static final int TALON_SHOOTER_2_PORT = 9;		//Shooter two is for the ball loading mechanism
	
	static final int LIMIT_SWITCH_1_PORT = 0;		//Limit switches one and two are toggled on when we touch
	static final int LIMIT_SWITCH_2_PORT = 1;		//the button which means we are done climbing
	
	static final int LEFT_JOYSTICK_PORT = 0;		//Left joystick controls the left side motors
	static final int RIGHT_JOYSTICK_PORT = 1;		//Right joystick controls the right side motors
	
	static final int CONTROL_SWITCH_BUTTON = 6;		//Button for switching joystick controls (probably won't be used)
	
	
	// Auto values:
	static final double AUTO_TURN_SPEED = 0.3;		//Maximum percentage of full speed the motors run while turning
	static final double AUTO_DRIVE_SPEED = 0.3;		//Maximum percentage of full speed the motors run while driving straight
	static final double AUTO_CLIMB_SPEED = 0.8;		//Maximum percentage of full speed the motors run while climbing
	static final double AUTO_SHOOT_SPEED = 1.0;		//Maximum percentage of full speed the motors run while shooting
	static final double AUTO_HOPPER_SPEED = 1.0;	//Maximum percentage of full speed the motors run while loading balls
	
	static final int AUTO_SIDE_LEFT = 1;			//Should be replaced with the values from the AutoMode enum
	static final int AUTO_SIDE_RIGHT = 2;			//Should be replaced with the values from the AutoMode enum
	
	static enum AutoMode { AUTO_MODE_NONE,			//Robot doesn't move during autonomous
						   AUTO_MODE_CENTER_CENTER,	//Starts at center, loads gear at center
						   AUTO_MODE_CENTER_RIGHT,	//Starts at center, loads gear at right
						   AUTO_MODE_CENTER_LEFT,	//Starts at center, loads gear at left
						   AUTO_MODE_LEFT_LEFT,		//Starts at left, loads gear at left
						   AUTO_MODE_RIGHT_RIGHT };	//Starts at right, loads gear at right
	
	
	//Sensor variables:
	static final int IMG_WIDTH = 320;				//Width of camera image
	static final int IMG_HEIGHT = 240;				//Heigth of camera image
	
	static final double ADJUSTING_CONSTANT = 0.012;	//Constant for finding an adjustment speed for driving straight
	static final double TURN_OFFSET = 3;			//Adjusting constant for the turn function
	static final double TICK_COEFFICIENT = 638.0;	//Conversion value from encoder ticks to feet
	
	static final double DISTANCE_OFFSET = 0.5;		//How far in feet the robot coasts after braking in autonomous
	
	
	//Other variables:
	static final double RAMP_RATE = 0.5;			//Ramp rate for the motor voltage, in volts per second (?) TODO: What is the actual unit?
	
	static final double DEAD_ZONE = 0.1;			//How far the joystick needs to be pushed before it sends value to motors
	
	static final int DRIVE_CURRENT_LIMIT = 39;		//Limits the amp usage of drive talons
	static final int CLIMB_CURRENT_LIMIT = 29;		//Limits the amp usage of climber talons
	static final int SHOOT_CURRENT_LIMIT = 29;		//Limits the amp usage of shooter talons
	
}
