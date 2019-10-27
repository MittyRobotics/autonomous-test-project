package com.amhsrobotics.commands;

import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class MotionProfileRotate extends Command {
	public MotionProfileRotate() {
		requires(DriveTrain.getInstance());
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
	
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
}
