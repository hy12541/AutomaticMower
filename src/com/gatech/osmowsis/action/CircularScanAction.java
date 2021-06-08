package com.gatech.osmowsis.action;

import java.util.LinkedHashMap;
import java.util.Map;
import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.SimulationState;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;
import com.gatech.osmowsis.strategy.Knowledge;

// circular scan action
public class CircularScanAction extends ScanAction {

	public CircularScanAction(Location curLocation) {
		super();
		this.actionName = "cscan";
		this.curLocation = curLocation;
		this.energyNeeded = 1;
	}

	// return surrounding squares based on simulation state
	@Override
	public Map<Location, Square> scan(SimulationState simulationState) {
		Square nextSquare;
		Map<Location, Square> resultSquares = new LinkedHashMap<Location, Square>();
		Direction[] directions = Direction.values();

		if (curLocation != null && simulationState != null) {
			for (int k = 0; k < directions.length; k++) {
				Direction lookThisWay = directions[k];
				// calculate new location by current location and direction
				MoveAction moveAction = new MoveAction(curLocation, lookThisWay);
				Location newLocation = moveAction.getNewLocation();

				if (simulationState.hasFence(newLocation)) {
					// has fence at new location
					nextSquare = new Square(SquareState.fence);
				} else if (simulationState.hasMower(newLocation)) {
					// has mower at new location
					nextSquare = new Square(SquareState.mower);
				} else {
					// get state of lawn square at new location
					nextSquare = new Square(simulationState.getLawn().getLawnSquareState(newLocation));
				}

				resultSquares.put(newLocation, nextSquare);
			}

		}

		return resultSquares;
	}

	// return surrounding squares based on knowledge
	public Square[] scan(Knowledge knowledge) {
		Square nextSquare;
		Square[] resultSquares = new Square[8];
		Direction[] directions = Direction.values();

		if (curLocation != null && knowledge != null) {
			// get surrounding square states by current location
			for (int k = 0; k < directions.length; k++) {
				Direction lookThisWay = directions[k];

				// calculate new location by current location and direction
				MoveAction moveAction = new MoveAction(curLocation, lookThisWay);
				Location newLocation = moveAction.getNewLocation();
				nextSquare = new Square(knowledge.getLawn().getLawnSquare(newLocation).getSquareState());

				resultSquares[k] = nextSquare;
			}

		}

		return resultSquares;
	}

}
