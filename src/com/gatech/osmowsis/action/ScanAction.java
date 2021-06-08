package com.gatech.osmowsis.action;

import java.util.Map;

import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.SimulationState;
import com.gatech.osmowsis.square.Square;

// base scan action
public abstract class ScanAction extends Action {
	Location curLocation;

	abstract Map<Location, Square> scan(SimulationState simulationState);
}
