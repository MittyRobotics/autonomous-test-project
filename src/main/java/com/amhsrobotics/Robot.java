/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.amhsrobotics;

import com.amhsrobotics.autonomous.commands.Translate2dTrajectory;
import com.amhsrobotics.constants.DriveConstants;
import com.amhsrobotics.purepursuit.PathFollowerPosition;
import com.amhsrobotics.purepursuit.VelocityConstraints;
import com.amhsrobotics.purepursuit.coordinate.Coordinate;
import com.amhsrobotics.purepursuit.paths.CubicHermitePath;
import com.amhsrobotics.purepursuit.paths.Path;
import com.amhsrobotics.subsystems.TankDrive;
import com.amhsrobotics.subsystems.DriveTrain;
import com.amhsrobotics.subsystems.Gyro;
import com.amhsrobotics.subsystems.TankVelocity;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

    public Robot() {
        super(0.06);
    }

    Translate2dTrajectory command;

    @Override
    public void robotInit() {
        System.out.println("Init");
        DriveTrain.getInstance();
        DriveTrain.getInstance().initHardware();
        Gyro.getInstance();
        Notifier odometryNotifier = new Notifier(Odometry.getInstance());
        odometryNotifier.startPeriodic(0.1);
        //TODO: measure track width

        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(0,0,0),
                new Coordinate(24,50,0)
        };
        Path path = new CubicHermitePath(coordinates,new VelocityConstraints(20,20,100,10,20,0,.8));
        command = new Translate2dTrajectory(path,false);

    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        //System.out.println(" Left: " + DriveTrain.getInstance().getLeftEncoder() / DriveConstants.DRIVE_TICKS_PER_INCH + " Right: " + DriveTrain.getInstance().getRightEncoder() / DriveConstants.DRIVE_TICKS_PER_INCH + " Gyro: " + Gyro.getInstance().getAngle());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {

    }


    @Override
    public void autonomousInit() {
        //new TankVelocity(20).start();
        PathFollowerPosition.getInstance().setupRobot(27);
        command.start();
    }

    @Override
    public void autonomousPeriodic() {
        //System.out.println(" Left Vel: " + DriveTrain.getInstance().getLeftVelocityInches()  + " Right Vel: " + DriveTrain.getInstance().getRightVelocityInches());
    }

    @Override
    public void teleopInit() {
        //TODO: calibrate ticks per inch
        //TODO: test odometry and PathFollowerPosition
        new TankDrive().start();

    }

    @Override
    public void teleopPeriodic() {
        System.out.println(" Left Vel: " + DriveTrain.getInstance().getLeftVelocityInches()  + " Right Vel: " + DriveTrain.getInstance().getRightVelocityInches());
    }

    @Override
    public void testPeriodic() {
    }
}
