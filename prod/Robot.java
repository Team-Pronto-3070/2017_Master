 // FRC 2017 Build Season Code

package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.*;	
import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3070.robot.*;
public class Robot extends IterativeRobot implements Pronstants  {
	Joystick joystick;
    TalonSRX talBR, talFR, talFL, talBL, talC1, talC2;
    AnalogGyro Gyro;
    
    Auto auto = new Auto(); // initialize all the objects
	Climber climber = new Climber();
	Timer timer = new Timer();
    Initial init = new Initial(); // we might not need all these
    Drive drive = new Drive();
    Sensors sensors = new Sensors();

	public void robotInit() {
        init.initialize(); // yo dawg, i heard you like initializing objects
	}
	
	public void autonomousInit() {
init.initialize();
		
	}
	
	
	
		
	public void autonomousPeriodic() {
	    auto.AutoC();
	    auto.AutoL();
	    auto.AutoR();

	}
	
	public void testPeriodic() { 
		Gyro.initGyro();
		 	Gyro.reset();
		 	talFR = new TalonSRX(0);
		 		talBR = new TalonSRX(3);
		 		talFL = new TalonSRX(2);
		 		talBL = new TalonSRX(7);
		 		joystick = new Joystick(0);
		 	}

	public void teleopPeriodic() {
		 	drive.Drive();
		 	climber.climb();

	
	}
	
}



