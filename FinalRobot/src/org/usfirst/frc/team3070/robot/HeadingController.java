package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import org.usfirst.frc.team3070.robot.Pronstants;

/* methods:
public HeadingController(ProntoGyro gyro) - constructs the class
public void start() - starts the PID controller
public double getAdjFactor() - returns the adjustment factor for driving
public void pidWrite(double output) - auto-generated method from PIDOutput or PIDSource
public void setPIDSourceType(PIDSourceType pidSource) - auto-generated method from PIDOutput or PIDSource
public PIDSourceType getPIDSourceType() - auto-generated method from PIDSource or PIDOutput
public double pidGet() - auto-generated method from PIDSource or PIDOutput

 */

public class HeadingController implements PIDSource, PIDOutput {
	// Defines the gyro
	ProntoGyro gyro;
	// Defines wpilib PID classes
	PIDController controller;
	PIDSource pidsource;
	PIDOutput pidout;
	// Creates a double equal to pidWrite
	double writeVar;
	
	public HeadingController(ProntoGyro gyro) {
		// Constructs the class
		
		// Tells the class which gyro to use
		this.gyro = gyro;
		
		// Creates a new PID controller with the given inputs
		controller = new PIDController(Pronstants.Kp, Pronstants.Ki, Pronstants.Kd, this, this);
	}
	
	public void start() {
		// starts the PID controller
		
		// Sets P, I, and D equal to variables on the smartDash
		double tempP = SmartDashboard.getNumber("DB/Slider 1", 0);
		double tempI = SmartDashboard.getNumber("DB/Slider 2", 0);
		double tempD = SmartDashboard.getNumber("DB/Slider 3", 0);
		controller.setPID(tempP, tempI, tempD);
		// Resets the controller
		controller.reset();
		
		// Starts the controller
		controller.enable();
	}
	
	public double getAdjFactor() {
		// Returns the variable given from pidWrite
		return writeVar;
	}
	
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		writeVar = output;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return gyro.getOffsetHeading();
	}

}
