package com.gatech.osmowsis.action;

import java.util.LinkedHashMap;
import java.util.Map;
import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.SimulationState;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;

// linear scan action
public class LinearScanAction extends ScanAction {

	private Direction curDirection;

	public LinearScanAction(Location curLocation, Direction curDirection) {
		super();
		this.actionName = "lscan";
		this.curLocation = curLocation;
		this.curDirection = curDirection;
		this.energyNeeded = 3;
	}

	// calculate linear squares from current location along current direction
	@Override
	public Map<Location, Square> scan(SimulationState simulationState) {
		Square nextSquare;
		Map<Location, Square> resultSquares = new LinkedHashMap<Location, Square>();

		if (curLocation != null && curDirection != null && simulationState != null) {

			// move forward along current direction
			MoveAction moveAction = new MoveAction(curLocation, curDirection);
			Location scanLocation = moveAction.getNewLocation();

			// stop when there is a fence square
			while (!simulationState.hasFence(scanLocation)) {

				// has mower at new location
				if (simulationState.hasMower(scanLocation)) {
					nextSquare = new Square(SquareState.mower);
				} else {
					// get state of lawn square at new location
					nextSquare = new Square(simulationState.getLawn().getLawnSquareState(scanLocation));
				}

				resultSquares.put(scanLocation, nextSquare);

				// keep moving forward along current direction
				moveAction = new MoveAction(scanLocation, curDirection);
				scanLocation = moveAction.getNewLocation();
			}

			// add fence square at last
			resultSquares.put(scanLocation, new Square(SquareState.fence));
		}
		return resultSquares;
	}

}
