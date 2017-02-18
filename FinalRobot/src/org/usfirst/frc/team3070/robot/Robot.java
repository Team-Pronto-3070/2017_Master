package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.VisionThread;
import gripvis.vision;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Defines classes
	Drive drive;
	Auto auto;
	Climb climber;
	Joystick joyL, joyR;
	Shooter shoot;
	ProntoGyro gyro;
	public VisionThread visionThread;
	public static vision grip;
	public static double startHeading;
	public static int startEnc1;
	public static int startEnc2;
	public static double adjSpeed;
	public double[] distanceTraveled = drive.getDistanceTraveled();
	
	//creates booleans for buttons on the SmartDash
	double value;
	double autoCode = SmartDashboard.getNumber("DB/Slider 0", value);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Initializes robot
		//Initializes FRC WPILIB Classes
		joyL = new Joystick(Pronstants.LEFT_JOYSTICK_PORT);
		joyR = new Joystick(Pronstants.RIGHT_JOYSTICK_PORT);
		//Initializes Pronto Classes
		drive = new Drive();
		auto = new Auto(drive);
		climber = new Climb();
		shoot = new Shooter();
		gyro = new ProntoGyro();

		grip = new vision();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(Pronstants.IMG_WIDTH, Pronstants.IMG_HEIGHT);
		startHeading = gyro.calculateHeading();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		drive.setStraightValue();
		drive.resetDistanceTraveled();
		gyro.reset();
	}
//practice comment
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//what happens during autonomous (stays during autonomous)
		//tells the code which autonomous program to run based on buttons from the SmartDash
		if (autoCode >= 0.4 && autoCode <= 0.6) {
			auto.dummy1();
		}
		else if (autoCode >= 0.9 && autoCode >= 1.1) {
			auto.dummy2();
		}
		SmartDashboard.putString("DB/String 0", "heading = " + (gyro.calculateHeading()));
		SmartDashboard.putString("DB/String 1", "enc1 =" + distanceTraveled[0]);
		SmartDashboard.putString("DB/String 2", "enc2 = " + distanceTraveled[1]);
		climber.printClimblimVoltage();
		climber.checkClimbImput2(joyL.getRawButton(2));
//		switch (autoSelected) {
//		case customAuto:
//			// Put custom auto code here
//			break;
//		case defaultAuto:
//		default:
//			// Put default auto code here
//			break;
//		}
}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//teleop programs  (names are pretty self-explanatory)
		climber.checkClimbImput2(joyL.getRawButton(2));
		shoot.checkShootInput(joyL.getRawButton(1), joyR.getRawButton(1));
		drive.joystickDrive(joyR.getRawAxis(0), joyL.getRawAxis(0));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		//unimportant
	}
}

