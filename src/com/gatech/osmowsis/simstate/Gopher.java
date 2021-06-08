package com.gatech.osmowsis.simstate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gatech.osmowsis.action.Action;
import com.gatech.osmowsis.action.MoveAction;
import com.gatech.osmowsis.strategy.Strategy;

public class Gopher {

	private static int period;
	private static Random randGenerator;
	private int id;
	private Location location;
	private GopherStatus status;
	private Mower mowerSelected;
	private boolean isCurrentGopher;
	private boolean isNextGopher;

	public Gopher() {
		super();
		if (randGenerator == null) {
			randGenerator = new Random();
		}

	}

	public static int getPeriod() {
		return period;
	}

	public static void setPeriod(int period) {
		Gopher.period = period;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public GopherStatus getStatus() {
		return status;
	}

	public void setStatus(GopherStatus status) {
		this.status = status;
	}

	public Mower getMowerSelected() {
		return mowerSelected;
	}

	public void setMowerSelected(Mower mower) {
		this.mowerSelected = mower;
	}

	public boolean isCurrentGopher() {
		return isCurrentGopher;
	}

	public void setCurrentGopher(boolean isCurrentGopher) {
		this.isCurrentGopher = isCurrentGopher;
	}

	public boolean isNextGopher() {
		return isNextGopher;
	}

	public void setNextGopher(boolean isNextGopher) {
		this.isNextGopher = isNextGopher;
	}

	// calculate mower selected
	public void determineMowerSelected(SimulationState simulationState) {
		// reset mowerSelected
		mowerSelected = null;
		if (simulationState != null) {
			Mower[] mowers = simulationState.getMowers();
			Lawn lawn = simulationState.getLawn();
			int width = lawn.getSize().getWidth();
			int length = lawn.getSize().getLength();
			int minDistance = Math.max(width, length);

			List<Mower> mowersSelected = new ArrayList<Mower>();
			// iterate non-crashed mowers and compare the distance
			for (Mower mower : mowers) {
				if (mower.getStatus() == MowerStatus.ok) {
					int stepNumberToTargetLocation = Strategy.stepNumberToTargetLocation(location, mower.getLocation());
					if (stepNumberToTargetLocation < minDistance) {
						minDistance = stepNumberToTargetLocation;
						if (mowersSelected.size() > 0) {
							mowersSelected.clear();
						}
						mowersSelected.add(mower);
					} else if (stepNumberToTargetLocation == minDistance) {
						mowersSelected.add(mower);
					}
				}
			}
			// if more than one mower have the same distance, then return one mower randomly
			if (mowersSelected.size() > 0) {
				mowerSelected = mowersSelected.get(randGenerator.nextInt(mowersSelected.size()));
			}
		}

	}

	// determine next action based on mower selected
	public Action determinNextAction() {
		Action nextAction = null;
		Direction directionToDestination = null;

		if (mowerSelected != null) {
			// calculate direction based on target location and origin location
			directionToDestination = Strategy.determinDirectionToTargetLocation(location, mowerSelected.getLocation());

		}

		if (directionToDestination != null) {
			nextAction = new MoveAction(location, directionToDestination);

		}
		return nextAction;

	}

}
