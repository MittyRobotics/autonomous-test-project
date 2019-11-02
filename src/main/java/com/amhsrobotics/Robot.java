/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.amhsrobotics;

import com.amhsrobotics.purepursuit.PathFollowerPosition;
import com.amhsrobotics.subsystems.TankDrive;
import com.amhsrobotics.subsystems.DriveTrain;
import com.amhsrobotics.subsystems.Gyro;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

    public Robot() {
        super(0.06);
    }


    @Override
    public void robotInit() {
        System.out.println("Init");
        DriveTrain.getInstance();
        DriveTrain.getInstance().initHardware();
        Gyro.getInstance();
        Notifier odometryNotifier = new Notifier(Odometry.getInstance());
        odometryNotifier.startPeriodic(0.06);
        //TODO: measure track width
        PathFollowerPosition.getInstance().setupRobot(27);
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
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
        //TODO: calibrate ticks per inch
        //TODO: test odometry and PathFollowerPosition
        new TankDrive().start();
    }

    @Override
    public void teleopPeriodic() {
        //System.out.println(" Left: " + DriveTrain.getInstance().getLeftEncoder() + " Right: " + DriveTrain.getInstance().getRightEncoder() + " Gyro: " + Gyro.getInstance().getAngle());

    }

    @Override
    public void testPeriodic() {
    }
}
