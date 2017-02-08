package org.usfirst.frc.team3070.robot;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3070.robot.*;

public class Climber extends Robot implements Pronstants {  
	
	public void climb() {
	if (joystick.getRawButton(4)){
		talC1.set(.3);
		talC2.set(.3);
	}
}
}

