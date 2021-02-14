// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot;
 
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
 
 
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //set up Joystick(s)
  Joystick gamepad1 = new Joystick(0);
  
  //set up Sparks
  Spark leftDriveCIM = new Spark(0); //left drive CIM motors CTRL-->left joy
  Spark rightDriveCIM = new Spark(1);//right drive CIM motors CTRL-->rightjoy
  Spark intakePG27 = new Spark(2); //ball intake PG27 motor CTRL-->toggled by A button
  Spark launcherAM775 = new Spark(3); //ball launcher is two AndyMark 775 motors on Y-connector CTRL-->right bumper
  Spark hopperCIM = new Spark(4); //ball hopper is two CIM/Mini-CIM motors on Y-connector CTRL-->right bumper
 
  //drive motor variables
  int drivePolarity = 1; //1 or -1 to reverse spinning direction
  int leftSneakEnabled = 0; //sneak mode enabled? Controls whether robot is driving at sneak speed; set to 0 by default
  int rightSneakEnabled = 0;
 
  double driveMaxSpeed = 0.5; //0 - 100 percent  (e.g. driveSpeed = 0.5 is 50 percent speed.)
  double driveSneakSpeed = 0.5; //0 - 100 percent of max speed
  double leftDriveCIMSpeed; //the final speed of the motors after all of the speed modifications
  double rightDriveCIMSpeed;
 
  //ball intake variables
  boolean intakePG27Enabled = true; //running by default
 
  int intakePG27Polarity = 1; //1 or -1 to reverse spinning direction

  double intakePG27MaxSpeed;  //0 - 100 percent  (e.g. driveSpeed = 0.5 is 50 percent speed.)
  double intakePG27Speed; //final speed of the motors after all of the speed modifications
 
  //ball launcher variables
  int launcherAM775Polarity = 1; //1 or -1 to reverse spinning direction
 
  double launcherAM775MaxSpeed = 0.5; //0 - 100 percent of max speed
  double launcherAM775Speed; //final speed of the motors after all of the speed modifications
 
  //ball hopper variables
  int hopperCIMPolarity = 1; //1 or -1 to reverse spinning direction
 
  double hopperCIMMaxSpeed = 0.5; //0 - 100 percent of max speed
  double hopperCIMSpeed; //final speed of the motors after all of the speed modifications
  
  @Override
  public void robotInit() {
  }
 
  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}
 
  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
  }
 
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }
 
  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}
 
  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
      //get y axis data
    double gamepad1LeftYAxis = -gamepad1.getRawAxis(1);
    double gamepad1RightYAxis = -gamepad1.getRawAxis(5);
 
  //System.out.println("Left Axis: " + gamepad1LeftYAxis + " Right Axis: " + gamepad1RightYAxis);
 
    //sneak mode
    if (gamepad1.getRawButtonPressed(9)) //left joystick button
    {
        leftSneakEnabled = 1;
    }
    else
    { leftSneakEnabled = 0; }
 
    if (gamepad1.getRawButtonPressed(10)) //right joystick button
    {
        rightSneakEnabled = 1;
    }
    else
    { rightSneakEnabled = 0; }
 
    //toggle ball intake
    if (gamepad1.getRawButtonPressed(1)) //toggled by A button
    {
      intakePG27Enabled = !intakePG27Enabled;
    }
 
    //control ball launcher
      launcherAM775Speed = launcherAM775Polarity * launcherAM775MaxSpeed;
 
    //control ball hopper
    if (gamepad1.getRawButton(8)) //controlled by right bumper
    {
      hopperCIMSpeed = hopperCIMPolarity * hopperCIMMaxSpeed;
    }
    else { hopperCIMSpeed = 0; }
    
    //calculate and set speed for drive motors (with polarity switch, sneak, and speed modifier)
    leftDriveCIMSpeed = gamepad1LeftYAxis * -drivePolarity * driveMaxSpeed * Math.pow(driveSneakSpeed, leftSneakEnabled);
    rightDriveCIMSpeed = gamepad1RightYAxis * drivePolarity * driveMaxSpeed * Math.pow(driveSneakSpeed, rightSneakEnabled);
    leftDriveCIM.set(leftDriveCIMSpeed);//move drive motors
    rightDriveCIM.set(rightDriveCIMSpeed);
 
    //calculate and set ball intake speed
    intakePG27Speed = intakePG27Polarity * Math.pow(intakePG27MaxSpeed, intakePG27Enabled ? 1 : 0); 
    intakePG27.set(intakePG27Speed); //move ball intake motor
 
    //calculate and set ball launcher speed
    launcherAM775.set(launcherAM775Speed);
 
    //calculate and set ball hopper speed
    hopperCIM.set(hopperCIMSpeed);
 
  }
 
  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}
 
  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}
 
  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}
 
  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
 
 
 
 

