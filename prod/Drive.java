package org.usfirst.frc.team3070.robot;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team3070.robot.*;
public class Drive extends Robot implements Pronstants {
public void Drive() {
	talFR.set(joystick.getRawAxis(5));
		talFL.set(-joystick.getRawAxis(5));
		talBR.set(-joystick.getRawAxis(1));
		talBL.set(joystick.getRawAxis(1));
}

}
