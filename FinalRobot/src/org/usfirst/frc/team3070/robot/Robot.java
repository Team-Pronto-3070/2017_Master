package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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
//	Shooter shoot;
	ProntoGyro gyro;
//	SendableChooser<Pronstants.AutoMode> autoChooser;
	Pronstants.AutoMode startMode;
	Thread checkSensors;
	Vision frcVision;

	// vision variables
	// public VisionThread visionThread;
	// public static vision grip;
	// defines the encoder starting variables
	public static int startEnc1;
	public static int startEnc2;
	// defines a double for adjusting the speed of the motors
	public static double adjSpeed;
	// public double[] distanceTraveled = drive.getDistanceTraveled();
	// creates a boolean for the control switcher button
	boolean button1 = false;
	boolean checkSwitch = false;
	String mode;

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
		
		climber = new Climb();
//		shoot = new Shooter();
		gyro = new ProntoGyro();
		frcVision = new Vision();
		frcVision.setDaemon(true);
		frcVision.start();
		auto = new Auto(frcVision, drive, gyro);
		
		// puts the auto program chooser on the dashboard

		/* disabled 3.18.17 2:51
		autoChooser = new SendableChooser<Pronstants.AutoMode>();
		autoChooser.addDefault("None", Pronstants.AutoMode.AUTO_MODE_NONE);
		autoChooser.addObject("Center -> Center", Pronstants.AutoMode.AUTO_MODE_CENTER_CENTER);
		autoChooser.addObject("Left -> Left", Pronstants.AutoMode.AUTO_MODE_LEFT_LEFT);
		autoChooser.addObject("Right -> Right", Pronstants.AutoMode.AUTO_MODE_RIGHT_RIGHT);
		SmartDashboard.putData("DB/Auto Selector", autoChooser);
//		// puts sensor values on the dashboard while disabled
//		checkSensors = new Thread(() -> {
//			while (!Thread.interrupted()) {
//			//	SmartDashboard.putString("EncPos/FR", "" + drive.getDistanceTraveled()[1]);
//				//SmartDashboard.putString("EncPos/BL", "" + drive.getDistanceTraveled()[0]);
////				SmartDashboard.putString("I/FR", "FR I: " + drive.talFR.getOutputCurrent());
////				SmartDashboard.putString("I/FL", "FL I: " + drive.talFL.getOutputCurrent());
////				SmartDashboard.putString("I/BR", "BR I: " + drive.talBR.getOutputCurrent());
////				SmartDashboard.putString("I/BL", "BL I: " + drive.talBL.getOutputCurrent());
//			//	SmartDashboard.putNumber("Gyro/gyro", gyro.getOffsetHeading());
//				Timer.delay(.1);
//			}
//
//		});
//		checkSensors.setDaemon(true);
//		checkSensors.start();
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
	@Override
	public void autonomousInit() {
		// resets the distance traveled
		drive.resetDistanceTraveled();
		// resets the gyro
		drive.toggleDriveTrain(true);
		drive.setDriveRampRate(Pronstants.AUTO_RAMP_RATE);
		drive.resetGyro();
		drive.resetDistanceTraveled();
		// gets the start mode from the dashboard
		//mode = autoChooser.getSelected().toString();

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
/*
	switch (mode) {
		case Pronstants.AUTO_MODE_NONE:
			drive.drive(0, 0);
			break;
		case Pronstants.AUTO_MODE_CENTER_CENTER:
			auto.autoC();
			break;
		case Pronstants.AUTO_MODE_RIGHT_RIGHT:
			auto.autoOutsideRight();
			break;
		case Pronstants.AUTO_MODE_LEFT_LEFT:
			auto.autoOutsideLeft();
			break;
		}
		checkSensors(); 
	} */
		auto.autoC();
		//auto.autoC();
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
		drive.joystickDrive(joyR.getRawAxis(1), joyL.getRawAxis(1), joyR.getRawAxis(2));
		climber.checkClimbInput(joyR.getRawButton(2), joyL.getRawButton(8));
//		checkSensors();
	}
	
//	private void checkSensors() {
//		SmartDashboard.putString("EncPos/FR", "" + drive.talFR.getEncPosition());
//		SmartDashboard.putString("EncPos/BL", "" + drive.talBL.getEncPosition());
//		SmartDashboard.putString("I/FR", "FR I: " + drive.talFR.getOutputCurrent());
//		SmartDashboard.putString("I/FL", "FL I: " + drive.talFL.getOutputCurrent());
//		SmartDashboard.putString("I/BR", "BR I: " + drive.talBR.getOutputCurrent());
//		SmartDashboard.putString("I/BL", "BL I: " + drive.talBL.getOutputCurrent());
//		SmartDashboard.putNumber("Gyro/gyro", gyro.getOffsetHeading());
//	}
}