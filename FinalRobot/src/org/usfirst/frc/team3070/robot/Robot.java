package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	Joystick joystick;
	Shooter shoot;
	static double startHeading;
	Timer timer;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Initializes robot
		//variable init

		joystick = new Joystick(JOYSTICK_PORT);
		//Initializes Pronto Classes
		drive = new Drive();
		auto = new Auto(drive);
		climber = new Climb();
		shoot = new Shooter();
		//Sets encoders to the feedback device for Talons
		//TODO see which talons have encoders
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
//		drive.setStraightValue();
//		drive.resetDistanceTraveled();
//		SmartDashboard.putString("DB/String 0", "Test thing");
		shoot.shooterStop();
//		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		/*if(timer.get()<15){
			shoot.shooterGo();
		}else{
			shoot.shooterStop();
		}*/
		//what happens during autonomous (stays during autonomous)
		//auto.autoSkeleton();
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
		/*climber.checkClimbInput(joystick.getRawButton(1), joystick.getRawButton(2));
		shoot.checkShootInput(joystick.getRawButton(3), joystick.getRawButton(4));
		drive.joystickDrive(joystick.getRawAxis(5), joystick.getRawAxis(1));*/
		if(joystick.getRawButton(1)){
			shoot.shooterGo();
		} else if(joystick.getRawButton(2)){
			shoot.shooterGoNega();
		}else{
			shoot.shooterStop();
		}
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

