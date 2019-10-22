/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.amhsrobotics;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

  public Robot(){
    super(0.06);
  }

  Talon talonLeft = new Talon(0);
  Talon talonRight = new Talon(1);
  XboxController controller = new XboxController(0);


  @Override
  public void robotInit() {
    System.out.println("Init");
    talonLeft.setInverted(false);
    talonRight.setInverted(true);
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    talonLeft.set(0);
    talonRight.set(0);
  }

  @Override
  public void disabledPeriodic() {

  }


  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

    double left = controller.getRawAxis(5);
    double right = controller.getRawAxis(1);

    double percentSlowdown = controller.getRawAxis(4) + 1;

    double minSlowdown = .7;
    double maxSlowdown = 1;

    percentSlowdown = (percentSlowdown-0)/(2-0) * (maxSlowdown-minSlowdown) +minSlowdown;

    double deadzone = 0.1;

    if(Math.abs(left) > deadzone){
      talonLeft.set(left * percentSlowdown);
    }
    else{
      talonLeft.stopMotor();
    }
    if(Math.abs(right) > deadzone){
      talonRight.set(right* percentSlowdown);
    }
    else{
      talonRight.stopMotor();
    }



  }

  @Override
  public void teleopInit() {


  }


  @Override
  public void teleopPeriodic() {

    //Right Trigger: controller.getX()
    //Left Trigger: controller.getTriggerAxis(GenericHID.Hand.kRight)
    //Left Joystick: controller.getX(GenericHID.Hand.kLeft)


    //Combination of right (forward power) and left (backward power) trigger values for drive power
    double drive =  -((controller.getX()+1) /2) + ((controller.getTriggerAxis(GenericHID.Hand.kRight)+1)/2);


    double radiusTurnGain = .1;
    double pointTurnGain = .5;


    //Right joystick value to adjust turning radius
    double turn = controller.getX(GenericHID.Hand.kLeft);

    double radiusTurn = Math.sqrt(Math.abs(turn)*radiusTurnGain) * Math.signum(turn);

    //Radius of circle to follow
    double radius = Math.pow(cot(radiusTurn*(Math.PI/2)),2) * Math.signum(radiusTurn) * -Math.signum(drive);

    //Avoid divide by 0 errors
    if(radius == 0){
      radius = 0.001;
    }

    ///System.out.println(radius);

    //Turn drive
    double turnDeadzone = 0.05;
    double driveDeadzone = 0.01;
    if(Math.abs(drive) > driveDeadzone && Math.abs(turn) > turnDeadzone){
      double[] wheelSpeeds = wheelSpeedFromRadius(radius,drive);

      talonLeft.set(wheelSpeeds[0]);
      talonRight.set(wheelSpeeds[1]);
    }
    //Drive straight
    else if(Math.abs(drive) > driveDeadzone && Math.abs(turn) < turnDeadzone){
      talonLeft.set(drive);
      talonRight.set(drive);
    }
    //Turn in place
    else if(Math.abs(drive) < driveDeadzone && Math.abs(turn) > turnDeadzone){
      talonLeft.set(turn * pointTurnGain);
      talonRight.set(-turn * pointTurnGain);
    }
    //Brake
    else{
      talonLeft.stopMotor();
      talonRight.stopMotor();
    }

  }



  public double[] wheelSpeedFromRadius(double r, double d) {
    double trackWidth = 19;

    double angularVelocity = d / r;

    double lSpeed = angularVelocity * (r - (trackWidth / 2));
    double rSpeed = angularVelocity * (r + (trackWidth / 2));



    if(Math.abs(lSpeed) < Math.abs(rSpeed)){
      double speedRatio = lSpeed/rSpeed;
      rSpeed = d;
      lSpeed = d * speedRatio;
    }
    else{
      double speedRatio = rSpeed/lSpeed;
      rSpeed = d * speedRatio;
      lSpeed = d;
    }

    return new double[]{lSpeed,rSpeed};
  }


  private CheesyDriveHelper driveHelper = new CheesyDriveHelper();
  @Override
  public void testPeriodic() {

    double throttle =  -((controller.getX()+1) /2) + ((controller.getTriggerAxis(GenericHID.Hand.kRight)+1)/2);
    double wheel = controller.getX(GenericHID.Hand.kLeft);

    if(!controller.getXButton()){
      throttle = throttle/4;
    }

    double[] drivePwm = driveHelper.cheesyDrive(throttle,wheel,Math.abs(throttle) < 0.01);

    if(drivePwm[0] == 0 && drivePwm[1] == 0){
      talonLeft.stopMotor();
      talonRight.stopMotor();
    }
    else{
      talonLeft.set(drivePwm[0]);
      talonRight.set(drivePwm[1]);
    }
  }

  private double cot(double x){
    return (Math.toRadians(Math.cos(x))/Math.toRadians(Math.sin(x)));
  }
}
