package com.amhsrobotics;

import com.amhsrobotics.constants.DriveConstants;
import com.amhsrobotics.purepursuit.PathFollowerPosition;
import com.amhsrobotics.subsystems.DriveTrain;
import com.amhsrobotics.subsystems.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Odometry implements Runnable {

    private double robotX = 0;
    private double robotY = 0;
    private double robotHeading = 0;

    private double lastLeftEncoderPos = 0;
    private double lastRightEncoderPos = 0;

    private double calibrateGyroVal = 0;

    private static Odometry ourInstance = new Odometry();

    public static Odometry getInstance() {
        return ourInstance;
    }

    private Odometry() {

    }

    @Override
    public void run(){
        //Update robot values based on encoder and gyro output
        robotHeading = Gyro.getInstance().getAngle() - calibrateGyroVal;
        if(robotHeading < 0){
            robotHeading = robotHeading+360;
        }
        robotHeading = 0-robotHeading;
        double deltaLeftPos = DriveTrain.getInstance().getLeftEncoder() - lastLeftEncoderPos;
        double deltaRightPos = DriveTrain.getInstance().getRightEncoder() - lastRightEncoderPos;
        double deltaPosition = (deltaLeftPos + deltaRightPos)/2/ DriveConstants.DRIVE_TICKS_PER_INCH;
        robotY += deltaPosition * Math.cos(Math.toRadians(robotHeading));
        robotX += deltaPosition * Math.sin(Math.toRadians(robotHeading));
        lastLeftEncoderPos =  DriveTrain.getInstance().getLeftEncoder();
        lastRightEncoderPos = DriveTrain.getInstance().getRightEncoder();
        PathFollowerPosition.getInstance().update(robotX,robotY,robotHeading,DriveTrain.getInstance().getLeftVelocity(), DriveTrain.getInstance().getRightVelocity());
        SmartDashboard.putNumber("odometry_X", robotX);
        SmartDashboard.putNumber("odometry_Y", robotY);
        SmartDashboard.putNumber("odometry_Heading", robotHeading);
    }

    public void resetPosition(){
        robotX = 0;
        robotY = 0;
        calibrateGyroVal = Gyro.getInstance().getAngle();
        lastLeftEncoderPos = DriveTrain.getInstance().getLeftEncoder();
        lastRightEncoderPos = DriveTrain.getInstance().getRightEncoder();

    }

    public void setPos(double x, double y, double heading){
        this.robotX = x;
        this.robotY = y;
        this.robotHeading = heading;
    }

    public double getRobotX(){
        return robotX;
    }

    public double getRobotY(){
        return robotY ;
    }

    public double getRobotHeading(){
        return robotHeading;
    }

}
