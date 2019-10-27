package com.amhsrobotics.commands;

import com.amhsrobotics.OI;
import com.amhsrobotics.constants.DriveConstants;
import com.amhsrobotics.purepursuit.PurePursuitController;
import com.amhsrobotics.purepursuit.PurePursuitOutput;
import com.amhsrobotics.purepursuit.VelocityConstraints;
import com.amhsrobotics.purepursuit.coordinate.Coordinate;
import com.amhsrobotics.purepursuit.paths.CubicHermitePath;
import com.amhsrobotics.purepursuit.paths.Path;
import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class Translate2dTrajectory extends Command {
	private Path path;
	private PurePursuitController controller;

	public Translate2dTrajectory(Path path) {
		requires(DriveTrain.getInstance());

	
		this.path = path;
	}
	
	@Override
	protected void initialize() {
		this.controller = new PurePursuitController(path);
	}
	
	@Override
	protected void execute() {
		double t = timeSinceInitialized();
		PurePursuitOutput output = controller.update(t);
		
		DriveTrain.getInstance().tankVelocity(output.getLeftVelocity(), output.getRightVelocity());
	}
	
	@Override
	protected boolean isFinished() {
		return controller.isFinished();
	}
	
	@Override
	protected void end() {
		DriveTrain.getInstance().tankVelocity(path.getVelocityConstraints().getEndVelocity(),path.getVelocityConstraints().getEndVelocity());
	}
	
	@Override
	protected void interrupted() {
	}
}
