package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Timer;

/* methods: 
public void run(int mode, boolean shoot) - Runs the autonomous based on a path and if we want to shoot or not
public double getSelected(boolean button1, boolean button2, boolean button3) - Selects the autonomous based on 3 buttons
*/

public class Auto {
	// Initializes classes
	private Drive drive;
	private Shooter shooter;
	private Timer timer;
	private Vision frcVision;
	
	// Constructs the class
	public Auto(Vision frcVision, Drive drive, Shooter shooter) {
		this.drive = drive;
		this.shooter = shooter;
		this.frcVision = frcVision;
		timer = new Timer();
	}

	// Turning distances for the different autonomous programs
	double firstTurn[] = {0, -60, 60};
	
	// Driving distances for the different autonomous programs
						//center,right,left
	double firstDist[] = {5.755, 7.27, 7.27, 5, 5};
	double secondDist[] = {0, 3.2, 3.2, 5, 5};
	

	// Keeps track of what part of the autonomous code the robot is on
	AutoState step = AutoState.FIRST_DISTANCE;
	
	// Creates a double to keep track of the distance traveled
	double distTraveled = 0;
	double shootDist = 0.984;
	double shootTurn[] = {-13, 13};

	/*
	 * if (vision) state(stepV)
	 * else { state(step)
	 */
	
	// Runs the autonomous based on a path and if we want to shoot or not
	public void run(int mode, boolean shoot, boolean vision) {
		// Sets distTraveled to the distance traveled by the robot in feet
		distTraveled = drive.getDistanceTraveled()[2];
		// Creates a state engine for the value of step
		// Note to Pronto devs: state engines are a fun way to make your code look nice.
		// Y'all should use them more often
		switch(step) {
		case FIRST_DISTANCE:
			// Checks if the robot has traveled the first distance or further
			if (distTraveled >= firstDist[mode]) {
				// If so, prepare the robot for the next step
				nextStep(AutoState.FIRST_BREAK);
			}
			
			else {
				// Otherwise, drive the robot forward
				drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			}
			
			// Skip other cases
			break;

		case FIRST_BREAK:
			// Wait one tenth of a second, then go to the next step
			if (timerWait(0.1)) {
				step = AutoState.FIRST_TURN;
			}
			
			// Skip other cases
			break;
		

		case FIRST_TURN:
			// Starts turning the robot, then checks if the robot is done turning
			if (drive.turn(firstTurn[mode], Pronstants.AUTO_TURN_SPEED)) {
				// If so, prepare the robot for the next step
				step = AutoState.SECOND_BREAK;
			}
			
			// Skip other cases
			break;
		

		case SECOND_BREAK:
			// Checks if the robot has waited one tenth of a second
			if (timerWait(0.1)) {
				// Checks if the robot should run vision
				if (vision) {
					// If so, go to the step where vision is run
					nextStep(AutoState.VISION);
				}
				else {
					// Otherwise, skip vision and go to the step for "SECOND_DISTANCE"
					nextStep(AutoState.SECOND_DISTANCE);
				}
			}
			
			// Skip other cases
			break;
		
		case VISION:
			// Checks if the vision centering is done
			if (!timerWait(3)) {
				if (visionCenterOnPeg()) {
					// If so, go to the next step
					step = AutoState.THIRD_BREAK;
				}
			}
			else {
				step = AutoState.THIRD_BREAK;
			}
			
			// Skip Other Cases
			break;
		
		case THIRD_BREAK:
			// Wait a tenth of a second, go to the next step
			if (timerWait(0.1)) {
				step = AutoState.SECOND_DISTANCE;
			}
			
			// Skip other cases
			break;
		
		case SECOND_DISTANCE:
			// Checks if the robot has traveled the second distance or further
			if (distTraveled >= secondDist[mode]) {
				// If so, prepare the robot for the next step
				nextStep(AutoState.THIRD_BREAK);
			}
			
			else {
			// Otherwise, drive the robot straight
			drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED - .15);
			}
			
			// Skip other cases
			break;
		
		case FOURTH_BREAK:
			// Checks if the robot isn't supposed to shoot
			if(!shoot) {
				// If so, wait for autonomous to end
				step = AutoState.WAIT;
			}
			
			// Otherwise, wait one tenth of a second and start the shooting sequence of events
			else if (timerWait(0.1)) {
				nextStep(AutoState.SHOOT_DISTANCE);
			}
			
			// Skip other cases
			break;			
			
		case SHOOT_DISTANCE:
			shooter.runShooter();
			if (distTraveled >= shootDist) {
				nextStep(AutoState.FIFTH_BREAK);
			}
			drive.driveRobotStraight(Pronstants.AUTO_DRIVE_SPEED);
			break;
		
		case FIFTH_BREAK:
			shooter.runShooter();
			if (timerWait(0.1)) {
				nextStep(AutoState.SHOOT_TURN);
			}
			break;
			
		case SHOOT_TURN:
			shooter.runShooter();
			if (drive.turn(shootTurn[mode], Pronstants.AUTO_DRIVE_SPEED)) {
				nextStep(AutoState.SIXTH_BREAK);
			}
			break;
			
		case SIXTH_BREAK:
			shooter.runShooter();
			if (timerWait(0.1)) {
				nextStep(AutoState.SHOOT_BALLS);
			}
			break;
			
		case SHOOT_BALLS:
			shooter.runShooter();
			shooter.runHopper();
			break;
			
		case WAIT:
			// Waits for autonomous to end with the robot standing still
			drive.drive(0, 0);
			break;
		}
	}
	
	enum AutoState{
		FIRST_DISTANCE,
		FIRST_BREAK,
		FIRST_TURN,
		SECOND_BREAK,
		SECOND_DISTANCE,
		THIRD_BREAK,
		VISION,
		FOURTH_BREAK,
		SHOOT_DISTANCE,
		FIFTH_BREAK,
		SHOOT_TURN,
		SIXTH_BREAK,
		SHOOT_BALLS,
		WAIT
	};
	
	// Runs the functions needed to go to the next step
	public void nextStep(AutoState next) {
		// Tells the robot to go to the next step
		step = next;
		
		// Stop the robot
		drive.drive(0, 0);
		
		// Resets the gyro
		drive.resetGyro();
		
		// Resets the distance traveled by the drive talons
		drive.resetDistanceTraveled();
		
		// Reset and start the timer
		timer.reset();
		timer.start();
	}
	
	// Selects the autonomous based on 3 buttons
	public int getSelected(boolean button1, boolean button2, boolean button3, boolean button4) {
		// If the first button is pressed, return the integer representing the center autonomous code4
		if (button1) {
			return Pronstants.AUTO_CENTER;
		}
		
		// Otherwise, if the second button is pressed,
		// return the integer representing the right autonomous code
		else if (!button4 &&button2) {
			return Pronstants.AUTO_RIGHT;
		}
		
		// Otherwise, if the third button is pressed,
		// return the integer representing the left autonomous code
		else if (!button4 && button3) {
			return Pronstants.AUTO_LEFT;
		}
		else if (button2 && button4) {
			return Pronstants.AUTO_RIGHT_V;
		}
		else if (button3 && button4) {
			return Pronstants.AUTO_RIGHT_V;
		}
		
		// If none of the above cases are true, return the integer
		// representing the center autonomous
		// This is here as a failsafe since no situation could be thought of
		// where you wouldn't want 5 extra points
		else {
			return Pronstants.AUTO_CENTER;
		}
	}
	
	public boolean timerWait(double seconds) {
		if (timer.get() >= seconds) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean visionCenterOnPeg() {
		double lineLocation = frcVision.getLineLocationX();
		double val = 0.1;
		if(lineLocation == 0 )
		{
			drive.drive(0, 0);
			return false;
		}
		else if(lineLocation > 0.50 + Pronstants.VISION_DEADBAND){
			drive.drive(-val, val);
			return false;
		}
		else if(lineLocation < 0.50 - Pronstants.VISION_DEADBAND){
			drive.drive(val, -val);
			return false;
		}
		else{
			drive.drive(0, 0);
			return true;
		}
	}
	
	public void resetState() {
		step = AutoState.FIRST_DISTANCE;
	}
}