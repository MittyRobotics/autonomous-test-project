package com.amhsrobotics.autonomous.commands;

import com.amhsrobotics.purepursuit.PathFollowerPosition;
import com.amhsrobotics.purepursuit.PurePursuitController;
import com.amhsrobotics.purepursuit.PurePursuitOutput;
import com.amhsrobotics.purepursuit.VelocityConstraints;
import com.amhsrobotics.purepursuit.paths.Path;
import com.amhsrobotics.purepursuit.paths.TrajectoryPoint;
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
		this.controller = new PurePursuitController(path,40,25, reversed);
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
		if(controller.getPath().getTrajectoryPoints()[controller.getPath().getTrajectoryPoints().length-1].distance(new TrajectoryPoint(PathFollowerPosition.getInstance().getPathCentricX(), PathFollowerPosition.getInstance().getPathCentricY())) < 40){
			output.setLeftVelocity( output.getLeftVelocity()/2);
			output.setRightVelocity( output.getRightVelocity()/2);
		}
		DriveTrain.getInstance().customTankVelocity(output.getLeftVelocity(), output.getRightVelocity(),output.getAngleToLookahead());
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
