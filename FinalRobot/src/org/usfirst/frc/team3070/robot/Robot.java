package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	Shooter shoot;
	ProntoGyro gyro;
	// vision variables
	// public VisionThread visionThread;
	// public static vision grip;
	// defines the encoder starting variables
	public static int startEnc1;
	public static int startEnc2;
	// defines a double for adjusting the speed of the motors
	// public double[] distanceTraveled = drive.getDistanceTraveled();
	// creates a boolean for the control switcher button
	boolean button0 = false;
	boolean button1 = false;
	boolean button2 = false;
	boolean button3 = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initializes robot
		// Initializes FRC WPILIB Classes
		joyL = new Joystick(Pronstants.LEFT_JOYSTICK_PORT);
		joyR = new Joystick(Pronstants.RIGHT_JOYSTICK_PORT);
		// Initializes Pronto Classes
		drive = new Drive();
		auto = new Auto();
		climber = new Climb();
		shoot = new Shooter();
		gyro = new ProntoGyro();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(Pronstants.IMG_WIDTH, Pronstants.IMG_HEIGHT);

		// puts the auto program chooser on the dashboard

		/* disabled 3.18.17 2:51
		// grip = new vision();
		// vision code
				// visionThread = new VisionThread (camera, grip,pipline ->{
		// System.out.println(grip.findBlobsOutput().size());
		// });
		// visionThread.start();
*/
		

	}
	@Override
	public void autonomousInit() {
		// resets the distance traveled
		// resets the gyro
		drive.toggleDriveTrain(true);
		drive.setDriveRampRate(Pronstants.AUTO_RAMP_RATE);
		drive.resetGyro();
		drive.resetDistanceTraveled();
		// gets the start mode from the dashboard
		button0 = SmartDashboard.getBoolean("DB/Button 0", false);
		button1 = SmartDashboard.getBoolean("DB/Button 1", false);
		button2 = SmartDashboard.getBoolean("DB/Button 2", false);
		button3 = SmartDashboard.getBoolean("DB/Button 3", false);
		auto.state= 1;
	}

	// practice comment
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// what happens during autonomous (stays during autonomous)
		// tells the code which autonomous program to run based on buttons from
		// the SmartDash
		if (button0) {
			auto.autoC();
		}
		if (button1) {
			auto.autoOutsideLeft();
		}
		if (button2) {
			auto.autoOutsideRight();
		}
		
	}

	public void teleopInit() {
		drive.resetDistanceTraveled();
		drive.resetGyro();
		drive.setDriveRampRate(Pronstants.RAMP_RATE);
		drive.toggleDriveTrain(false);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// teleop programs (names are pretty self-explanatory)
		drive.joystickDrive(joyR.getRawAxis(1), joyL.getRawAxis(1), joyR.getTrigger());
		climber.checkClimbInput(joyR.getRawButton(2), joyL.getRawButton(8));
	}
}