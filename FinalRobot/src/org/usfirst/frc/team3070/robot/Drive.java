package org.usfirst.frc.team3070.robot;
import com.ctre.CANTalon;
import org.usfirst.frc.team3070.robot.Pronstants;
import edu.wpi.first.wpilibj.AnalogGyro;

public class Drive {
	static CANTalon talFR, talFL, talBR, talBL;
	static AnalogGyro gyro;
	public Drive()
	{
		//defines the talon variables
		talFR = new CANTalon(Pronstants.TALON_FRONT_RIGHT_PORT);
		talFL = new CANTalon(Pronstants.TALON_FRONT_lEFT_PORT);
		talBR = new CANTalon(Pronstants.TALON_BACK_RIGHT_PORT);
		talBL = new CANTalon(Pronstants.TALON_BACK_LEFT_PORT);
		//sets a voltage ramp rate on the talons
		talFR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talFL.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBR.setVoltageRampRate(Pronstants.RAMP_RATE);
		talBL.setVoltageRampRate(Pronstants.RAMP_RATE);
		//sets a current limit on the talons
		talFR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talFL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBR.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		talBL.setCurrentLimit(Pronstants.DRIVE_CURRENT_LIMIT);
		//sets feedback device to encoders
		talFR.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talFL.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
			}
	public void joystickDrive(double joyR, double joyL) {
		double speedR, speedL;
		if (joyR > Pronstants.DEAD_ZONE || joyR < -Pronstants.DEAD_ZONE) {
			speedR = joyR;
		}
		else {
			speedR = 0;
		}
		if (joyL > Pronstants.DEAD_ZONE || joyL < -Pronstants.DEAD_ZONE) {
			speedL = joyL;
		}
		else {
			speedL = 0;
		}
		drive(speedR, speedL);
	}
	public void drive(double right, double left)
	{
		talFR.set(right);
		talBR.set(right);
		talFL.set(left);
		talBL.set(left);
	}
	public double[] getDistanceTraveled()
	{
		double ar[] = new double[2];
		ar[0] = talFR.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		ar[1] = talFL.getEncPosition() / Pronstants.TICK_COEFFICIENT;
		return ar;
	}
	public void resetDistanceTraveled() {
		talFR.setEncPosition(0);
		talFL.setEncPosition(0);
	}
	  public void turnRight(double angle, double speed) {
		    gyro.reset();
		    if (gyro.getAngle() < angle) {  //consider implementing some compensation for the robot taking a bit to stop 
		      talFR.set(-speed);
		      talBR.set(-speed);
		      talFL.set(speed);
		      talBL.set(speed);
		    }
		      talFR.set(0);
		      talBR.set(0);
		      talFL.set(0);
		      talBL.set(0);
		  }
	  public void turnLeft(double angle, double speed) {  
		    gyro.reset();
		    if (Math.abs(gyro.getAngle()) < angle) {  //consider implementing some compensation for the robot taking a bit to stop 
		      talFR.set(speed);
		      talBR.set(speed);
		      talFL.set(-speed);
		      talBL.set(-speed);
		    }
		      talFR.set(0);
		      talBR.set(0);
		      talFL.set(0);
		      talBL.set(0);
		  }
}