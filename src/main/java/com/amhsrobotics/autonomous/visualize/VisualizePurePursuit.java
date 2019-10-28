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
		
		VelocityConstraints velocityConstraints = new VelocityConstraints(10, 10, 30, 0, 0, 1);
		
		Coordinate[] coordinates = new Coordinate[]{
				new Coordinate(0, 0, 0),
				new Coordinate(0, 100, 90),
				new Coordinate(20, 100, 90)
		};
		
		Path path = new CubicHermitePath(coordinates, velocityConstraints);
		
		PurePursuitController controller = new PurePursuitController(path);
		PathFollowerPosition.getInstance().update(0, 0, 0);
		
		PurePursuitSimulator simulator = new PurePursuitSimulator(controller);
		simulator.start();
		
	}
}
