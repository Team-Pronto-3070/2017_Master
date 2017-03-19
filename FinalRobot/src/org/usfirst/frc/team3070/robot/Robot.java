package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.vision.VisionThread;
import gripvis.vision;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

/*
methods:
public void robotInit() - Initializes robot
public void autonomousInit() - Run when autonomous is enabled before iterative program
public void autonomousPeriodic() - Iterative Autonomous Program
public void teleopInit() - Runs when robot is enabled for teleop before the iterative teleop program
public void teleopPeriodic() - Iterative teleop program
 */

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// Defines classes
	Drive drive;
	Auto auto;
	Climb climber;
	ProntoGyro gyro;
	Joystick joyL, joyR;
//	Shooter shoot;
	Thread checkSensors;

	// Defines smartDash buttons
	boolean button1, button2;
	
	// Defines the mode for autonomous
	int mode;
	
	// vision variables
	// public VisionThread visionThread;
	// public static vision grip;

	// Initializes robot
	@Override
	public void robotInit() {
		// Initializes FRC WPILIB Classes
		joyL = new Joystick(Pronstants.LEFT_JOYSTICK_PORT);
		joyR = new Joystick(Pronstants.RIGHT_JOYSTICK_PORT);
		
		// Initializes Pronto Classes
		gyro = new ProntoGyro();
		drive = new Drive(gyro);
		auto = new Auto(gyro, drive);
		climber = new Climb();
//		shoot = new Shooter();
		
		
		/**
		 Vision code (for when we need to implement it)
		 * grip = new vision();
		 * UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		 * camera.setResolution(Pronstants.IMG_WIDTH, Pronstants.IMG_HEIGHT);
		 * visionThread = new VisionThread (camera, grip,pipline ->{
		 * System.out.println(grip.findBlobsOutput().size());
		 */
	}
	
	// Run when autonomous is enabled before iterative program
	@Override
	public void autonomousInit() {
		// Resets the distance traveled
		drive.resetDistanceTraveled();
		
		// Enables the brake mode on the drive talons
		drive.toggleDriveTrain(true);
		
		// Sets the Ramp Rate on the drive talons
		drive.setDriveRampRate(Pronstants.AUTO_RAMP_RATE);
		
		// Resets the gyro
		drive.resetGyro();
		
		// Resets the distance traveled by the drive talons
		drive.resetDistanceTraveled();
		
		// Gets the value of the smartDash buttons
		button1 = SmartDashboard.getBoolean("DB/Button 0", false);
		button2 = SmartDashboard.getBoolean("DB/Button 1", false);
		
		// Gets the autonomous program we are running based off smartDash buttons
		mode = auto.chooseAuto(button1, button2);
	}

	// Iterative Autonomous Program
	@Override
	public void autonomousPeriodic() {
		// Tells the code which autonomous program to run
	switch (mode) {
		case Pronstants.AUTO_RIGHT:
			auto.run(Pronstants.AUTO_RIGHT);
			break;
		case Pronstants.AUTO_LEFT:
			auto.run(Pronstants.AUTO_LEFT);
			break;
		case Pronstants.AUTO_CENTER:
			auto.run(Pronstants.AUTO_CENTER);
			break;
		} 
	}
	
	// Runs when robot is enabled for teleop before the iterative teleop program
	public void teleopInit() {
		// Resets the distance traveled by the drive talons
		drive.resetDistanceTraveled();
		
		// Resets the gyro
		drive.resetGyro();
		
		// Sets the Ramp Rate on the talons to the regular ramp rate
		drive.setDriveRampRate(Pronstants.RAMP_RATE);
		
		// Enables coast mode on the drive talons
		drive.toggleDriveTrain(false);
	}

	// Iterative teleop program
	@Override
	public void teleopPeriodic() {
		// Drives the robot according to joystick inputs
		drive.joystickDrive(joyR.getRawAxis(1), joyL.getRawAxis(1));
		
		// Makes the robot climb up or down according to joystick inputs
		climber.checkClimbInput(joyR.getRawButton(2), joyL.getRawButton(8));
	}
}