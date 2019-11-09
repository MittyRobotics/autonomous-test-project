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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

        //FORWARD:
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(0, 0, 0),
                new Coordinate(0, 30, 0),
//                new Coordinate(-20, 50, -90),
//                new Coordinate(-30, 50, -90),
//                new Coordinate(-50, 70, 0),
                new Coordinate(-50, 100, 0)
        };
        Path path = new CubicHermitePath(coordinates,new VelocityConstraints(70,20,150,20,50,0,.8));
        command = new Translate2dTrajectory(path,false);


//
////        //REVERSE:
//
//        Coordinate[] coordinates = new Coordinate[]{
//                new Coordinate(0, 0, 180),
//                new Coordinate(0, -30, 180),
//                new Coordinate(20, -50, 90),
//                new Coordinate(30, -50, 90),
//                new Coordinate(50, -70, 180),
//                new Coordinate(50, -100, 180)
//        };
//        Path path = new CubicHermitePath(coordinates,new VelocityConstraints(70,20,150,20,50,0,.8));
//        command = new Translate2dTrajectory(path,true);
//
////



        SmartDashboard.putNumber("LEFT_WHEEL_SETPOINT", 0);
        SmartDashboard.putNumber("RIGHT_WHEEL_SETPOINT", 0);
        SmartDashboard.putNumber("LEFT_WHEEL_VEL", 0);
        SmartDashboard.putNumber("RIGHT_WHEEL_VEL", 0);
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("LEFT_WHEEL_VEL", DriveTrain.getInstance().getLeftVelocityInches());
        SmartDashboard.putNumber("RIGHT_WHEEL_VEL", DriveTrain.getInstance().getRightVelocityInches());
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
        //System.out.println(" Left Vel: " + DriveTrain.getInstance().getLeftVelocityInches()  + " Right Vel: " + DriveTrain.getInstance().getRightVelocityInches());
    }

    @Override
    public void testPeriodic() {
    }
}
