package com.amhsrobotics.autonomous.commands;

import com.amhsrobotics.constants.DriveConstants;
import com.amhsrobotics.motionprofile.TrapezoidalMotionProfile;
import com.amhsrobotics.motionprofile.datatypes.MechanismBounds;
import com.amhsrobotics.motionprofile.datatypes.MotionFrame;
import com.amhsrobotics.motionprofile.datatypes.VelocityConstraints;
import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class MotionProfileTranslate extends Command {
	
	private final double setpoint;
	private final VelocityConstraints velocityConstraints;
	private TrapezoidalMotionProfile motionProfile;
	
	private double leftStartPos;
	private double rightStartPos;
	
	
	public MotionProfileTranslate(double setpoint){
		this(setpoint, DriveConstants.DEFAULT_DRIVE_VELOCITY_CONSTRAINTS);
	}
	
	public MotionProfileTranslate(double setpoint, VelocityConstraints velocityConstraints, double timeout) {
		this(setpoint,velocityConstraints);
		setTimeout(timeout);
	}
	public MotionProfileTranslate(double setpoint, VelocityConstraints velocityConstraints) {
		requires(DriveTrain.getInstance());
		System.out.println("Constructor");
		this.setpoint = setpoint;
		this.velocityConstraints = velocityConstraints;
	}
	
	@Override
	protected void initialize() {
		System.out.println("Init");
		leftStartPos = DriveTrain.getInstance().getLeftEncoder()/DriveConstants.DRIVE_TICKS_PER_INCH;
		rightStartPos = DriveTrain.getInstance().getRightEncoder()/DriveConstants.DRIVE_TICKS_PER_INCH;
		motionProfile = new TrapezoidalMotionProfile(setpoint,velocityConstraints,new MechanismBounds(0,0,0));
	}
	
	@Override
	protected void execute() {
		double t = timeSinceInitialized();
		
		MotionFrame frame = motionProfile.getFrameAtTime(t);
		double position = frame.getPosition();
		
		DriveTrain.getInstance().translation(position + leftStartPos,position + rightStartPos);
	}
	
	@Override
	protected boolean isFinished() {
		double reachedSetpointThreshold = 1;
		System.out.println(Math.abs((DriveTrain.getInstance().getLeftEncoder()/DriveConstants.DRIVE_TICKS_PER_INCH)-leftStartPos - setpoint)< reachedSetpointThreshold);
		return (Math.abs((DriveTrain.getInstance().getLeftEncoder()/DriveConstants.DRIVE_TICKS_PER_INCH)-leftStartPos - setpoint) < reachedSetpointThreshold && Math.abs((DriveTrain.getInstance().getRightEncoder()/DriveConstants.DRIVE_TICKS_PER_INCH)-rightStartPos - setpoint) < reachedSetpointThreshold) || isTimedOut();
	}
	
	@Override
	protected void end() {
		System.out.println("End");
		DriveTrain.getInstance().tankVelocity(velocityConstraints.getEndVelocity(), velocityConstraints.getEndVelocity());
	}
	
	@Override
	protected void interrupted() {
	}
}
