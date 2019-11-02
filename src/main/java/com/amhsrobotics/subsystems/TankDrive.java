package com.amhsrobotics.subsystems;

import com.amhsrobotics.OI;
import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {
	public TankDrive() {
		requires(DriveTrain.getInstance());
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double left;
		double right;
		double threshold = 0.1;
		if(Math.abs(OI.getInstance().getDriveController().getY(GenericHID.Hand.kLeft)) > threshold){
			left = -OI.getInstance().getDriveController().getY(GenericHID.Hand.kLeft);
		}
		else{
			left = 0;
		}
		if(Math.abs(OI.getInstance().getDriveController().getY(GenericHID.Hand.kRight)) > threshold){
			right = -OI.getInstance().getDriveController().getY(GenericHID.Hand.kRight);
		}
		else{
			right = 0;
		}
		DriveTrain.getInstance().tankDrive(left,right);
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
