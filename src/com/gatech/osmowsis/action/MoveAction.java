package com.gatech.osmowsis.action;

import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.strategy.DirectionMap;

// move action
public class MoveAction extends Action {
	private static final DirectionMap directionMap = com.gatech.osmowsis.strategy.DirectionMap.getDirectionMap();
	private Location currentLocation;
	private Direction currentDirection;

	public MoveAction(Location currentLocation, Direction currentDirection) {
		super();
		this.actionName = "move";
		this.currentLocation = currentLocation;
		this.currentDirection = currentDirection;
		this.energyNeeded = 2;
	}

	public Location getNewLocation() {
		return directionMap.getTargetLocation(currentLocation, currentDirection);
	}

	@Override
	public String toString() {
		return "MoveAction [currentLocation=" + currentLocation + ", currentDirection=" + currentDirection + "]";
	}

}
