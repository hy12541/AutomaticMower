package com.gatech.osmowsis.action;

import com.gatech.osmowsis.simstate.Direction;

// steer action
public class SteerAction extends Action {
	private int turningRadius;
	private Direction targetDirection;

	public SteerAction(int turningRadius) {
		super();
		this.actionName = "steer";
		this.turningRadius = turningRadius;
		this.energyNeeded = 1;
	}

	public SteerAction(Direction targetDirection) {
		super();
		this.actionName = "steer";
		this.energyNeeded = 1;
		this.targetDirection = targetDirection;
	}

	// caculate direction based on turning radius
	public Direction steer() {
		if (targetDirection == null) {
			// get new direction by turning radius
			Direction newDirection = Direction.values()[turningRadius];

			return newDirection;

		}
		return this.targetDirection;

	}

}
