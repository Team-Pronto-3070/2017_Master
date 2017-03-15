package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
methods:
public void robotInit()
public void autonomousInit()
public void autonomousPeriodic()
public void teleopInit()
public void teleopPeriodic()
public void testInit()
public void testPeriodic()
 */

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// Defines Pronto classes
	Drive drive;
	Auto auto;
	Climb climber;
	Joystick joyL, joyR;
	Shooter shoot;
	// Defines a new SendableChooser to be the auto selector
	SendableChooser<Pronstants.AutoMode> autoChooser;
	// Creates a variable for the enums "AutoMode"
	Pronstants.AutoMode startMode;

	@Override
	public void robotInit() {
		// Runs when the Robot Starts

		// Initializes Joysticks
		joyL = new Joystick(Pronstants.LEFT_JOYSTICK_PORT);
		joyR = new Joystick(Pronstants.RIGHT_JOYSTICK_PORT);

		// Initializes Pronto Classes
		drive = new Drive();
		auto = new Auto(drive, shoot);
		climber = new Climb();
		shoot = new Shooter();

		// Starts the PID controller
		drive.startPID();
		
		// Creates the auto selector
		autoChooser = new SendableChooser<Pronstants.AutoMode>();
		// Creates the different optios for the auto selector
		autoChooser.addDefault("None",Pronstants.AutoMode.AUTO_MODE_NONE);
		autoChooser.addObject("Center -> Center", Pronstants.AutoMode.AUTO_MODE_CENTER_CENTER);
		autoChooser.addObject("Center -> Right", Pronstants.AutoMode.AUTO_MODE_CENTER_RIGHT);
		autoChooser.addObject("Center -> Left", Pronstants.AutoMode.AUTO_MODE_CENTER_LEFT);
		autoChooser.addObject("Left -> Left", Pronstants.AutoMode.AUTO_MODE_LEFT_LEFT);
		autoChooser.addObject("Right -> Right", Pronstants.AutoMode.AUTO_MODE_RIGHT_RIGHT);
		SmartDashboard.putData("DB/Auto Selector", autoChooser);
		
		// Prints SmartDash values
		SmartDashboard.putString("EncPos/FR", "Right Encoder = " + drive.getDriveValues()[0] + " feet");
		SmartDashboard.putString("EncPos/BL", "Left Encoder = " + drive.getDriveValues()[1] + " feet");
		SmartDashboard.putString("I/FR", "FR Current = " + drive.getDriveValues()[2]);
		SmartDashboard.putString("I/FL", "FL Current = " + drive.getDriveValues()[3]);
		SmartDashboard.putString("I/BR", "BR Current = " + drive.getDriveValues()[4]);
		SmartDashboard.putString("I/BL", "BL Current = " + drive.getDriveValues()[5]);
		climber.printClimblimValues();
		SmartDashboard.putNumber("Gyro/gyro", drive.getDriveValues()[6]);
	}

	@Override
	public void autonomousInit() {
		// Runs before the iterative autonomous code
		
		//resets the distance traveled
		drive.resetDistanceTraveled();
		
		// Sets the ramp rate on the drive talons to the autonomous ramp rate
		drive.setDriveRampRate(Pronstants.AUTO_RAMP_RATE);
		
		// resets the gyro
		drive.resetGyro();
		
		// turns brake mode on the talons on
		drive.toggleDriveMode(true);
		
		// creates a boolean for the value of the autonomous selector
		startMode = autoChooser.getSelected();		
		
		// resets the distance traveled
		drive.resetDistanceTraveled();
	}

	// Iterative autonomous program
	@Override
	public void autonomousPeriodic() {
		// Creates a state engine (selector) for autonomous
		switch(startMode) {
		// Checks if the selector is on "None"
		case AUTO_MODE_NONE:
			// If so, don't do anything
			break;
		// Checks if the selector is on "Center -> Center"
		case AUTO_MODE_CENTER_CENTER:
			// If so, run the program with the values for the center auto program
			auto.auto(Pronstants.AUTO_CENTER);
			break;
		// Checks if the selector is on "Right -> Right"
		case AUTO_MODE_RIGHT_RIGHT:
			// If so, run the program with the values for the right -> right auto program
			auto.auto(Pronstants.AUTO_OUTSIDE_RIGHT);
			break;
		// Checks if the selector is on "Left -> Left"
		case AUTO_MODE_LEFT_LEFT:
			// If so, run the program with the values for the left -> left auto program
			auto.auto(Pronstants.AUTO_OUTSIDE_LEFT);
			break;
		// Checks if the selector is on "Center -> Right"
		case AUTO_MODE_CENTER_RIGHT:
			// If so, run the program with the values for the center -> right auto program
			auto.auto(Pronstants.AUTO_CENTER_RIGHT);
			break;
		// Checks if the selector is on "Center -> Left"
		case AUTO_MODE_CENTER_LEFT:
			// If so, run the program with the values for the center -> left auto program
			auto.auto(Pronstants.AUTO_CENTER_RIGHT);
			break;
		}
	}

	// Runs before the Iterative teleop program
	public void teleopInit() {
		// Resets the distance traveled
		drive.resetDistanceTraveled();
		
		// Resets the Gyro
		drive.resetGyro();
		
		// Sets the drive talons to coast mode
		drive.toggleDriveMode(false);
		
		// Sets the ramp rate on the drive talons to the teleop ramp rate
		drive.setDriveRampRate(Pronstants.RAMP_RATE);
	}

	// Iterative Teleop Program
	@Override
	public void teleopPeriodic() {
		// Drives the robot according to the joystick inputs
		drive.joystickDrive(joyR.getRawAxis(0), joyL.getRawAxis(0), joyR.getRawAxis(2));
		
		// Makes the robot climb up/down according to the set joystick buttons
		climber.checkClimbInput(joyR.getRawButton(1), joyL.getRawButton(1));
		
		// Makes the robot shoot according to the set joystick buttons
		shoot.checkShootInput(joyR.getRawButton(2));
	}
	
	@Override
	public void testInit() {
		// Runs before the Iterative Test Mode program
		// NOTE: no other important comments will be here due to the fact that this mode is for Testing
	}
	
	@Override
	public void testPeriodic() {
		// Iterative Test Mode program
		// NOTE: no other important comments will be here due to the fact that this mode is for Testing
	}
}