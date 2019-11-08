package com.amhsrobotics.autonomous.visualize;

import com.amhsrobotics.purepursuit.PathFollowerPosition;
import com.amhsrobotics.purepursuit.PurePursuitController;
import com.amhsrobotics.purepursuit.PurePursuitSimulator;
import com.amhsrobotics.purepursuit.VelocityConstraints;
import com.amhsrobotics.purepursuit.coordinate.Coordinate;
import com.amhsrobotics.purepursuit.paths.CubicHermitePath;
import com.amhsrobotics.purepursuit.paths.Path;

public class VisualizePurePursuit {
	public static void main(String[] args) {
		
		VelocityConstraints velocityConstraints = new VelocityConstraints(20, 10, 50, 0, 0, 1);
		
		Coordinate[] coordinates = new Coordinate[]{
				new Coordinate(0, 0, 0),
				new Coordinate(0, 45, 0),
				new Coordinate(5, 50, 90),
				new Coordinate(45, 50, 90),
				new Coordinate(50, 55, 0),
				new Coordinate(50, 100, 0)
		};
		Path path = new CubicHermitePath(coordinates, velocityConstraints);
		
//		PurePursuitController controller = new PurePursuitController(path, 20, 10, false);
//		PathFollowerPosition.getInstance().update(0, 0, 0, 0, 0);
//		PathFollowerPosition.getInstance().setupRobot(27);
//
//		PurePursuitSimulator simulator = new PurePursuitSimulator(controller, 60, PathFollowerPosition.getInstance().getTrackWidth());
//		simulator.start();
//
	}
}
