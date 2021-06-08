package com.gatech.osmowsis.simsystem;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gatech.osmowsis.action.Action;
import com.gatech.osmowsis.action.LinearScanAction;
import com.gatech.osmowsis.action.SteerAction;
import com.gatech.osmowsis.simstate.Direction;
import com.gatech.osmowsis.simstate.Gopher;
import com.gatech.osmowsis.simstate.GopherStatus;
import com.gatech.osmowsis.simstate.Lawn;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.Mower;
import com.gatech.osmowsis.simstate.MowerStatus;
import com.gatech.osmowsis.simstate.RechargingPad;
import com.gatech.osmowsis.simstate.SimulationState;
import com.gatech.osmowsis.simstate.Size;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;

public class SimulationMonitor {

	private ScenarioFile scenarioFile;
	private LogFile logFile;
	private SimulationState simulationState;
	private boolean showState;
	private Render render;
	private OsMowSisError error;
	private Map<Integer, Location> initialMowerLocation;
	private Set<RechargingPad> rechargingPads;

	private boolean simRunCompleted;
	private boolean terminated;
	private boolean summaryReportShowed;
	private boolean turnCompleted;

	private Mower nextMower;
	private Gopher nextGopher;

	public SimulationMonitor(String testFileName, Boolean showState) {
		super();

		// initialize fields
		initFields();
		// set flag to show lawn state
		this.showState = showState;

		initialize(testFileName);

	}

	// initialize fields
	private void initFields() {
		scenarioFile = new ScenarioFile();
		logFile = new LogFile();
		simulationState = new SimulationState();
		showState = false;
		render = new Render();
		error = null;
		initialMowerLocation = new HashMap<>();
		rechargingPads = new HashSet<>();
		simRunCompleted = false;
		terminated = false;
		summaryReportShowed = false;
		turnCompleted = false;
		nextMower = null;
		nextGopher = null;
	}

	// reset fileds
	private void resetFields() {
		logFile = new LogFile();
		simulationState = new SimulationState();
		showState = false;
		render = new Render();
		error = null;
		initialMowerLocation = new HashMap<>();
		rechargingPads = new HashSet<>();
		simRunCompleted = false;
		terminated = false;
		summaryReportShowed = false;
		turnCompleted = false;
		nextMower = null;
		nextGopher = null;
	}

	public boolean isSimRunCompleted() {
		return simRunCompleted;
	}

	public void setSimRunCompleted(boolean simRunCompleted) {
		this.simRunCompleted = simRunCompleted;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public Render getRender() {
		return render;
	}

	public void setRender(Render render) {
		this.render = render;
	}

	public boolean isSummaryReportShowed() {
		return summaryReportShowed;
	}

	public void setSummaryReportShowed(boolean summaryReportShowed) {
		this.summaryReportShowed = summaryReportShowed;
	}

	public SimulationState getSimulationState() {
		return simulationState;
	}

	public void setSimulationState(SimulationState simulationState) {
		this.simulationState = simulationState;
	}

	public OsMowSisError getError() {
		return error;
	}

	public void setError(OsMowSisError error) {
		this.error = error;
	}

	// initialize simulation state by scenario file read by command
	public void initialize(String testFileName) {

		// init the communication channel
		com.gatech.osmowsis.strategy.CommunicationChannel.getCommunicationChannel();

		scenarioFile.readTestFile(testFileName);

		if (scenarioFile.getError() == null) {
			// setup initial simulation state
			setupInitState();

		} else {

			// set error if there is any error after read test file
			setError(scenarioFile.getError());
		}
	}

	// initialize simulation state by scenario file read by gui
	public void initializeByFile(File testFile) {

		// init the communication channel
		com.gatech.osmowsis.strategy.CommunicationChannel.getCommunicationChannel();

		scenarioFile.readTestFile(testFile);

		if (scenarioFile.getError() == null) {
			// setup initial simulation state
			setupInitState();

		} else {

			// set error if there is any error after read test file
			setError(scenarioFile.getError());
		}
	}

	// setup initialized state
	private void setupInitState() {
		try {

			if (scenarioFile != null && scenarioFile.getLines() != null && scenarioFile.getLines().size() > 0) {

				String[] tokens;
				List<String> lines = scenarioFile.getLines();
				int currentLine = 0;

				// read in information of lawn size
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int lawnWidth = Integer.parseInt(tokens[0]);
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int lawnHeight = Integer.parseInt(tokens[0]);
				Size lawnSize = new Size(lawnWidth, lawnHeight);

				// generate information of lawn squares
				Square[][] lawnSquares = new Square[lawnWidth][lawnHeight];
				for (int i = 0; i < lawnWidth; i++) {
					for (int j = 0; j < lawnHeight; j++) {
						lawnSquares[i][j] = new Square(SquareState.grass);
					}
				}

				// read in information of lawn mowers
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int numberOfMowers = Integer.parseInt(tokens[0]);
				Mower[] lawnMowers = new Mower[numberOfMowers];

				for (int k = 0; k < numberOfMowers; k++) {
					tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
					int x = Integer.parseInt(tokens[0]);
					int y = Integer.parseInt(tokens[1]);

					// set information of lawn mower
					lawnMowers[k] = new Mower();
					lawnMowers[k].setId(k);

					// Instead of store the initial mower directly to mower object, the initial
					// location is kept in the initialMowerLocation field. Mowers need to get its
					// absolute location by scanning
					this.initialMowerLocation.put(lawnMowers[k].getId(), new Location(x, y));

					lawnMowers[k].setLocation(new Location(x, y));
					lawnMowers[k].setDirection(Direction.valueOf(tokens[2]));
					lawnMowers[k].setStrategy(Integer.parseInt(tokens[3]));
					lawnMowers[k].setStatus(MowerStatus.ok);
					lawnMowers[k].getKnowledge().setRechargingPadLocation(new Location(x, y));

					// set information of recharging pad
					this.rechargingPads.add(new RechargingPad(new Location(x, y)));

					// mow the grass at the initial location
					lawnSquares[x][y].setSquareState(SquareState.empty);
				}

				// read in information of max energy capacity
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int energy = Integer.parseInt(tokens[0]);
				// set information of max energy capacity
				Mower.setEnergyCapacity(energy);

				// recharge lawn mowers
				for (int l = 0; l < numberOfMowers; l++) {
					lawnMowers[l].recharge();
				}

				// read in information of gophers
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int numOfGophers = Integer.parseInt(tokens[0]);
				Gopher[] lawnGophers = new Gopher[numOfGophers];
				for (int m = 0; m < numOfGophers; m++) {
					tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
					int x = Integer.parseInt(tokens[0]);
					int y = Integer.parseInt(tokens[1]);

					// set information of gophers
					lawnGophers[m] = new Gopher();
					lawnGophers[m].setId(m);
					lawnGophers[m].setLocation(new Location(x, y));
					lawnGophers[m].setStatus(GopherStatus.ok);

					// update current square state
					lawnSquares[x][y].setSquareState(SquareState.gopher_grass);
				}

				// read in information of gopher period
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int gopherPeriod = Integer.parseInt(tokens[0]);
				// set information of gopher period
				Gopher.setPeriod(gopherPeriod);

				// read in information of turn limit
				tokens = lines.get(currentLine++).split(ScenarioFile.DELIMITER);
				int turnLimit = Integer.parseInt(tokens[0]);

				// set initial state of lawn
				simulationState.setLawn(new Lawn(lawnSize, lawnSquares));

				// set initial state of mowers
				simulationState.setTotalMowerNumber(numberOfMowers);
				simulationState.setMowers(lawnMowers);

				// set initial state of recharging pads
				simulationState.setRechargingPads(rechargingPads);

				// set initial state of gophers
				simulationState.setTotalGopherNumber(numOfGophers);
				simulationState.setGophers(lawnGophers);

				// set initial state of turn number
				simulationState.setMaxTurnNumber(turnLimit);
				simulationState.setTurnCompletedNumber(0);

				// set next object
				lawnMowers[0].setNextMower(true);
				;

			} else {
				// set error
				setError(OsMowSisError.INVALID_FILE_INFORMATION);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// set error
			setError(OsMowSisError.INVALID_FILE_INFORMATION);
		}

	}

	// begin simulation run
	public void begin() {

		if (simulationState != null && simulationState.getMowers() != null && simulationState.getLawn() != null) {
			// get all mowers
			Mower[] mowers = simulationState.getMowers();
			// get all gohers
			Gopher[] gophers = simulationState.getGophers();

			// render the initial state of the lawn
			/*
			 * if (showState) { render.renderSimulationState(simulationState); }
			 */

			// begin turns
			while (!simulationState.isHalted()) {

				// iterate mowers
				for (int i = 0; i < simulationState.getTotalMowerNumber(); i++) {

					// check if mower is crashed or out of energy
					if (mowers[i].isCrashed() || mowers[i].isOutOfEnergy()) {
						continue;
					}

					if (mowers[i].getStrategy() == 1) {
						// use lscan to get absolute location if absolute location is still unknown
						if (!mowers[i].isxCoordinateScanned()) {
							if (mowers[i].getDirection() == Direction.west) {
								LinearScanAction lscan = new LinearScanAction(
										this.initialMowerLocation.get(mowers[i].getId()), Direction.west);
								mowers[i].getInitialLocation().setxCoordinate(lscan.scan(simulationState).size() - 1);
								mowers[i].setxCoordinateScanned(true);
								simulationState.validateMowerAction(new LinearScanAction(
										this.initialMowerLocation.get(mowers[i].getId()), Direction.west), mowers[i]);
								render.displayMowerActionAndResponses(mowers[i]);
								continue;
							} else {
								// steer to west
								simulationState.validateMowerAction(new SteerAction(Direction.west), mowers[i]);
								render.displayMowerActionAndResponses(mowers[i]);
								continue;
							}
						}
						if (!mowers[i].isyCoordinateScanned()) {
							if (mowers[i].getDirection() == Direction.south) {
								LinearScanAction lscan = new LinearScanAction(
										this.initialMowerLocation.get(mowers[i].getId()), Direction.south);
								mowers[i].getInitialLocation().setyCoordinate(lscan.scan(simulationState).size() - 1);
								mowers[i].setyCoordinateScanned(true);
								simulationState.validateMowerAction(new LinearScanAction(
										this.initialMowerLocation.get(mowers[i].getId()), Direction.south), mowers[i]);
								render.displayMowerActionAndResponses(mowers[i]);
								continue;
							} else {
								simulationState.validateMowerAction(new SteerAction(Direction.south), mowers[i]);
								render.displayMowerActionAndResponses(mowers[i]);
								continue;
							}
						}
					}

					// determine next action
					Action actionProposed = mowers[i].determineNextAction();

					// validate action proposed by mower
					boolean isMowerActionValid = simulationState.validateMowerAction(actionProposed, mowers[i]);

					// save knowledge if mower is not crashed
					if (mowers[i].getStatus() == MowerStatus.ok && mowers[i].getStrategy() == 1) {
						mowers[i].updateIndividualKnowledge();
						mowers[i].sendInformationToCommunicationChannel();
					}

					// display states of mower and action if action is recognized
					if (isMowerActionValid) {
						render.displayMowerActionAndResponses(mowers[i]);
					}

					// render the state of the lawn after each command
					if (showState) {
						render.renderSimulationState(simulationState);
						// render.renderKnowledge(mowers[i]);
					}
				}

				// iterate gophers
				if (simulationState.isGopherTurn()) {
					for (int j = 0; j < simulationState.getTotalGopherNumber(); j++) {

						// if all mowers are removed, end iteration of gophers
						if (simulationState.mowersAllRemoved()) {
							break;
						}

						// determine mower selected
						gophers[j].determineMowerSelected(simulationState);

						// determine next action
						Action actionProposed = gophers[j].determinNextAction();

						// validate action proposed by gopher
						boolean isGopherActionValid = simulationState.validateGopherAction(actionProposed, gophers[j]);

						// display states of gopher and action if gopher moves
						if (isGopherActionValid) {
							render.displayGopherActionAndResponses(gophers[j]);
						}

						// render the state of the lawn after each command
						if (showState) {
							render.renderSimulationState(simulationState);
							// render.renderKnowledge(mowers[i]);

						}

					}
				}

				// increment turn completed number
				simulationState.incrementTurnCompletedNumber();

				// display turn completed number
				/*
				 * if (showState) {
				 * System.out.println("Turn#"+simulationState.getTurnCompletedNumber()+
				 * " Completed!"); System.out.println(); }
				 */
			}

			// generate and display final report
			simulationState.generateSummaryReport().display();

		}
	}

	public void startByFile(File testFile) {
		// initialize fields
		initFields();
		// initialize simulation state by file
		initializeByFile(testFile);
	}

	public void restart() {
		// reset fields
		resetFields();
		// initialize simulation state by existing file
		setupInitState();
	}

	// execute simulation run one step at a time
	public void next() {

		if (!simRunCompleted && simulationState != null && simulationState.getMowers() != null
				&& simulationState.getLawn() != null) {

			// get all mowers
			Mower[] mowers = simulationState.getMowers();
			// get all gohers
			Gopher[] gophers = simulationState.getGophers();

			// get next object
			nextMower = getNextMower(mowers);
			nextGopher = null;
			if (nextMower == null) {
				nextGopher = getNextGopher(gophers);

				if (nextGopher == null) {
					nextMower = mowers[0];
					nextMower.setNextMower(true);
				}
			}

			// if turn was completed in last step
			if (turnCompleted) {
				turnCompleted = false;
				// reset All Scanned Squares
				simulationState.setAllScannedSquares(null);

			}

			resetcurrentMowerInLastStep(mowers);
			resetcurrentGopherInLastStep(gophers);
			resetRenderOutput();

			// poll next object and execute its action
			if (!simulationState.isHalted() && !terminated) {

				// iterate mowers
				while (nextMower != null) {
					// set current object
					Mower currentMower = nextMower;

					currentMower.setCurrentMower(true);
					// reset last nextObject
					currentMower.setNextMower(false);

					if (currentMower.getStrategy() == 1) {
						// use lscan to get absolute location if absolute location is still unknown
						if (!currentMower.isxCoordinateScanned()) {
							if (currentMower.getDirection() == Direction.west) {
								LinearScanAction lscan = new LinearScanAction(
										this.initialMowerLocation.get(currentMower.getId()), Direction.west);
								currentMower.getInitialLocation()
										.setxCoordinate(lscan.scan(simulationState).size() - 1);
								currentMower.setxCoordinateScanned(true);
								simulationState.validateMowerAction(
										new LinearScanAction(this.initialMowerLocation.get(currentMower.getId()),
												Direction.west),
										currentMower);
								updateNextObjectForNextStep(currentMower, null);
								// output to console
								render.displayMowerActionAndResponses(currentMower);
								// output to GUI
								render.appendOutputText(render.getMowerActionAndResponsesStr(currentMower));
								// save knowledge
								if (currentMower.getStatus() == MowerStatus.ok) {
									currentMower.updateIndividualKnowledge();
									currentMower.sendInformationToCommunicationChannel();
								}
								// output to Log file
								logFile.appendLogText(render.getMowerActionAndResponsesStr(currentMower));

								if (showState) {
									// display output to console
									render.renderSimulationState(simulationState);
								}

								if (determineIncrementTurnCompletedNumber(currentMower, null)) {
									// increment turn completed number
									simulationState.incrementTurnCompletedNumber();
									// indicate this turn is completed in this step
									turnCompleted = true;
								}
								if (simulationState.isHalted()) {
									showSummaryReport();
									summaryReportShowed = true;

									// clear all scanned squares
									simulationState.setAllScannedSquares(null);
								}
								return;
							} else {
								// steer to west
								simulationState.validateMowerAction(new SteerAction(Direction.west), currentMower);
								updateNextObjectForNextStep(currentMower, null);
								// output to console
								render.displayMowerActionAndResponses(currentMower);
								// output to GUI
								render.appendOutputText(render.getMowerActionAndResponsesStr(currentMower));
								// output to log file
								logFile.appendLogText(render.getMowerActionAndResponsesStr(currentMower));

								if (showState) {
									// display output to console
									render.renderSimulationState(simulationState);
								}
								if (determineIncrementTurnCompletedNumber(currentMower, null)) {
									// increment turn completed number
									simulationState.incrementTurnCompletedNumber();
									// indicate this turn is completed in this step
									turnCompleted = true;
								}
								if (simulationState.isHalted()) {
									// show summary report
									showSummaryReport();
									summaryReportShowed = true;

									// clear all scanned squares
									simulationState.setAllScannedSquares(null);
								}
								return;
							}
						}
						if (!currentMower.isyCoordinateScanned()) {
							if (currentMower.getDirection() == Direction.south) {
								LinearScanAction lscan = new LinearScanAction(
										this.initialMowerLocation.get(currentMower.getId()), Direction.south);
								currentMower.getInitialLocation()
										.setyCoordinate(lscan.scan(simulationState).size() - 1);
								currentMower.setyCoordinateScanned(true);
								simulationState.validateMowerAction(
										new LinearScanAction(this.initialMowerLocation.get(currentMower.getId()),
												Direction.south),
										currentMower);
								updateNextObjectForNextStep(currentMower, null);
								// output to console
								render.displayMowerActionAndResponses(currentMower);
								// output to GUI
								render.appendOutputText(render.getMowerActionAndResponsesStr(currentMower));
								// save knowledge
								if (currentMower.getStatus() == MowerStatus.ok) {
									currentMower.updateIndividualKnowledge();
									currentMower.sendInformationToCommunicationChannel();
								}
								// output to Log file
								logFile.appendLogText(render.getMowerActionAndResponsesStr(currentMower));
								if (showState) {
									// display output to console
									render.renderSimulationState(simulationState);
								}
								if (determineIncrementTurnCompletedNumber(currentMower, null)) {
									// increment turn completed number
									simulationState.incrementTurnCompletedNumber();
									// indicate this turn is completed in this step
									turnCompleted = true;
								}
								if (simulationState.isHalted()) {
									// show summary report
									showSummaryReport();
									summaryReportShowed = true;

									// clear all scanned squares
									simulationState.setAllScannedSquares(null);
								}
								return;
							} else {
								simulationState.validateMowerAction(new SteerAction(Direction.south), currentMower);
								updateNextObjectForNextStep(currentMower, null);
								// output to console
								render.displayMowerActionAndResponses(currentMower);
								// output to GUI
								render.appendOutputText(render.getMowerActionAndResponsesStr(currentMower));
								// output to Log file
								logFile.appendLogText(render.getMowerActionAndResponsesStr(currentMower));
								if (showState) {
									// display output to console
									render.renderSimulationState(simulationState);
								}
								if (determineIncrementTurnCompletedNumber(currentMower, null)) {
									// increment turn completed number
									simulationState.incrementTurnCompletedNumber();
									// indicate this turn is completed in this step
									turnCompleted = true;
								}

								if (simulationState.isHalted()) {
									// show summary report
									showSummaryReport();
									summaryReportShowed = true;

									// clear all scanned squares
									simulationState.setAllScannedSquares(null);
								}
								return;
							}
						}
					}
					// determine next action
					Action actionProposed = currentMower.determineNextAction();

					// validate action proposed by mower
					boolean isMowerActionRecognized = simulationState.validateMowerAction(actionProposed, currentMower);

					// save knowledge if mower is not crashed
					if (currentMower.getStatus() == MowerStatus.ok) {
						currentMower.updateIndividualKnowledge();
						currentMower.sendInformationToCommunicationChannel();
					}

					// determine next object based on current object
					updateNextObjectForNextStep(currentMower, null);

					// display states of mower and action if action is recognized
					if (isMowerActionRecognized) {

						// output to console
						render.displayMowerActionAndResponses(currentMower);
						// output to GUI
						render.appendOutputText(render.getMowerActionAndResponsesStr(currentMower));
						// output to Log file
						logFile.appendLogText(render.getMowerActionAndResponsesStr(currentMower));

						// render the state of the lawn after each command
						if (showState) {
							// display output to console
							render.renderSimulationState(simulationState);

						}

						if (determineIncrementTurnCompletedNumber(currentMower, null)) {
							// increment turn completed number
							simulationState.incrementTurnCompletedNumber();
							// indicate this turn is completed in this step
							turnCompleted = true;

							// display turn completed number
							/*
							 * if (showState) {
							 * System.out.println("Turn#"+simulationState.getTurnCompletedNumber()+
							 * " Completed!"); System.out.println(); }
							 */
						}

						if (simulationState.isHalted()) {
							// show summary report
							showSummaryReport();
							summaryReportShowed = true;

							// clear all scanned squares
							simulationState.setAllScannedSquares(null);

						}

						return;
					}

				}

				// iterate mowers
				while (simulationState.isGopherTurn() && nextGopher != null) {
					// set current object
					Gopher currentGopher = nextGopher;

					currentGopher.setCurrentGopher(true);
					// reset last nextObject
					currentGopher.setNextGopher(false);

					// determine next object based on current object
					updateNextObjectForNextStep(null, currentGopher);

					// if all mowers are removed, end iteration of gophers
					if (simulationState.mowersAllRemoved()) {
						// increment turn completed number
						simulationState.incrementTurnCompletedNumber();

						// display turn completed number
						/*
						 * if (showState) {
						 * System.out.println("Turn#"+simulationState.getTurnCompletedNumber()+
						 * " Completed!"); System.out.println(); }
						 */

						// show summary report
						showSummaryReport();
						summaryReportShowed = true;
						// clear all scanned squares
						simulationState.setAllScannedSquares(null);
						return;
					}

					// determine mower selected
					currentGopher.determineMowerSelected(simulationState);

					// determine next action
					Action actionProposed = currentGopher.determinNextAction();

					// validate action proposed by gopher
					boolean isGopherActionRecognized = simulationState.validateGopherAction(actionProposed,
							currentGopher);

					// display states of gopher and action if action is recognized
					if (isGopherActionRecognized) {

						// output to console
						render.displayGopherActionAndResponses(currentGopher);
						// output to GUI
						render.appendOutputText(render.getGopherActionAndResponsesStr(currentGopher));
						// output to Log file
						logFile.appendLogText(render.getGopherActionAndResponsesStr(currentGopher));

						// render the state of the lawn after each command
						if (showState) {
							// display output to console
							render.renderSimulationState(simulationState);
						}

						if (determineIncrementTurnCompletedNumber(null, currentGopher)) {
							// increment turn completed number
							simulationState.incrementTurnCompletedNumber();
							// indicate this turn is completed in this step
							turnCompleted = true;

							// display turn completed number
							/*
							 * if (showState) {
							 * System.out.println("Turn#"+simulationState.getTurnCompletedNumber()+
							 * " Completed!"); System.out.println(); }
							 */
						}

						if (simulationState.isHalted()) {
							// show summary report
							showSummaryReport();
							summaryReportShowed = true;
							// clear all scanned squares
							simulationState.setAllScannedSquares(null);
						}

						return;
					}

				}

			} else {

				if (!summaryReportShowed) {
					// show summary report
					showSummaryReport();
					summaryReportShowed = true;

					// clear all scanned squares
					simulationState.setAllScannedSquares(null);
				}

				else {
					// set simulation run completed
					simRunCompleted = true;

				}

			}

		}
	}

	// update next object for next step
	private void updateNextObjectForNextStep(Mower currentMower, Gopher currentGopher) {
		nextMower = null;
		nextGopher = null;
		boolean startOver = false;

		if (currentMower != null) {
			nextMower = determinNextMower(currentMower, startOver);
			if (nextMower == null) {
				if (simulationState.isGopherTurn()) {
					nextGopher = simulationState.getGophers()[0];
					nextGopher.setNextGopher(true);
					return;
				} else {
					startOver = true;
					nextMower = determinNextMower(simulationState.getMowers()[0], startOver);
					if (nextMower != null) {
						nextMower.setNextMower(true);
						return;
					} else {
						return;
					}

				}
			} else {
				nextMower.setNextMower(true);
				return;
			}
		} else if (currentGopher != null) {
			nextGopher = determinNextGopher(currentGopher);
			if (nextGopher == null) {
				startOver = true;
				nextMower = determinNextMower(simulationState.getMowers()[0], startOver);
				if (nextMower != null) {
					nextMower.setNextMower(true);
					return;
				} else {
					return;
				}

			} else {
				nextGopher.setNextGopher(true);
				return;
			}
		}

	}

	// determine next mower
	private Mower determinNextMower(Mower currentMower, boolean startOver) {
		Mower nextMower = null;
		int nextMowerId;
		if (currentMower != null && simulationState != null) {
			// set next mower id
			if (startOver) {
				nextMowerId = currentMower.getId();
			} else {
				nextMowerId = currentMower.getId() + 1;
			}

			while (nextMowerId < simulationState.getTotalMowerNumber()) {
				Mower tempMower = simulationState.getMowers()[nextMowerId];
				if (tempMower.getStatus() == MowerStatus.ok) {
					nextMower = tempMower;
					break;
				} else {
					nextMowerId++;
				}
			}
		}
		return nextMower;
	}

	// determine next gopher
	private Gopher determinNextGopher(Gopher currentGopher) {
		Gopher nextGopher = null;
		if (currentGopher != null && simulationState != null) {
			if (currentGopher.getId() < simulationState.getTotalGopherNumber() - 1) {
				nextGopher = simulationState.getGophers()[currentGopher.getId() + 1];
			}
		}

		return nextGopher;
	}

	// get next mower
	private Mower getNextMower(Mower[] mowers) {
		Mower nextMower = null;
		if (mowers != null && mowers.length > 0) {
			for (int i = 0; i < mowers.length; i++) {
				if (mowers[i].isNextMower()) {
					nextMower = mowers[i];
					break;
				}
			}
		}

		return nextMower;
	}

	// get next gopher
	private Gopher getNextGopher(Gopher[] gophers) {
		Gopher nextGopher = null;
		if (gophers != null && gophers.length > 0) {
			for (int i = 0; i < gophers.length; i++) {
				if (gophers[i].isNextGopher()) {
					nextGopher = gophers[i];
					break;
				}
			}
		}

		return nextGopher;
	}

	// reset current mower from last step
	private void resetcurrentMowerInLastStep(Mower[] mowers) {
		if (mowers != null && mowers.length > 0) {
			for (int i = 0; i < mowers.length; i++) {
				mowers[i].setCurrentMower(false);
			}
		}

	}

	private void resetcurrentGopherInLastStep(Gopher[] gophers) {
		if (gophers != null && gophers.length > 0) {
			for (int i = 0; i < gophers.length; i++) {
				gophers[i].setCurrentGopher(false);
			}
		}

	}

	// reset gui output
	private void resetRenderOutput() {
		if (render != null) {
			render.setOutputText("");
		}

	}

	// calculate turn completed number
	private boolean determineIncrementTurnCompletedNumber(Mower currentMower, Gopher currentGopher) {
		boolean incrementTurnCompletedNumber = false;
		int maxMowerId = simulationState.getMaxIdInAvailableMowers();

		// no available mowers, terminate the turn
		if (maxMowerId == -1) {
			incrementTurnCompletedNumber = true;
		} else if (currentMower != null) {
			// execute all available mowers
			if (currentMower.getId() >= maxMowerId) {
				if (!simulationState.isGopherTurn()) {
					incrementTurnCompletedNumber = true;
				}

			}
		} else if (currentGopher != null) {
			if (currentGopher.getId() >= simulationState.getTotalGopherNumber() - 1) {
				incrementTurnCompletedNumber = true;

			}
		}

		return incrementTurnCompletedNumber;

	}

	// generate and output final report
	private void showSummaryReport() {
		// generate summary report
		SummaryReport summaryReport = simulationState.generateSummaryReport();
		// output summary report to console
		summaryReport.display();

		// output summary report to GUI
		render.appendOutputText(summaryReport.toString());
		// output summary report to Log file
		logFile.appendLogText(summaryReport.toString());
		// write to log file
		logFile.writeToLog();

		// output log description to GUI
		render.appendOutputText("\n" + logFile.getDescription());
	}

}
