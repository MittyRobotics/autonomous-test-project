package com.amhsrobotics.autonomous.visualize;

import com.amhsrobotics.autonomous.commands.Translate2dTrajectory;
import com.amhsrobotics.purepursuit.PathFollowerPosition;
import com.amhsrobotics.purepursuit.PurePursuitController;
import com.amhsrobotics.purepursuit.PurePursuitSimulator;
import com.amhsrobotics.purepursuit.VelocityConstraints;
import com.amhsrobotics.purepursuit.coordinate.Coordinate;
import com.amhsrobotics.purepursuit.graph.PurePursuitSimulatorGraph;
import com.amhsrobotics.purepursuit.paths.CubicHermitePath;
import com.amhsrobotics.purepursuit.paths.Path;

public class VisualizePurePursuit {
	public static void main(String[] args) {

		//FORWARD:
		//FORWARD:
		Coordinate[] coordinates = new Coordinate[]{
				new Coordinate(0, 0, 0),
				new Coordinate(0, 25, 0),
				new Coordinate(-50, 75, 0),
				new Coordinate(-50, 100, 0)
		};

		Path path = new CubicHermitePath(coordinates,new VelocityConstraints(50,50,150,10,50,0,10), 0, 2);
		PurePursuitSimulatorGraph.getInstance().graphPath(path);
		PurePursuitSimulatorGraph.getInstance().resizeGraph();
//		PurePursuitController controller = new PurePursuitController(path, 20, 10, false);
//		PathFollowerPosition.getInstance().update(0, 0, 0, 0, 0);
//		PathFollowerPosition.getInstance().setupRobot(27);
//
//		PurePursuitSimulator simulator = new PurePursuitSimulator(controller, 60, PathFollowerPosition.getInstance().getTrackWidth());
//		simulator.start();
//








	}
}
