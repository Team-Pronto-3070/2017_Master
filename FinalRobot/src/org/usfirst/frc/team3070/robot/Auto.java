package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 
/* methods: 
 * public void autoC() - drive center
 * public void autoOutside(int side) - drive from outer start position to outer lift
 * public void autoOutsideCenter(int side) - drive from outer start position to center
*/

public class Auto {
// initializes classes
private Drive drive = new Drive();
private Shooter shooter = new Shooter();
private ProntoGyro gyro = new ProntoGyro();

public Auto(Drive drive) {
    // Auto constructor
    this.drive = drive;
}

// initial distance
double[] rotations = drive.getDistanceTraveled();
double initDist = 0; 
// Difference in distance
double diffDist; 
 
// initial heading
double initHeading = gyro.getOffset();

// Flags for if we're turning
boolean firstTurning = false;
boolean secondTurning = false;

// Turning distance
double firstTurn = 0;
double secondTurn = 0;

// When it's time to shoot
boolean robotShoot = false;

// Things done during Autonomous
public void autoSkeleton() {
    autoC();
}

public void autoC() {
    // autonomous code for the center to the center gearloading station
    // Drives straight for 5 meters, then stops

    // updates distance
    rotations = drive.getDistanceTraveled(); 
    // difference in distance
    diffDist = rotations[2] - initDist; 

    // If the robot has not gone 5 feet, drive straight forward
    if (diffDist < 7.77 - Pronstants.DISTANCE_OFFSET) {
        drive.driveRobotStraight();
    }
    // If the robot has gone 5 feet, let vision take over
    else {
        // TODO: implement vision here
        drive.drive(0, 0);
    }
}

static boolean turnStarted = false;

public void autoOutside(int side) {
    // autonomous code for going to an outside gearloader
    // from the same side starting position

    // updates distance
    rotations = drive.getDistanceTraveled(); 
    // difference in distance
    diffDist = rotations[2] - initDist; 
    // difference in angle
    //double diffAngle = gyro.getHeading()- initHeading; 

    // creates a case statement for the value of "side"
    switch(side) {
    // If side = value for right
    case Pronstants.AUTO_SIDE_LEFT: 
        // Set firstTurn to 60
        firstTurn = 60;
        break;
    // If side = value for right
    case Pronstants.AUTO_SIDE_RIGHT: 
        // Set firstTurn to -60
        firstTurn = -60;
        break;
    default:
        break;
    }

    // If the robot has not gone 5 feet, drive straight forward
    if (diffDist < 5 && !firstTurning) {
        drive.driveRobotStraight();
        robotShoot = false;
    }
    // If the robot has gone 5 feet and the first turn is not finished\
    if(!turnStarted){
    	drive.resetGyro();
    }
    else if (!drive.turn(initHeading + firstTurn, Pronstants.AUTO_TURN_SPEED)) {
        // if so, turn it a number of degrees equal to "firstTurn",
        // reset the distance traveled, and set robotShoot to false
        drive.resetDistanceTraveled();
        robotShoot = false;
        firstTurning = true;
    }
//    // checks if the first turn has finished
//    else if (drive.turn(initHeading + firstTurn, Pronstants.AUTO_TURN_SPEED)) {
//        // if so, start vision here and set robotShoot to true
//        drive.drive(0, 0);
//        robotShoot = true;
//        // TODO: finish vision and implement here
//    }

    // checks if robotShoot is true
    if (robotShoot) {
        // if so, start shooting
        shooter.shoot();
    }
    else {
        // If not true, don't shoot
        shooter.stopShooter();
    }
}

public void autoOutsideCenter(int side) {
    // autonomous code for a non-center side
    //to the center gearloading station

    // updates distance
    rotations = drive.getDistanceTraveled(); 
    // difference in distance
    diffDist = rotations[2] - initDist; 
    // difference in angle
    double diffAngle = gyro.getHeading()-initHeading; 

    // creates a case statement for the value of "side"
    switch(side) {
        // checks if side = value for left
        case Pronstants.AUTO_SIDE_LEFT:
            // if so, set firstTurn to 90 and secondTurn to -90
            firstTurn = 90;
            secondTurn = -90;
            break;
        // checks if side = value for right
        case Pronstants.AUTO_SIDE_RIGHT:
            // if so, set firstTurn to -90 and secondTurn to 90
            firstTurn = -90;
            secondTurn = 90;
            break;
        default:
            break;
    }

    // checks if the robot has not gone 5 feet
    if (diffDist < 5 && !firstTurning) {
        //if so, drive straight forward
        drive.driveRobotStraight();
    }
    //checks if the robot has gone 5 feet and the first is not finished
    else if (drive.turn(initHeading + firstTurn, Pronstants.AUTO_DRIVE_SPEED) == false && !secondTurning) {
        //if so, turn it an amount of degrees equal to firstTurn
        // and reset the distance traveled
        drive.turn(initHeading + firstTurn, Pronstants.AUTO_DRIVE_SPEED);
        drive.resetDistanceTraveled();
        firstTurning = true;
    }
    //checks if the first turn has finished
    //and the robot has not traveled an additional 2 feet
    else if (rotations[2] < 2 && !secondTurning) {
        //if so, drive the robot straight
        initHeading += firstTurn;
        drive.driveRobotStraight();
    }
    //checks if the robot has traveled an additional 2 feet
    //and the second has not finished
    else if (drive.turn(initHeading + secondTurn, Pronstants.AUTO_DRIVE_SPEED) == false) {
        //if so, turn the robot to face the center gearloader (aka an amount
        // of degrees equal to secondTurn)
        drive.turn(initHeading + secondTurn, Pronstants.AUTO_DRIVE_SPEED);
        drive.resetDistanceTraveled();
        secondTurning = true;
    }
    //checks if the second turn has finished
    else if (drive.turn(initHeading + secondTurn, Pronstants.AUTO_DRIVE_SPEED) == true) {
        drive.drive(0, 0);
        //TODO: implement vision here
    }
}
}