package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	//Defines classes
	Drive drive;
	Auto auto;
	Climb climber;
	Joystick joyL, joyR;
	Shooter shoot;
	ProntoGyro gyro;
	Sensors sensors;
	//vision variables
//	public VisionThread visionThread;
//	public static vision grip;
	//defines the encoder starting variables
	public static int startEnc1;
	public static int startEnc2;
	//defines a double for adjusting the speed of the motors
	public static double adjSpeed;
	//public double[] distanceTraveled = drive.getDistanceTraveled();
	//creates a boolean for the control switcher button
	boolean button1 = false;
	//creates booleans for buttons on the SmartDash
	boolean dash1;
	boolean dash2;
	boolean dash3;
	boolean dash4;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Initializes robot
		//Initializes FRC WPILIB Classes
		joyL = new Joystick(Pronstants.LEFT_JOYSTICK_PORT);
	//	joyR = new Joystick(Pronstants.RIGHT_JOYSTICK_PORT);
		//Initializes Pronto Classes
		drive = new Drive(gyro);
		auto = new Auto(drive);
		climber = new Climb();
		shoot = new Shooter();
		gyro = new ProntoGyro();
		sensors = new Sensors();
//		grip = new vision();
	//vision code
//		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setResolution(Pronstants.IMG_WIDTH, Pronstants.IMG_HEIGHT);
//visionThread = new VisionThread (camera, grip,pipline ->{
//			System.out.println(grip.findBlobsOutput().size());
//		});
//visionThread.start();
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
		//resets the distance traveled
		drive.resetDistanceTraveled();
		// resets the gyro
		drive.talFR.enableBrakeMode(true);
		drive.talFL.enableBrakeMode(true);
		drive.talBR.enableBrakeMode(true);
		drive.talBL.enableBrakeMode(true);
		drive.resetGyro();
		//gyro.reset();
		//gets the value of the buttons on the "basic" smartDash tab
		dash1 = SmartDashboard.getBoolean("DB/Button 0", false);
		dash2 = SmartDashboard.getBoolean("DB/Button 1", false);
		dash3 = SmartDashboard.getBoolean("DB/Button 2", false);
		dash4 = SmartDashboard.getBoolean("DB/Button 3", false);
	}
//practice comment
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//what happens during autonomous (stays during autonomous)
		//tells the code which autonomous program to run based on buttons from the SmartDash
		//checks if the 1st and 4th buttons are pressed
//		drive.turn(90, Pronstants.AUTO_DRIVE_SPEED);
		if (dash1 && dash4) {
			//if so, run the left side to left gearloader code
			auto.autoOutsideCenter(Pronstants.AUTO_SIDE_LEFT);
		}
		//checks if the 1st button and not 4th button is pressed
		else if (dash1 && !dash4) {
			//if so, run the right side to right gearloader code
			auto.autoOutsideCenter(Pronstants.AUTO_SIDE_RIGHT);
		}
		//checks if the 2nd and 4th buttons are pressed
		else if (dash2 && dash4) {
			//if so, run the left side to center gearloader code
			auto.autoOutside(Pronstants.AUTO_SIDE_LEFT);
		}
		//checks if the 2nd button and not the 4th button is pressed
		else if (dash2 && !dash4) {
			//if so, run the right side to center gearloader code
			auto.autoOutside(Pronstants.AUTO_SIDE_RIGHT);
		}
		//checks if the 3rd button is pressed
		else if (dash3) {
			//if so, run the center to center gearloader code
			auto.autoC();
		}
		else {
			//if none of the previous cases are true, have the robot stay still
			drive.turn(90, 0.15);
		}

//		SmartDashboard.putString("DB/String 1", "enc1 =" + distanceTraveled[0]);
//		SmartDashboard.putString("DB/String 2", "enc2 = " + distanceTraveled[1]);
//		switch (autoSelected) {
//		case customAuto:
//			// Put custom auto code here
//			break;
//		case defaultAuto:
//		default:
//			// Put default auto code here
//			break;
//		}
//		//drive.turn(-170, .15);
}

	public void teleopInit(){
		drive.resetDistanceTraveled();
		/*drive.talFR.enableBrakeMode(false);
		drive.talFL.enableBrakeMode(false);
		drive.talBR.enableBrakeMode(false);
		drive.talBL.enableBrakeMode(false); */
		drive.resetGyro();

	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//teleop programs  (names are pretty self-explanatory)
		
		//runs the climber program on the 8 and 9 buttons on the left joystick
		/*climber.checkClimbInput2(joyL.getRawButton(8), joyL.getRawButton(9));
		//runs the shooter program for the left and right joystick triggers
//		shoot.checkShootInput(joyL.getRawButton(1), joyR.getRawButton(1));
		//checks if the control switcher button is pressed
		if (joyR.getRawButton(Pronstants.CONTROL_SWITCH_BUTTON)) {
			//if so, set the boolean for that button to true
			button1 = true;
		}
		else if (joyL.getRawButton(Pronstants.CONTROL_SWITCH_BUTTON)) {
			//otherwise, set that button's boolean to false
			button1 = false;
		}
		//checks if the boolean for the control switcher button is true
		if (button1) {
			//if so, switch the controls
			drive.driveSwitch(joyR.getRawAxis(1), joyL.getRawAxis(1));
		}
		else {
			//if not, keep the controls the same
			drive.joystickDrive(joyR.getRawAxis(1), joyL.getRawAxis(1));
		}
		//gets and displays the distance traveled
		drive.getDistanceTraveled();
		//gets and displays the current Heading for the gyro
 		sensors.ultrasonicOutput();
//		climber.printClimblimVoltage();
	}

	/**
	 * This function is called periodically during test mode
	 */
		drive.joystickDrive(joyR.getRawAxis(0), joyL.getRawAxis(0));
		climber.checkClimbInput(joyR.getRawButton(0), joyL.getRawButton(0));
		shoot.checkShootInput(joyR.getRawButton(1), joyL.getRawButton(1));
	}
	@Override
	public void testPeriodic() {
		//unimportant
	}
}