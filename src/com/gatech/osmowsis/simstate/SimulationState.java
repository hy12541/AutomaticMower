package com.gatech.osmowsis.simstate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.gatech.osmowsis.action.Action;
import com.gatech.osmowsis.action.CircularScanAction;
import com.gatech.osmowsis.action.LinearScanAction;
import com.gatech.osmowsis.action.MoveAction;
import com.gatech.osmowsis.action.PassAction;
import com.gatech.osmowsis.action.SteerAction;
import com.gatech.osmowsis.simsystem.OsMowSisError;
import com.gatech.osmowsis.simsystem.SummaryReport;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;

// simulation state
public class SimulationState {

	private Lawn lawn;
	private int totalMowerNumber;
	private int totalGopherNumber;
	private int totalRechargingPadNumber;
	private Mower[] mowers;
	private Gopher[] gophers;
	private int maxTurnNumber;
	private int turnCompletedNumber;
	private boolean isHalted = false;
	private OsMowSisError error;
	private Set<RechargingPad> rechargingPads;
	private Map<Location, Square> allScannedSquares;

	public SimulationState() {
		super();
	}

	public Lawn getLawn() {
		return lawn;
	}

	public void setLawn(Lawn lawn) {
		this.lawn = lawn;
	}

	public Gopher[] getGophers() {
		return gophers;
	}

	public void setGophers(Gopher[] gophers) {
		this.gophers = gophers;
	}

	public int getTotalGopherNumber() {
		return totalGopherNumber;
	}

	public void setTotalGopherNumber(int totalGopherNumber) {
		this.totalGopherNumber = totalGopherNumber;
	}

	public Mower[] getMowers() {
		return mowers;
	}

	public void setMowers(Mower[] mowers) {
		this.mowers = mowers;
	}

	public int getTotalMowerNumber() {
		return totalMowerNumber;
	}

	public void setTotalMowerNumber(int totalMowerNumber) {
		this.totalMowerNumber = totalMowerNumber;
	}

	public int getTotalRechargingPadNumber() {
		return totalRechargingPadNumber;
	}

	public void setTotalRechargingPadNumber(int totalRechargingPadNumber) {
		this.totalRechargingPadNumber = totalRechargingPadNumber;
	}

	public int getMaxTurnNumber() {
		return maxTurnNumber;
	}

	public void setMaxTurnNumber(int maxTurnNumber) {
		this.maxTurnNumber = maxTurnNumber;
	}

	public Set<RechargingPad> getRechargingPads() {
		return rechargingPads;
	}

	public void setRechargingPads(Set<RechargingPad> rechargingPads) {
		this.rechargingPads = rechargingPads;
	}

	public int getTurnCompletedNumber() {
		return turnCompletedNumber;
	}

	public void setTurnCompletedNumber(int turnCompletedNumber) {
		this.turnCompletedNumber = turnCompletedNumber;
	}

	public OsMowSisError getError() {
		return error;
	}

	public void setError(OsMowSisError error) {
		this.error = error;
	}

	public Map<Location, Square> getAllScannedSquares() {
		return allScannedSquares;
	}

	public void setAllScannedSquares(Map<Location, Square> allScannedSquares) {
		this.allScannedSquares = allScannedSquares;
	}

	public boolean isHalted() {
		// check if simulation run should be halted
		if (mowersAllRemoved() || lawn.grassAllCut() || !isSimulationDuration()) {
			isHalted = true;
		}
		return isHalted;
	}

	// calculate if all mowers have been removed
	public boolean mowersAllRemoved() {
		if (this.mowers != null && this.mowers.length > 0) {
			// check if all mowers are crashed
			for (int k = 0; k < this.mowers.length; k++) {
				if (this.mowers[k].getStatus() == MowerStatus.ok) {
					return false;
				}
			}

		}
		return true;
	}

	private boolean isSimulationDuration() {

		// check if it reaches turn limit
		return turnCompletedNumber < maxTurnNumber;
	}

	public void incrementTurnCompletedNumber() {
		// increment turn completed number
		turnCompletedNumber++;
	}

	// calculate if it is gopher's turn
	public boolean isGopherTurn() {
		if ((turnCompletedNumber + 1) % Gopher.getPeriod() == 0) {
			return true;
		}
		return false;
	}

	// get max id value of available mowers
	public int getMaxIdInAvailableMowers() {
		int maxId = -1;

		if (mowers != null && mowers.length > 0) {
			for (int i = mowers.length - 1; i >= 0; i--) {
				if (mowers[i].getStatus() == MowerStatus.ok) {
					maxId = mowers[i].getId();
					break;
				}
			}
		}
		return maxId;
	}

	// add scan results to all scanned squares
	private void addIntoAllScannedSquares(Map<Location, Square> trackScanResults) {
		if (allScannedSquares == null) {
			allScannedSquares = new LinkedHashMap<Location, Square>();
		}

		// save scanned squares
		if (trackScanResults != null && trackScanResults.size() > 0) {
			for (Map.Entry<Location, Square> entry : trackScanResults.entrySet()) {
				allScannedSquares.put(entry.getKey(), entry.getValue());

			}
		}
	}

	// validate mower's action
	public boolean validateMowerAction(Action actionProposed, Mower mower) {

		boolean isValid = false;

		if (actionProposed != null && mower != null) {

			if (actionProposed instanceof CircularScanAction) {
				isValid = true;
				// in the case of a circular scan, return the information for the eight
				// surrounding squares
				// always use a north-bound orientation
				mower.setTrackScanResults(((CircularScanAction) actionProposed).scan(this));
				// set status of last action
				mower.setTrackAction(actionProposed);

				// add into all scanned squares
				addIntoAllScannedSquares(((CircularScanAction) actionProposed).scan(this));

				// if there is a recharging pad
				if (hasRechargingPad(mower.getLocation())) {
					// recharge engergy
					mower.recharge();
				} else {
					// reduce energy
					mower.reduceEnergy(actionProposed.getEnergyNeeded());

				}

			} else if (actionProposed instanceof LinearScanAction) {
				isValid = true;
				// in the case of a linear scan, return the information for forward squares
				// until a fence square
				// along the current direction
				mower.setTrackScanResults(((LinearScanAction) actionProposed).scan(this));
				// set status of last action
				mower.setTrackAction(actionProposed);

				// add into all scanned squares
				addIntoAllScannedSquares(((LinearScanAction) actionProposed).scan(this));

				// if there is a recharging pad
				if (hasRechargingPad(mower.getLocation())) {
					// recharge engergy
					mower.recharge();
				} else {
					// reduce energy
					mower.reduceEnergy(actionProposed.getEnergyNeeded());

				}

			} else if (actionProposed instanceof PassAction) {
				isValid = true;
				// in the case of a pass, set status of last action
				// set status of last action
				mower.setTrackAction(actionProposed);

				// if there is a recharging pad
				if (hasRechargingPad(mower.getLocation())) {
					// recharge engergy
					mower.recharge();
				} else {
					// reduce energy
					mower.reduceEnergy(actionProposed.getEnergyNeeded());

				}

			} else if (actionProposed instanceof SteerAction) {
				isValid = true;
				// in the case of a steer, set new direction
				mower.setDirection(((SteerAction) actionProposed).steer());
				// set status of last action
				mower.setTrackAction(actionProposed);

				// if there is a recharging pad
				if (hasRechargingPad(mower.getLocation())) {
					// recharge engergy
					mower.recharge();
				} else {
					// reduce energy
					mower.reduceEnergy(actionProposed.getEnergyNeeded());

				}

			} else if (actionProposed instanceof MoveAction) {
				isValid = true;

				// in the case of a move, calculate new location by current location and
				// direction
				Location newLocation = ((MoveAction) actionProposed).getNewLocation();

				if (hasFence(newLocation)) {

					// mower hit a fence
					mower.setStatus(MowerStatus.crash);

				} else if (hasGopher(newLocation)) {

					// mower will be eaten by gopher
					mower.setStatus(MowerStatus.crash);

				} else if (hasMower(newLocation)) {

					// mower will be collided with another mower
					Mower collidedMower = getMower(newLocation);

					// update status of 2 collied mowers
					if (collidedMower != null) {
						collidedMower.setStatus(MowerStatus.crash);
					}
					mower.setStatus(MowerStatus.crash);

				} else {

					// mow the lawn at new location
					lawn.getLawnSquare(newLocation).setSquareState(SquareState.empty);

				}

				// mower moves
				mower.setLocation(newLocation);
				// set status of last action
				mower.setTrackAction(actionProposed);

				// if there is a recharging pad at new location
				if (hasRechargingPad(newLocation)) {
					// recharge engergy
					mower.recharge();
				} else {
					// reduce energy
					mower.reduceEnergy(actionProposed.getEnergyNeeded());

				}

			}
			// action is not recognized
			else {

				// mower crashes if action is not recognized
				mower.setStatus(MowerStatus.crash);
				// set last action to null
				mower.setTrackAction(null);
			}

		}

		return isValid;

	}

	// validate gopher's action
	public boolean validateGopherAction(Action actionProposed, Gopher gopher) {
		boolean isValid = false;
		if (gopher != null) {
			if (actionProposed instanceof MoveAction) {
				// valid action
				isValid = true;

				// old location before move
				Location oldLocation = gopher.getLocation();

				// in the case of a move, calculate new location by current location and
				// direction
				Location newLocation = ((MoveAction) actionProposed).getNewLocation();

				if (!hasFence(newLocation) && !hasGopher(newLocation)) {
					// update gopher location
					gopher.setLocation(newLocation);

					// update status of mower collided
					if (hasMower(newLocation)) {
						Mower collidedMower = getMower(newLocation);

						// update status of collied mower
						if (collidedMower != null) {
							collidedMower.setStatus(MowerStatus.crash);
						}
					}

					// update state of lawn square at new location
					if (lawn.getLawnSquareState(newLocation) == SquareState.empty) {
						lawn.setLawnSquareState(newLocation, SquareState.gopher_empty);
					} else if (lawn.getLawnSquareState(newLocation) == SquareState.grass) {
						lawn.setLawnSquareState(newLocation, SquareState.gopher_grass);
					}

					// update state of lawn square at old location
					if (lawn.getLawnSquareState(oldLocation) == SquareState.gopher_empty) {
						lawn.setLawnSquareState(oldLocation, SquareState.empty);
					} else if (lawn.getLawnSquareState(oldLocation) == SquareState.gopher_grass) {
						lawn.setLawnSquareState(oldLocation, SquareState.grass);
					}
				}

			}
		}

		return isValid;
	}

	// calculate if it has fence at this location
	public boolean hasFence(Location location) {
		boolean hasFence = false;
		if (location != null) {
			// check if it has fence at this location
			if (location.getxCoordinate() < 0 || location.getxCoordinate() >= lawn.getSize().getWidth()
					|| location.getyCoordinate() < 0 || location.getyCoordinate() >= lawn.getSize().getLength()) {
				hasFence = true;

			}
		}

		return hasFence;

	}

	// calculate if it has mower at this location
	public boolean hasMower(Location location) {
		boolean hasMower = false;
		if (location != null) {
			for (int k = 0; k < mowers.length; k++) {
				// check if it has a non-crashed mower at this location
				if (mowers[k].getStatus() == MowerStatus.ok) {
					if (mowers[k].getLocation().getxCoordinate() == location.getxCoordinate()
							&& mowers[k].getLocation().getyCoordinate() == location.getyCoordinate()) {
						hasMower = true;
						break;
					}
				}

			}
		}

		return hasMower;

	}

	// calculate if it has gopher at this location
	public boolean hasGopher(Location location) {
		boolean hasGopher = false;
		if (location != null) {
			// check if it has a crater at this location
			for (int k = 0; k < gophers.length; k++) {
				// check if it has a non-crashed gopher at this location
				if (gophers[k].getLocation().getxCoordinate() == location.getxCoordinate()
						&& gophers[k].getLocation().getyCoordinate() == location.getyCoordinate()) {

					hasGopher = true;
					break;
				}
			}
		}
		return hasGopher;

	}

	// calculate if it has recharging pad at this location
	public boolean hasRechargingPad(Location location) {

		return this.rechargingPads.contains(new RechargingPad(location));

	}

	// return mower by location
	private Mower getMower(Location location) {
		Mower mower = null;
		if (location != null) {
			for (int k = 0; k < mowers.length; k++) {
				// get mower instance at this location
				if (mowers[k].getStatus() == MowerStatus.ok) {
					if (mowers[k].getLocation().getxCoordinate() == location.getxCoordinate()
							&& mowers[k].getLocation().getyCoordinate() == location.getyCoordinate()) {
						mower = mowers[k];
						break;
					}
				}
			}

		}

		return mower;

	}

	// generate final report
	public SummaryReport generateSummaryReport() {
		// calculate lawn area
		int lawnArea = lawn.getArea();

		// calculate original grass number
		int originalGrassSquareNumber = lawnArea;

		// calculate current grass number
		int numGrass = lawn.getGrassNumber();

		// calculate empty square number
		int emptySquareNumber = originalGrassSquareNumber - numGrass;

		SummaryReport summaryReport = new SummaryReport(lawnArea, originalGrassSquareNumber, emptySquareNumber,
				turnCompletedNumber);

		return summaryReport;

	}

}
