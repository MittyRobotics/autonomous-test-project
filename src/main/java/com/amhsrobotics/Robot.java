/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.amhsrobotics;

import com.amhsrobotics.commands.TankDrive;
import com.amhsrobotics.constants.DriveConstants;
import com.amhsrobotics.subsystems.DriveTrain;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;

import java.awt.geom.Point2D;

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

  }

  @Override
  public void teleopInit() {


  }


  double driveDeadzone = 0.01;
  double turnDeadzone = 0.05;

  @Override
  public void teleopPeriodic() {
    //System.out.println(controller.getTriggerAxis(GenericHID.Hand.kRight) );

    double t = Math.sin(controller.getX(GenericHID.Hand.kLeft)) /2;
    double d = -((controller.getX()+1) /8) + ((controller.getTriggerAxis(GenericHID.Hand.kRight)+1)/8);
    d = d * (1-Math.abs((t/1.5)));

    if(!controller.getXButton()){
      d = d*2;
    }


    if(Math.abs(d) > driveDeadzone && Math.abs(t) > turnDeadzone){
      double r = Math.pow(cot(t*(Math.PI/2)),2) * Math.signum(t) * -Math.signum(d);

      talonLeft.set(leftSpeedFromRadius(r,d));
      talonRight.set(rightSpeedFromRadius(r,d));
    }
    else if(Math.abs(d) > driveDeadzone && Math.abs(t) < turnDeadzone){
      talonLeft.set(d);
      talonRight.set(d);
    }
    else if(Math.abs(d) < driveDeadzone&& Math.abs(t) > turnDeadzone){
      talonLeft.set(t);
      talonRight.set(-t);
    }
    else{
      talonLeft.stopMotor();
      talonRight.stopMotor();
    }

  }

  double trackWidth = 12.5;

  public double leftSpeedFromRadius(double r, double d) {
    double baseVelocity = d;

    double angularVelocity = baseVelocity / r;

    return angularVelocity * (r - (trackWidth / 2));
  }

  public double rightSpeedFromRadius(double r, double d) {
    double baseVelocity = d;

    double angularVelocity = baseVelocity / r;

    return angularVelocity * (r + (trackWidth / 2));
  }

  CheesyDriveHelper driveHelper = new CheesyDriveHelper();
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
