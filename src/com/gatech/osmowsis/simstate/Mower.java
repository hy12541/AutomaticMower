package com.gatech.osmowsis.simstate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.gatech.osmowsis.action.Action;
import com.gatech.osmowsis.action.CircularScanAction;
import com.gatech.osmowsis.action.LinearScanAction;
import com.gatech.osmowsis.action.MoveAction;
import com.gatech.osmowsis.action.PassAction;
import com.gatech.osmowsis.action.SteerAction;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;
import com.gatech.osmowsis.strategy.CommunicationChannel;
import com.gatech.osmowsis.strategy.DirectionMap;
import com.gatech.osmowsis.strategy.Knowledge;

// mower
public class Mower {

	private static Random randGenerator;
	private static int energyCapacity;
	private static DirectionMap directionMap = com.gatech.osmowsis.strategy.DirectionMap.getDirectionMap();

	private int id;
	private Location initialLocation = new Location();
	private Location relativeLocation = new Location(0, 0);
	private Location location;
	private Direction direction;
	private MowerStatus status;
	private int energy;
	private int strategy;
	private Action action;
	private boolean isCurrentMower;
	private boolean isNextMower;
	private Map<Location, Square> trackScanResults;
	private Knowledge knowledge = new Knowledge();
	private boolean xCoordinateScanned = false;
	private boolean yCoordinateScanned = false;
	private CommunicationChannel communicationChannel = com.gatech.osmowsis.strategy.CommunicationChannel
			.getCommunicationChannel(); // Singleton

	public Mower() {
		super();
		if (randGenerator == null) {
			randGenerator = new Random();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getInitialLocation() {
		return initialLocation;
	}

	public void setInitialLocation(Location initialLocation) {
		this.initialLocation = initialLocation;
	}

	public void setMowerRelativeLocation(Location absoluteLocation) {
//		this.relativeLocation.setxCoordinate(absoluteLocation.getxCoordinate() - this.initialLocation.getxCoordinate());
//		this.relativeLocation.setyCoordinate(absoluteLocation.getyCoordinate() - this.initialLocation.getyCoordinate());
		this.location = absoluteLocation;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getStrategy() {
		return strategy;
	}

	public boolean isxCoordinateScanned() {
		return xCoordinateScanned;
	}

	public void setxCoordinateScanned(boolean xCoordinateScanned) {
		this.xCoordinateScanned = xCoordinateScanned;
	}

	public boolean isyCoordinateScanned() {
		return yCoordinateScanned;
	}

	public void setyCoordinateScanned(boolean yCoordinateScanned) {
		this.yCoordinateScanned = yCoordinateScanned;
	}

	public static Random getRandGenerator() {
		return randGenerator;
	}

	public static void setRandGenerator(Random randGenerator) {
		Mower.randGenerator = randGenerator;
	}

	public static int getEnergyCapacity() {
		return energyCapacity;
	}

	public static void setEnergyCapacity(int energyCapacity) {
		Mower.energyCapacity = energyCapacity;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Action getTrackAction() {
		return action;
	}

	public void setTrackAction(Action trackAction) {
		this.action = trackAction;
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

	public MowerStatus getStatus() {
		return status;
	}

	public void setStatus(MowerStatus status) {
		this.status = status;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public Map<Location, Square> getTrackScanResults() {
		return trackScanResults;
	}

	public void setTrackScanResults(Map<Location, Square> trackScanResults) {
		this.trackScanResults = trackScanResults;
	}

	@Override
	public String toString() {
		return "Mower [id=" + id + ", initialLocation=" + initialLocation + ", relativeLocation=" + relativeLocation
				+ ", location=" + location + ", direction=" + direction + ", status=" + status + ", energy=" + energy
				+ ", strategy=" + strategy + ", action=" + action + ", isCurrentMower=" + isCurrentMower
				+ ", isNextMower=" + isNextMower + ", trackScanResults=" + trackScanResults + ", knowledge=" + knowledge
				+ ", xCoordinateScanned=" + xCoordinateScanned + ", yCoordinateScanned=" + yCoordinateScanned
				+ ", communicationChannel=" + communicationChannel + "]";
	}

	// return scan results in string format
	public String getStrTrackScanResults() {
		StringBuilder resultString = new StringBuilder();
		// get string of scan results
		if (trackScanResults != null && trackScanResults.size() > 0) {
			for (Map.Entry<Location, Square> entry : trackScanResults.entrySet()) {
				String currentValue = entry.getValue().getSquareState().toString();
				if (resultString.length() == 0) {
					resultString.append(currentValue);
				} else {
					resultString.append("," + currentValue);
				}

			}
		}

		return resultString.toString();
	}

	// return if mower is crashed
	public boolean isCrashed() {
		return this.status == MowerStatus.crash;
	}

	// return if mower has enough energy
	public boolean hasEnergyNeeded(int energyNeeded) {

		return energy - energyNeeded >= 0;
	}

	// reduce energy
	public void reduceEnergy(int energyNeeded) {
		energy = energy - energyNeeded;

	}

	// calculate if mower is out of energy
	public boolean isOutOfEnergy() {
		return this.energy <= 0;
	}

	// restore energy
	public void recharge() {
		energy = energyCapacity;
	}

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}

	// determine next action
	public Action determineNextAction() {
		Action nextAction = null;

		// if out of energy, then return pass action
		if (isOutOfEnergy()) {
			nextAction = new PassAction();
			return nextAction;
		}

		// if strategy is 0, then return random action
		if (strategy == 0) {
			nextAction = getRandomAction();
		}
		// if strategy is 1, then return action by strategy
		else if (strategy == 1) {
			nextAction = getActionByStrategy();

		}

		// if action is null, or mower does not have enough energy to execute action
		// proposed, then return pass action
		if (nextAction == null || !hasEnergyNeeded(nextAction.getEnergyNeeded())) {
			// do nothing
			nextAction = new PassAction();
		}

		return nextAction;
	}

	// determine next action randomly
	private Action getRandomAction() {
		Action nextAction = null;

		// determine a new action
		int actionRandomChoice = randGenerator.nextInt(100);

		if (actionRandomChoice < 25) {
			// check your surroundings
			nextAction = new CircularScanAction(getLocation());

		} else if (actionRandomChoice < 50) {

			// check linear squares along current direction
			nextAction = new LinearScanAction(getLocation(), getDirection());
		} else if (actionRandomChoice < 75) {
			// change direction
			nextAction = new SteerAction(actionRandomChoice % (Direction.values().length - 1));

		} else {
			// move forward
			nextAction = new MoveAction(getLocation(), getDirection());
		}

		return nextAction;

	}

	// determine next action by strategy
	private Action getActionByStrategy() {
		// move to nearest charging pad when energy level is below 40%
		if (this.energy < Mower.energyCapacity * 0.3) {
			Location targetLocation = this.getNearestChargingPadLocation();
			Direction targetDirection = this.determineDirectionTowardTargetLocation(targetLocation);
			// return move action if current directon is same as target direction
			if (this.direction == targetDirection) {
				return new MoveAction(this.getLocation(), this.direction);
				// return steer action
			} else {
				return new SteerAction(targetDirection);
			}
		}
		// check if current direction if safe
		Location targetLocation = Mower.directionMap.getTargetLocation(this.getLocation(), this.direction);
		SquareState targetLocationState = this.communicationChannel.getSquareStateOnLocation(targetLocation);
		// if target location is unknown, perform circular scan
		if (targetLocationState == SquareState.unknown) {
//			for (int i = 0; i < this.getSurroundingLocations().size(); i++) {
//				Location location = this.getSurroundingLocations().get(i);
//				if (this.communicationChannel.getSquareStateOnLocation(location) == SquareState.grass) {
//					return new SteerAction(this.determineDirectionTowardTargetLocation(
//							new Location(location.getxCoordinate(), location.getyCoordinate())));
//				}
//			}
			return new CircularScanAction(this.getLocation());
		}

		// if target location is not safe, steer to another direction
		if (targetLocationState == SquareState.fence || targetLocationState == SquareState.mower
				|| targetLocationState == SquareState.gopher_empty || targetLocationState == SquareState.gopher_grass) {
			// if surrounding squares have grass, steer towards it
			for (int i = 0; i < this.getSurroundingLocations().size(); i++) {
				Location location = this.getSurroundingLocations().get(i);
				if (this.communicationChannel.getSquareStateOnLocation(location) == SquareState.grass) {
					return new SteerAction(this.determineDirectionTowardTargetLocation(
							new Location(location.getxCoordinate(), location.getyCoordinate())));
				}
			}
			// if surrounding squares have fence, turn around
			for (int i = 0; i < this.getSurroundingLocations().size(); i++) {
				Location location = this.getSurroundingLocations().get(i);
				if (this.communicationChannel.getSquareStateOnLocation(location) == SquareState.fence) {
					return new SteerAction(this.determineDirectionTowardTargetLocation(
							new Location(-location.getxCoordinate(), -location.getyCoordinate())));
				}
			}

			int actionRandomChoice = randGenerator.nextInt(100);
			return new SteerAction(actionRandomChoice % (Direction.values().length - 1));
		}

		return new MoveAction(this.getLocation(), this.direction);

	}

	// calculate the nearest recharging pad location
	private Location getNearestChargingPadLocation() {
		return this.communicationChannel.getChargingLocations().stream().min((location1, location2) -> {
			return (int) (Math.pow((location1.getxCoordinate() - this.getLocation().getxCoordinate()), 2)
					+ Math.pow((location1.getyCoordinate() - this.getLocation().getyCoordinate()), 2)
					- Math.pow((location2.getxCoordinate() - this.getLocation().getxCoordinate()), 2)
					- Math.pow((location2.getyCoordinate() - this.getLocation().getyCoordinate()), 2));
		}).get();
	}

	// calculate surrounding locations
	private List<Location> getSurroundingLocations() {
		List<Location> surroundingLocations = new ArrayList<>();

		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate() + 1, this.getLocation().getyCoordinate()));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate() + 1, this.getLocation().getyCoordinate() + 1));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate() + 1, this.getLocation().getyCoordinate() - 1));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate() - 1, this.getLocation().getyCoordinate()));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate() - 1, this.getLocation().getyCoordinate() + 1));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate() - 1, this.getLocation().getyCoordinate() - 1));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate(), this.getLocation().getyCoordinate() + 1));
		surroundingLocations
				.add(new Location(this.getLocation().getxCoordinate(), this.getLocation().getyCoordinate() - 1));

		return surroundingLocations;
	}

	// calculate direction to target location
	private Direction determineDirectionTowardTargetLocation(Location targetLocation) {
		if (targetLocation.getxCoordinate() > this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() > this.getLocation().getyCoordinate()) {
			return Direction.northeast;
		}
		if (targetLocation.getxCoordinate() > this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() == this.getLocation().getyCoordinate()) {
			return Direction.east;
		}
		if (targetLocation.getxCoordinate() > this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() < this.getLocation().getyCoordinate()) {
			return Direction.southeast;
		}
		if (targetLocation.getxCoordinate() == this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() < this.getLocation().getyCoordinate()) {
			return Direction.south;
		}
		if (targetLocation.getxCoordinate() < this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() < this.getLocation().getyCoordinate()) {
			return Direction.southwest;
		}
		if (targetLocation.getxCoordinate() < this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() == this.getLocation().getyCoordinate()) {
			return Direction.west;
		}
		if (targetLocation.getxCoordinate() < this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() > this.getLocation().getyCoordinate()) {
			return Direction.northwest;
		}
		if (targetLocation.getxCoordinate() == this.getLocation().getxCoordinate()
				&& targetLocation.getyCoordinate() > this.getLocation().getyCoordinate()) {
			return Direction.north;
		}
		return null;
	}

	// update knowledge
	public void updateIndividualKnowledge() {
		this.knowledge.setId(this.id);
		this.knowledge.setMowerStatus(this.getStatus());
		this.knowledge.setAbsoluteLocation(this.getLocation());
		this.knowledge.setRechargingPadLocation(this.initialLocation);
		if (this.action instanceof CircularScanAction) {
			// save current square of mower
			this.knowledge.getIndividualKnowledge().put(
					new Location(this.getLocation().getxCoordinate(), this.getLocation().getyCoordinate()),
					new Square(SquareState.empty));

			// save scanned squares of mower
			if (trackScanResults != null && trackScanResults.size() > 0) {
				for (Map.Entry<Location, Square> entry : trackScanResults.entrySet()) {
					this.knowledge.getIndividualKnowledge().put(entry.getKey(), entry.getValue());

				}
			}

			/*
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate(),
			 * this.getLocation().getyCoordinate()), new Square(SquareState.empty));
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate(),
			 * this.getLocation().getyCoordinate() + 1), this.trackScanResults[0]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate() + 1,
			 * this.getLocation().getyCoordinate() + 1), this.trackScanResults[1]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate() + 1,
			 * this.getLocation().getyCoordinate()), this.trackScanResults[2]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate() + 1,
			 * this.getLocation().getyCoordinate() - 1), this.trackScanResults[3]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate(),
			 * this.getLocation().getyCoordinate() - 1), this.trackScanResults[4]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate() - 1,
			 * this.getLocation().getyCoordinate() - 1), this.trackScanResults[5]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate() - 1,
			 * this.getLocation().getyCoordinate()), this.trackScanResults[6]);
			 * this.knowledge.getIndividualKnowledge().put( new
			 * Location(this.getLocation().getxCoordinate() - 1,
			 * this.getLocation().getyCoordinate() + 1), this.trackScanResults[7]);
			 */
		}

	}

	// get previous location
	public Location getPreviousLocation() {
		return directionMap.getPreviousLocation(this.location, this.direction);
	}

	// update shared knowledge
	public void sendInformationToCommunicationChannel() {
		this.communicationChannel.addKnowledgeToCommunicationChannel(this.knowledge);
		if (this.action instanceof MoveAction) {
			this.communicationChannel.getCombinedKnowledgeOnSquareState().put(this.getLocation(),
					new Square(SquareState.mower));
			this.communicationChannel.getCombinedKnowledgeOnSquareState().put(this.getPreviousLocation(),
					new Square(SquareState.empty));
		}
		if (this.action instanceof SteerAction) {
			this.communicationChannel.getCombinedKnowledgeOnSquareState().put(this.getLocation(),
					new Square(SquareState.mower));
		}
		if (this.action instanceof LinearScanAction) {
			this.communicationChannel.getCombinedKnowledgeOnSquareState().putAll(this.trackScanResults);
			this.communicationChannel.getCombinedKnowledgeOnSquareState().put(this.location,
					new Square(SquareState.mower));
		}
		if (this.action instanceof CircularScanAction) {
			// save current square of mower
			this.communicationChannel.getCombinedKnowledgeOnSquareState().put(this.location,
					new Square(SquareState.mower));

			// save scanned squares of mower
			if (trackScanResults != null && trackScanResults.size() > 0) {
				for (Map.Entry<Location, Square> entry : trackScanResults.entrySet()) {
					this.communicationChannel.getCombinedKnowledgeOnSquareState().put(entry.getKey(), entry.getValue());

				}
			}

			/*
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate(),
			 * this.getLocation().getyCoordinate()), new Square(SquareState.empty));
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate(),
			 * this.getLocation().getyCoordinate() + 1), this.trackScanResults[0]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate() + 1,
			 * this.getLocation().getyCoordinate() + 1), this.trackScanResults[1]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate() + 1,
			 * this.getLocation().getyCoordinate()), this.trackScanResults[2]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate() + 1,
			 * this.getLocation().getyCoordinate() - 1), this.trackScanResults[3]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate(),
			 * this.getLocation().getyCoordinate() - 1), this.trackScanResults[4]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate() - 1,
			 * this.getLocation().getyCoordinate() - 1), this.trackScanResults[5]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate() - 1,
			 * this.getLocation().getyCoordinate()), this.trackScanResults[6]);
			 * this.communicationChannel.getCombinedKnowledgeOnSquareState().put( new
			 * Location(this.getLocation().getxCoordinate() - 1,
			 * this.getLocation().getyCoordinate() + 1), this.trackScanResults[7]);
			 */
		}
	}

}
