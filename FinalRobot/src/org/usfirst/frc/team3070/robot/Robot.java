package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	Shooter shoot;
	ProntoGyro gyro;
	Joystick joyL, joyR;
	SendableChooser<Pronstants.AutoMode> autoChooser;
	Pronstants.AutoMode startMode;
	
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
		auto = new Auto();
		climber = new Climb();
		shoot = new Shooter();
		gyro = new ProntoGyro();
		
		autoChooser = new SendableChooser<Pronstants.AutoMode>();
		autoChooser.addDefault("None",Pronstants.AutoMode.AUTO_MODE_NONE);
		autoChooser.addObject("Center -> Center", Pronstants.AutoMode.AUTO_MODE_CENTER_CENTER);
		autoChooser.addObject("Center -> Right", Pronstants.AutoMode.AUTO_MODE_CENTER_RIGHT);
		autoChooser.addObject("Center -> Left", Pronstants.AutoMode.AUTO_MODE_CENTER_LEFT);
		autoChooser.addObject("Left -> Left", Pronstants.AutoMode.AUTO_MODE_LEFT_LEFT);
		autoChooser.addObject("Right -> Right", Pronstants.AutoMode.AUTO_MODE_RIGHT_RIGHT);
		
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
		gyro.reset();
		drive.talFR.enableBrakeMode(true);
		drive.talFL.enableBrakeMode(true);
		drive.talBR.enableBrakeMode(true);
		drive.talBL.enableBrakeMode(true);
		drive.resetGyro();
		//gyro.reset();
		startMode = autoChooser.getSelected();
		
		
		drive.resetDistanceTraveled();

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
		
		switch(startMode) {
		case AUTO_MODE_NONE:
			drive.drive(0, 0);
		case AUTO_MODE_CENTER_CENTER:
			auto.autoC();
		case AUTO_MODE_RIGHT_RIGHT:
			auto.autoOutside(Pronstants.AUTO_SIDE_RIGHT);
		case AUTO_MODE_LEFT_LEFT:
			auto.autoOutside(Pronstants.AUTO_SIDE_LEFT);
		case AUTO_MODE_CENTER_RIGHT:
			auto.autoOutsideCenter(Pronstants.AUTO_SIDE_RIGHT);
		case AUTO_MODE_CENTER_LEFT:
			auto.autoOutsideCenter(Pronstants.AUTO_SIDE_LEFT);
		}

//		}
//		else {
//			//if none of the previous cases are true, have the robot stay still
//			drive.drive(0, 0);
//		}
		
		autoProg = (binarySearch(autoSelect, true));
		
		switch(autoCase) {
		case 1: // Drive straight with first value if needed
			if(drive.getDistanceTraveled()[2] < Pronstants.FIRST_AUTO_STRAIGHT_DISTANCE[autoProg]) {
				drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			} else {
				autoCase = 2;
				drive.resetDistanceTraveled();
				drive.drive(0, 0);
			}
			break;
		case 2: // Turn 60 degrees right or left, depending on auto selected, or if not needed don't turn
			if(!drive.turn(Pronstants.AUTO_TURN[autoProg], Pronstants.AUTO_MAX_TURN_SPEED)) {
				break;
			} else {
				autoCase = 3;
				drive.resetDistanceTraveled();
				drive.drive(0, 0);
			}
			break;
		case 3: // Drive straight with second value if needed
			if(drive.getDistanceTraveled()[2] < Pronstants.SECOND_AUTO_STRAIGHT_DISTANCE[autoProg]) {
				drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			} else {
				autoCase = 4;
				drive.resetDistanceTraveled();
				drive.drive(0,  0);
			}
			break;
		case 4: // Code for loading gear using vision
			drive.drive(0, 0);
			break;
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
	}


	public void teleopInit(){
		drive.resetDistanceTraveled();
		drive.resetGyro();
		drive.talFR.enableBrakeMode(false);
		drive.talFL.enableBrakeMode(false);
		drive.talBR.enableBrakeMode(false);
		drive.talBL.enableBrakeMode(false);
	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//teleop programs  (names are pretty self-explanatory)
		//runs the climber program on the 8 and 9 buttons on the left joystick
		//climber.checkClimbInput2(joyL.getRawButton(8), joyL.getRawButton(9));
		//runs the shooter program for the left and right joystick triggers
//		shoot.checkShootInput(joyL.getRawButton(1), joyR.getRawButton(1));
		//checks if the control switcher button is pressed
		/*if (joyR.getRawButton(Pronstants.CONTROL_SWITCH_BUTTON)) {
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
		gyro.calculateHeading();
//		climber.printClimblimVoltage();
 * 
 * */
		drive.joystickDrive(-joyR.getRawAxis(1), -joyL.getRawAxis(1));
		if(joyR.getRawButton(2)) {
			climber.moveUp();
		} else {
			climber.stop();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		//unimportant
	}
}