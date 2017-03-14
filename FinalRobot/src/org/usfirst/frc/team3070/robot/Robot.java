package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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
	Joystick joyL, joyR;
	Shooter shoot;
	ProntoGyro gyro;
	SendableChooser<Pronstants.AutoMode> autoChooser;
	Pronstants.AutoMode startMode;
	Thread checkSensors;
	
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
	boolean checkSwitch = false;
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
		//puts the auto program chooser on the dashboard
		autoChooser = new SendableChooser<Pronstants.AutoMode>();
		autoChooser.addDefault("None",Pronstants.AutoMode.AUTO_MODE_NONE);
		autoChooser.addObject("Center -> Center", Pronstants.AutoMode.AUTO_MODE_CENTER_CENTER);
		autoChooser.addObject("Center -> Right", Pronstants.AutoMode.AUTO_MODE_CENTER_RIGHT);
		autoChooser.addObject("Center -> Left", Pronstants.AutoMode.AUTO_MODE_CENTER_LEFT);
		autoChooser.addObject("Left -> Left", Pronstants.AutoMode.AUTO_MODE_LEFT_LEFT);
		autoChooser.addObject("Right -> Right", Pronstants.AutoMode.AUTO_MODE_RIGHT_RIGHT);
		//puts sensor values on the dashboard while disabled
		checkSensors = new Thread(() -> {
			while(!Thread.interrupted())
			{
				SmartDashboard.putString("EncPos/FR", ""+drive.talFR.getEncPosition());
				SmartDashboard.putString("EncPos/BL",	""+drive.talBL.getEncPosition());
				SmartDashboard.putString("I/FR", "FR I: "+drive.talFR.getOutputCurrent());
				SmartDashboard.putString("I/FL", "FL I: "+drive.talFL.getOutputCurrent());
				SmartDashboard.putString("I/BR", "BR I: "+drive.talBR.getOutputCurrent());
				SmartDashboard.putString("I/BL", "BL I: "+drive.talBL.getOutputCurrent());	
				if (climber.climbLim1.getVoltage() > .4) {
					SmartDashboard.putBoolean("Climb/C1", true);
				} else {
					SmartDashboard.putBoolean("Climb/C1", false);
				}
				if (climber.climbLim2.getVoltage() > .4) {
					SmartDashboard.putBoolean("Climb/C2", true);
				} else {
					SmartDashboard.putBoolean("Climb/C2", false);
				}
				SmartDashboard.putNumber("Gyro/gyro", gyro.getOffsetHeading());
				Timer.delay(.1);
			}
			
		});
		checkSensors.setDaemon(true);
		checkSensors.start();
//		grip = new vision();
	//vision code
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(Pronstants.IMG_WIDTH, Pronstants.IMG_HEIGHT);
//visionThread = new VisionThread (camera, grip,pipline ->{
//			System.out.println(grip.findBlobsOutput().size());
//		});
//visionThread.start();
		
		
	}
	@Override
	public void autonomousInit() {
		//resets the distance traveled
		drive.resetDistanceTraveled();
		// resets the gyro
		drive.talFR.enableBrakeMode(true);
		drive.talFL.enableBrakeMode(true);
		drive.talBR.enableBrakeMode(true);
		drive.talBL.enableBrakeMode(true);
		drive.talFR.setVoltageRampRate(Pronstants.AUTO_RAMP_RATE);
		drive.talFL.setVoltageRampRate(Pronstants.AUTO_RAMP_RATE);
		drive.talBR.setVoltageRampRate(Pronstants.AUTO_RAMP_RATE);
		drive.talBL.setVoltageRampRate(Pronstants.AUTO_RAMP_RATE);
		drive.resetGyro();
		
		//startMode = autoChooser.getSelected();
		
		
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

	}


	public void teleopInit(){
		drive.resetDistanceTraveled();
		drive.resetGyro();
		drive.talFR.setVoltageRampRate(Pronstants.RAMP_RATE);
		drive.talFL.setVoltageRampRate(Pronstants.RAMP_RATE);
		drive.talBR.setVoltageRampRate(Pronstants.RAMP_RATE);
		drive.talBL.setVoltageRampRate(Pronstants.RAMP_RATE);
		drive.talFR.enableBrakeMode(false);
		drive.talFL.enableBrakeMode(false);
		drive.talBR.enableBrakeMode(false);
		drive.talBL.enableBrakeMode(false);
		checkSwitch = false;
	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//teleop programs  (names are pretty self-explanatory)
		drive.joystickDrive(joyR.getRawAxis(1), joyL.getRawAxis(1), joyR.getRawAxis(2));
		climber.checkClimbInput(joyR.getRawButton(2), joyL.getRawButton(2));
//		shoot.checkShootInput(joyR.getRawButton(1), joyL.getRawButton(1));
	}
}