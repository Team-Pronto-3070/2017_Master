package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.IterativeRobot;

/* Vision Imports
import edu.wpi.first.wpilibj.vision.VisionThread;
import gripvis.vision;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

/*
methods:
public void robotInit()
public void autonomousInit()
public void autonomousPeriodic()
public void teleopInit()
public void teleopPeriodic()
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
	Joystick joyL, joyR;
	Shooter shooter;
	ProntoGyro gyro;
	
	// Vision variables
	// public VisionThread visionThread;
	// public static vision grip;

	// Defines booleans for the smartDash buttons for the autonomous selector and sets them to false
	boolean autoC = false;
	boolean autoR = false;
	boolean autoL = false;
	
	// Creates an integer representing the autonomous mode
	int mode;
	
	// Creates a boolean of whether we are shooting during autonomous or not
	boolean shoot = false;

	// Runs when robot is initially turned on
	@Override
	public void robotInit() {
		// Initializes FRC WPILIB Classes
		joyL = new Joystick(Pronstants.LEFT_JOYSTICK_PORT);
		joyR = new Joystick(Pronstants.RIGHT_JOYSTICK_PORT);
		
		// Initializes Pronto Classes
		shooter = new Shooter();
		gyro = new ProntoGyro();
		drive = new Drive(gyro);
		auto = new Auto(drive, shooter);
		climber = new Climb();

		/* disabled 3.18.17 2:51
		 * Extra vision code
		// grip = new vision();
		// vision code
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(Pronstants.IMG_WIDTH, Pronstants.IMG_HEIGHT);
		// visionThread = new VisionThread (camera, grip,pipline ->{
		// System.out.println(grip.findBlobsOutput().size());
		// });
		// visionThread.start();
		 */
		

	}
	
	public void autonomousDisabled()
	{
		drive.toggleDriveTrain(false);
	}
	
	// Runs when the robot is enabled before the iterative autonomous program
	@Override
	public void autonomousInit() {
		// Resets the distance traveled
		drive.resetDistanceTraveled();

		// Puts the drive talons in brake mode
		drive.toggleDriveTrain(true);
		
		// Sets the voltage ramp rate of the talons to the autonomous ramp rate
		drive.setDriveRampRate(Pronstants.AUTO_RAMP_RATE);
		
		// Resets the gyro
		drive.resetGyro();

		// Gets the value of the smartDash buttons for the autonomous selector
		autoC = SmartDashboard.getBoolean("DB/Button 0", false);
		autoR = SmartDashboard.getBoolean("DB/Button 1", false);
		autoL = SmartDashboard.getBoolean("DB/Button 2", false);
		shoot = SmartDashboard.getBoolean("DB/Button 3", false);
		
		// Selects the autonomous based on those buttons
		mode = auto.getSelected(autoC, autoR, autoL);
		
		
	}

	// Iterative autonomous program
	@Override
	public void autonomousPeriodic() {
		// Tells the code which autonomous program to run based on buttons from the smartDash
		auto.run(mode, shoot);

	}

	// Runs before the iterative teleop program
	public void teleopInit() {
		// Resets the distance traveled
		drive.resetDistanceTraveled();
		
		// Resets the gyro
		drive.resetGyro();
		
		// Sets the voltage ramp rate to the teleop/regular ramp rate
		drive.setDriveRampRate(Pronstants.RAMP_RATE);
		
		// Sets the drive talons to coast mode
		drive.toggleDriveTrain(false);
	}

	// Iterative teleop program
	@Override
	public void teleopPeriodic() {
		// Drives the robot according to the joystick inputs
		drive.joystickDrive(joyR.getRawAxis(1), joyL.getRawAxis(1), joyR.getTrigger());
		
		// Makes the robot climb up or down according to the joystick inputs
		climber.checkClimbInput(joyR.getRawButton(2), joyL.getRawButton(8));
		
		// Makes the robot shoot according to joystick input
//		shooter.checkShootInput(joyR.getRawButton(4), joyL.getRawButton(4));
	}
}