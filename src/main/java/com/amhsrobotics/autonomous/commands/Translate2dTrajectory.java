package com.amhsrobotics.autonomous.commands;

import com.amhsrobotics.purepursuit.PurePursuitController;
import com.amhsrobotics.purepursuit.PurePursuitOutput;
import com.amhsrobotics.purepursuit.paths.Path;
import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class Translate2dTrajectory extends Command {
	private Path path;
	private boolean reversed;
	private PurePursuitController controller;

	public Translate2dTrajectory(Path path, boolean reversed) {
		requires(DriveTrain.getInstance());
		this.path = path;
		this.reversed = reversed;
	}
	
	@Override
	protected void initialize() {
		this.controller = new PurePursuitController(path, reversed);
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
