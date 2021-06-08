package com.gatech.osmowsis.strategy;

import java.util.HashMap;

import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Location;

public class DirectionMap {

	private static DirectionMap directionMap;

	public static DirectionMap getDirectionMap() {
		if (directionMap == null) {
			directionMap = new DirectionMap();
		}
		return directionMap;
	}

	private HashMap<Direction, Integer> xDIR_MAP;
	private HashMap<Direction, Integer> yDIR_MAP;

	private DirectionMap() {
		super();
		// for calculating offset of x by direction
		xDIR_MAP = new HashMap<>();
		xDIR_MAP.put(Direction.northeast, 1);
		xDIR_MAP.put(Direction.north, 0);

		xDIR_MAP.put(Direction.east, 1);
		xDIR_MAP.put(Direction.southeast, 1);
		xDIR_MAP.put(Direction.south, 0);
		xDIR_MAP.put(Direction.southwest, -1);
		xDIR_MAP.put(Direction.west, -1);
		xDIR_MAP.put(Direction.northwest, -1);

		// for calculating offset of y by direction
		yDIR_MAP = new HashMap<>();
		yDIR_MAP.put(Direction.northeast, 1);
		yDIR_MAP.put(Direction.north, 1);

		yDIR_MAP.put(Direction.east, 0);
		yDIR_MAP.put(Direction.southeast, -1);
		yDIR_MAP.put(Direction.south, -1);
		yDIR_MAP.put(Direction.southwest, -1);
		yDIR_MAP.put(Direction.west, 0);
		yDIR_MAP.put(Direction.northwest, 1);

	}

	public int getOffsetX(Direction direction) {
		return xDIR_MAP.get(direction);
	}

	public int getOffsetY(Direction direction) {
		return yDIR_MAP.get(direction);
	}

	// calculate target location by original location and direction
	public Location getTargetLocation(Location originalLoacation, Direction direction) {
		int offsetX = getOffsetX(direction);
		int offsetY = getOffsetY(direction);

		// calculate target direction
		int targetX = originalLoacation.getxCoordinate() + offsetX;
		int targetY = originalLoacation.getyCoordinate() + offsetY;
		return new Location(targetX, targetY);
	}

	// calculate previous location by current location and direction
	public Location getPreviousLocation(Location currentLocation, Direction currentDirection) {
		int offsetX = -getOffsetX(currentDirection);
		int offsetY = -getOffsetY(currentDirection);

		int previousX = currentLocation.getxCoordinate() + offsetX;
		int previousY = currentLocation.getyCoordinate() + offsetY;

		return new Location(previousX, previousY);

	}

}
