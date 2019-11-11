package com.amhsrobotics.autonomous.commands;

import com.amhsrobotics.purepursuit.PurePursuitController;
import com.amhsrobotics.purepursuit.PurePursuitOutput;
import com.amhsrobotics.purepursuit.VelocityConstraints;
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
		this.controller = new PurePursuitController(path,20,15, reversed);
		System.out.println("Constructor");
	}

	@Override
	protected void initialize() {
		System.out.println("Init");
	}

	@Override
	protected void execute() {
		double t = timeSinceInitialized();
		PurePursuitOutput output = controller.update(t);

		System.out.println("Expected: " + output.getLeftVelocity() + " " + output.getRightVelocity());

		DriveTrain.getInstance().customTankVelocity(output.getLeftVelocity(), output.getRightVelocity());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		System.out.println("End Translate2dTrajectory");
		//DriveTrain.getInstance().tankDrive(0,0);
	}

	@Override
	protected void interrupted() {
	}
}
