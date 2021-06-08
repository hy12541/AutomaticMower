package com.gatech.osmowsis.strategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.MowerStatus;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;

// Singleton class to store shared information of the mowers
public class CommunicationChannel {

	private static CommunicationChannel sharedCommunicationChannel = null;

	public static CommunicationChannel getCommunicationChannel() {
		if (sharedCommunicationChannel == null) {
			sharedCommunicationChannel = new CommunicationChannel();
		}
		return sharedCommunicationChannel;
	}

//	private Square[][] sharedSquares;
	private Map<Integer, Location> mowerLocations = new HashMap<>();
	private Map<Integer, MowerStatus> mowerStatuses = new HashMap<>();
	private Set<Location> chargingPadLocations = new HashSet<>();
	private Map<Location, Square> combinedKnowledgeOnSquareState = new HashMap<>();

	private CommunicationChannel() {
		super();
	}

	public static CommunicationChannel getSharedCommunicationChannel() {
		return sharedCommunicationChannel;
	}

	public static void setSharedCommunicationChannel(CommunicationChannel sharedCommunicationChannel) {
		CommunicationChannel.sharedCommunicationChannel = sharedCommunicationChannel;
	}

	public Set<Location> getChargingPadLocations() {
		return chargingPadLocations;
	}

	public void setChargingPadLocations(Set<Location> chargingPadLocations) {
		this.chargingPadLocations = chargingPadLocations;
	}

	public void setMowerLocations(Map<Integer, Location> mowerLocations) {
		this.mowerLocations = mowerLocations;
	}

	public void setMowerStatuses(Map<Integer, MowerStatus> mowerStatuses) {
		this.mowerStatuses = mowerStatuses;
	}

	public Map<Location, Square> getCombinedKnowledgeOnSquareState() {
		return combinedKnowledgeOnSquareState;
	}

	public void setCombinedKnowledgeOnSquareState(Map<Location, Square> combinedKnowledgeOnSquareState) {
		this.combinedKnowledgeOnSquareState = combinedKnowledgeOnSquareState;
	}

	// add knowledge
	public void addKnowledgeToCommunicationChannel(Knowledge knowledge) {

//		this.combinedKnowledge.put(knowledge.getId(), knowledge);
		updateMowerLocation(knowledge.getId(), knowledge.getAbsoluteLocation());
		updateMowerStatus(knowledge.getId(), knowledge.getMowerStatus());
		addChargingPadLocation(knowledge.getRechargingPadLocation());
	}

	// get square state in shared knowledge by location
	public SquareState getSquareStateOnLocation(Location targetLocation) {

		if (this.combinedKnowledgeOnSquareState.containsKey(targetLocation)) {
			return this.combinedKnowledgeOnSquareState.get(targetLocation).getSquareState();
		}
		return SquareState.unknown;
	}

	/**
	 * merges mower's Location instance into mowerLocations which saves the location
	 * of all mowers
	 */
	private void updateMowerLocation(int id, Location location) {
		mowerLocations.put(id, location);
	}

	/**
	 * merges mower's MowerStatus instance into mowerStatuses which saves the status
	 * of all mowers
	 */
	private void updateMowerStatus(int id, MowerStatus mowerStatus) {
		mowerStatuses.put(id, mowerStatus);
	}

	/**
	 * add mower's rechargingPadLocation into chargingPadLocations instance if not
	 * there already
	 */
	private void addChargingPadLocation(Location location) {
		chargingPadLocations.add(location);
	}

//	/**
//	 * deep copy localSquares to sharedSquares
//	 */
//	private void deepCopySquares(Square[][] localSquares) {
//		if (localSquares != null) {
//			sharedSquares = new Square[localSquares.length][localSquares[0].length];
//			for (int i = 0; i < sharedSquares.length; i++) {
//				for (int j = 0; j < sharedSquares[0].length; j++) {
//					Square square = localSquares[i][j];
//					if (square != null) {
//						sharedSquares[i][j] = new Square(square.getSquareState());
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * merge known SquareState to sharedSquares
//	 */
//	private void copyKnownSquares(Square[][] localSquares) {
//		if (localSquares != null) {
//			for (int i = 0; i < sharedSquares.length; i++) {
//				for (int j = 0; j < sharedSquares[0].length; j++) {
//					Square square = localSquares[i][j];
//					if (square != null && square.getSquareState() != SquareState.unknown) {
//						sharedSquares[i][j].setSquareState(square.getSquareState());
//					}
//				}
//			}
//		}
//	}

	/**
	 * get the absolution location of all mowers
	 */
	public Collection<Location> getMowerLocations() {
		return mowerLocations.values();
	}

	/**
	 * get the absolution location of input mower ID
	 */
	public Location getMowerLocation(int id) {
		return mowerLocations.get(id);
	}

	/**
	 * get the MowerStatus of all mowers
	 */
	public Collection<MowerStatus> getMowerStatuses() {
		return mowerStatuses.values();
	}

	/**
	 * get the status of input mower ID
	 */
	public MowerStatus getMowerStatus(int id) {
		return mowerStatuses.get(id);
	}

	/**
	 * get the location of all charging pads
	 */
	public Collection<Location> getChargingLocations() {
		return chargingPadLocations;
	}

//	public Square[][] getSharedSquares() {
//		return sharedSquares;
//	}
//
//	public void setSharedSquares(Square[][] sharedSquares) {
//		this.sharedSquares = sharedSquares;
//	}

}
