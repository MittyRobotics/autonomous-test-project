package com.amhsrobotics.autonomous.commands;

import com.amhsrobotics.Robot;
import com.amhsrobotics.TKOSubsystem;
import com.amhsrobotics.motionprofile.TrapezoidalMotionProfile;
import com.amhsrobotics.motionprofile.datatypes.MechanismBounds;
import com.amhsrobotics.motionprofile.datatypes.MotionFrame;
import com.amhsrobotics.motionprofile.datatypes.VelocityConstraints;
import edu.wpi.first.wpilibj.command.Command;

public class MotionProfileCommand extends Command {
	private TrapezoidalMotionProfile motionProfile;
	private TKOSubsystem subsystem;
	private double setpoint;
	private double maxPercent;
	
	public MotionProfileCommand(TKOSubsystem subsystem, double setpoint, double maxPercent){
		super("Motion Profile");
		this.subsystem = subsystem;
		this.setpoint = setpoint;
		this.maxPercent = maxPercent;
	}
	
	@Override
	protected void initialize() {
		VelocityConstraints constraints = new VelocityConstraints(subsystem.getMaxAcceleration(),subsystem.getMaxDeceleration(),subsystem.getMaxVelocity(),subsystem.getVelocity(),0);
		MechanismBounds bounds = new MechanismBounds(subsystem.getPosition(),0,0); //Min and max position can be added as another subsystem variable later
		motionProfile = new TrapezoidalMotionProfile(setpoint,constraints,bounds);
	}
	
	@Override
	protected void execute() {
		double t = timeSinceInitialized();
		MotionFrame frame = motionProfile.getFrameAtTime(t);
		subsystem.setMotor(getPercentOutputFromPID(frame.getPosition()));
	}
	
	private double lastError;
	private double integral;
	
	private double getPercentOutputFromPID(double position){
		double Kp = subsystem.getKp();
		double Ki = subsystem.getKi();
		double Kd = subsystem.getKd();
		
		double measured = subsystem.getPosition();
		
		double percent = 0;
		
		double error = position - measured;
		
		integral = integral + error * Robot.PERIOD;
		double derivative = (error-lastError) / Robot.PERIOD;
		
		percent = Kp * error + Ki * integral + Kd * derivative;
		
		percent = Math.max(-maxPercent, Math.min(maxPercent,percent));
		
		lastError = error;
		
		return percent;
	}
	
	@Override
	protected boolean isFinished() {
		double threshold = 1; //Stopping threshold can be set as another subsystem variable later
		return Math.abs(subsystem.getPosition() - setpoint) < threshold;
	}
}
