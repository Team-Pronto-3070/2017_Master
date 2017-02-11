package org.usfirst.frc.team3070.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements Pronstants {
	Drive drive;
	Auto auto;
	Climb climber;
	CANTalon talFR, talFL, talBR, talBL, talC1, talC2;
	Joystick joystick;
	public static double startHeading;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Initializes robot
		//variable init
		talFR = new CANTalon(TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(TALON_FRONT_lEFT_PORT);
		talBR = new CANTalon(TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(TALON_BACK_LEFT_PORT);
		talC1 = new CANTalon(TALON_CLIMBER_1_PORT);
		talC2 = new CANTalon(TALON_CLIMBER_2_PORT);
		joystick = new Joystick(0);
		//Initializes Pronto Classes
		drive = new Drive();
		auto = new Auto();
		climber = new Climb();
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
		startHeading = Sensors.imu.getHeading();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//what happens during autonomous (stays during autonomous)
		auto.skeleton();
		//TODO Placeholder for vision
		//sensors.visionAuto();
		
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
		//teleop programs
		climber.checkClimbInput(joystick.getRawButton(1), joystick.getRawButton(2));
		drive.joystickDrive();
		//sensors.visionTeleop();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		//unimportant
	}
}

