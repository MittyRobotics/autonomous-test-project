package com.amhsrobotics.commands;

import com.amhsrobotics.OI;
import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class Translate2dTrajectory extends Command {
	public Translate2dTrajectory() {
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
