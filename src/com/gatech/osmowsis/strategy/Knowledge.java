package com.gatech.osmowsis.strategy;

import java.util.HashMap;
import java.util.Map;

import com.gatech.osmowsis.simstate.Lawn;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.MowerStatus;
import com.gatech.osmowsis.square.Square;

/**
 * CS6310 Fall 2019 team 11: Hou, Hu, Lin, Wang, Zhang this class saves
 * knowledge of each mower that can be shared with other mowers
 */
public class Knowledge {
	private static final DirectionMap directionMap = com.gatech.osmowsis.strategy.DirectionMap.getDirectionMap();
	private int id;
	private MowerStatus mowerStatus;
	private Location absoluteLocation;
	private Location rechargingPadLocation;
	private Map<Location, Square> individualKnowledge = new HashMap<>();

	public Knowledge() {
		super();
	}

//	private boolean isOutOfBounds(int xCoordinate, int yCoordinate) {
//		return xCoordinate < 0 || xCoordinate >= localSquares[0].length || yCoordinate < 0
//				|| yCoordinate >= localSquares.length;
//	}
//
//	private boolean isOutOfBounds(Location location) {
//		int xCoordinate = location.getxCoordinate();
//		int yCoordinate = location.getyCoordinate();
//		return xCoordinate < 0 || xCoordinate >= localSquares[0].length || yCoordinate < 0
//				|| yCoordinate >= localSquares.length;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MowerStatus getMowerStatus() {
		return mowerStatus;
	}

	public void setMowerStatus(MowerStatus mowerStatus) {
		this.mowerStatus = mowerStatus;
	}

	public Location getAbsoluteLocation() {
		return absoluteLocation;
	}

	public void setAbsoluteLocation(Location absoluteLocation) {
		this.absoluteLocation = absoluteLocation;
	}

	public Location getRechargingPadLocation() {
		return rechargingPadLocation;
	}

	public void setRechargingPadLocation(Location rechargingPadLocation) {
		this.rechargingPadLocation = rechargingPadLocation;
	}

	public Map<Location, Square> getIndividualKnowledge() {
		return individualKnowledge;
	}

	public void setIndividualKnowledge(Map<Location, Square> individualKnowledge) {
		this.individualKnowledge = individualKnowledge;
	}

//	/**
//	 * merge circular scan result to localSquares does not save fence - out of bound
//	 * of localSquares
//	 */
//	public void saveCircularScanResults(Square[] trackScanResults) {
//		Direction[] directions = Direction.values();
//		int currXCoordinate = absoluteLocation.getxCoordinate();
//		int currYCoordinate = absoluteLocation.getyCoordinate();
//
//		for (int k = 0; k < directions.length; k++) {
//			int nextXCoordinate = currXCoordinate + directionMap.getOffsetX(directions[k]);
//			int nextYCoordinate = currYCoordinate + directionMap.getOffsetY(directions[k]);
//			// fence is not included in localSquares, out of bound
//			if (trackScanResults[k] != null) {
//				if (trackScanResults[k].getSquareState().equals(SquareState.fence)) {
//					continue;
//				} else if (!isOutOfBounds(nextXCoordinate, nextYCoordinate)) {
//					localSquares[nextYCoordinate][nextXCoordinate].setSquareState(trackScanResults[k].getSquareState());
//				}
//			}
//		}
//	}
//
//	/**
//	 * merge linear scan result to localSquares does not save fence - out of bound
//	 * of localSquares
//	 */
//	public void saveLinearScanResults(Square[] trackScanResults, Direction direction) {
//		int nextXCoordinate = absoluteLocation.getxCoordinate();
//		int nextYCoordinate = absoluteLocation.getyCoordinate();
//		int xOffset = directionMap.getOffsetX(direction);
//		int yOffset = directionMap.getOffsetY(direction);
//
//		for (int k = 0; k < trackScanResults.length; k++) {
//			nextXCoordinate += xOffset;
//			nextYCoordinate += yOffset;
//			// fence is not included in localSquares, out of bound
//			if (trackScanResults[k] != null) {
//				if (trackScanResults[k].getSquareState().equals(SquareState.fence)) {
//					break;
//				} else if (!isOutOfBounds(nextXCoordinate, nextYCoordinate)) {
//					localSquares[nextYCoordinate][nextXCoordinate].setSquareState(trackScanResults[k].getSquareState());
//				}
//			}
//		}
//	}

	/**
	 * update mower location and localSquares after move action
	 */
	// comment out for undefined square state: mower_gopher
	/*
	 * public void saveMoveResult(Direction direction) { // remove mower from
	 * current square and set state to be empty or gopher_empty
	 * removeMowerFromOldSquare(absoluteLocation);
	 * 
	 * // calculate new location using current location and direction int
	 * xCoordinate = absoluteLocation.getxCoordinate() +
	 * directionMap.getOffsetX(direction); int yCoordinate =
	 * absoluteLocation.getyCoordinate() + directionMap.getOffsetY(direction);
	 * Location newLocation = new Location(xCoordinate, yCoordinate);
	 * 
	 * // update mower new location setAbsoluteLocation(newLocation);
	 * 
	 * // update localSquares with mower new location
	 * addMowerToNewSquare(newLocation); }
	 * 
	 *//**
		 * remove mower from current square and set state to be empty or gopher_empty
		 *//*
			 * 
			 * private void removeMowerFromOldSquare(Location location) { Square curr =
			 * localSquares[location.getyCoordinate()][location.getxCoordinate()]; if
			 * (curr.getSquareState().equals(SquareState.mower_gopher)) {
			 * curr.setSquareState(SquareState.gopher_empty); } else if
			 * (curr.getSquareState().equals(SquareState.mower)) {
			 * curr.setSquareState(SquareState.empty); } }
			 */

	/**
	 * update localSquares after move action
	 * ---------------?????????????-------------- what to do if new location is
	 * fence, mower, mower_gopher, unknown?
	 */
	// comment out for undefined square state: mower_gopher
	/*
	 * private void addMowerToNewSquare(Location location) { if
	 * (!isOutOfBounds(location)) { Square square =
	 * localSquares[location.getyCoordinate()][location.getxCoordinate()]; if
	 * (square.getSquareState().equals(SquareState.gopher_empty)) {
	 * square.setSquareState(SquareState.mower_gopher); } else if
	 * (square.getSquareState().equals(SquareState.gopher_grass)) {
	 * square.setSquareState(SquareState.mower_gopher); } else if
	 * (square.getSquareState().equals(SquareState.grass) ||
	 * square.getSquareState().equals(SquareState.empty)) {
	 * square.setSquareState(SquareState.mower); } else if
	 * (square.getSquareState().equals(SquareState.mower) ||
	 * square.getSquareState().equals(SquareState.mower_gopher)) {
	 * square.setSquareState(SquareState.empty); } } }
	 */

	public Lawn getLawn() {
		// TODO Auto-generated method stub
		return null;
	}

	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
}
