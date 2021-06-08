package com.gatech.osmowsis.ui.model;

import com.gatech.osmowsis.action.Action;
import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.MowerStatus;

// ui mower
public class UIMower {
	private int id;
	private int energy;
	private Location location;
	private Direction direction;
	private MowerStatus mowerStatus;
	private Action trackAction;
	private boolean isCurrentMower;
	private boolean isNextMower;
	private Location[] trackScanLocations;

	public UIMower() {
		super();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public MowerStatus getMowerStatus() {
		return mowerStatus;
	}

	public void setMowerStatus(MowerStatus mowerStatus) {
		this.mowerStatus = mowerStatus;
	}

	public Action getTrackAction() {
		return trackAction;
	}

	public void setTrackAction(Action trackAction) {
		this.trackAction = trackAction;
	}

	public boolean isCurrentMower() {
		return isCurrentMower;
	}

	public void setCurrentMower(boolean isCurrentMower) {
		this.isCurrentMower = isCurrentMower;
	}

	public boolean isNextMower() {
		return isNextMower;
	}

	public void setNextMower(boolean isNextMower) {
		this.isNextMower = isNextMower;
	}

	public Location[] getTrackScanLocations() {
		return trackScanLocations;
	}

	public void setTrackScanLocations(Location[] trackScanLocations) {
		this.trackScanLocations = trackScanLocations;
	}

}
