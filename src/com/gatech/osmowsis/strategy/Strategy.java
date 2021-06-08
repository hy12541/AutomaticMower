package com.gatech.osmowsis.strategy;

import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Location;

public class Strategy {
	// calculate the step number to target location
	public static int stepNumberToTargetLocation(Location oriLoacation, Location targetLocaton) {
		int stepNumber = 0;
		int xOffset, yOffset;
		int absXOffset, absYOffset;
		if (oriLoacation != null && targetLocaton != null) {
			xOffset = oriLoacation.getxCoordinate() - targetLocaton.getxCoordinate();
			yOffset = oriLoacation.getyCoordinate() - targetLocaton.getyCoordinate();

			absXOffset = Math.abs(xOffset);
			absYOffset = Math.abs(yOffset);

			if (absXOffset == 0 && absYOffset != 0) {
				stepNumber = absYOffset;
			} else if (absXOffset != 0 && absYOffset == 0) {
				stepNumber = absXOffset;
			} else if (absXOffset != 0 && absYOffset != 0) {
				if (absXOffset == absYOffset) {
					stepNumber = absXOffset;
				} else if (absXOffset < absYOffset) {
					stepNumber = absYOffset;
				} else {
					stepNumber = absXOffset;
				}

			}
		}

		return stepNumber;
	}

	// calculate the next direction to target location
	public static Direction determinDirectionToTargetLocation(Location oriLoacation, Location targetLocaton) {

		Direction direction = Direction.north;
		int xOffset, yOffset;
		if (oriLoacation != null && targetLocaton != null) {
			xOffset = oriLoacation.getxCoordinate() - targetLocaton.getxCoordinate();
			yOffset = oriLoacation.getyCoordinate() - targetLocaton.getyCoordinate();

			if (xOffset == 0 && yOffset != 0) {
				if (yOffset > 0) {
					direction = Direction.south;
				} else {
					direction = Direction.north;
				}

			} else if (xOffset != 0 && yOffset == 0) {
				if (xOffset > 0) {
					direction = Direction.west;
				} else {
					direction = Direction.east;
				}
			} else if (xOffset != 0 && yOffset != 0) {
				if (xOffset > 0) {
					if (yOffset > 0) {
						direction = Direction.southwest;
					} else {
						direction = Direction.northwest;
					}
				} else {
					if (yOffset > 0) {
						direction = Direction.southeast;
					} else {
						direction = Direction.northeast;
					}
				}

			}
		}

		return direction;

	}

}
