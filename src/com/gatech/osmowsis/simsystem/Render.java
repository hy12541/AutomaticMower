package com.gatech.osmowsis.simsystem;

import com.gatech.osmowsis.action.MoveAction;
import com.gatech.osmowsis.action.PassAction;
import com.gatech.osmowsis.action.CircularScanAction;
import com.gatech.osmowsis.action.LinearScanAction;
import com.gatech.osmowsis.action.SteerAction;
import com.gatech.osmowsis.simstate.Gopher;
import com.gatech.osmowsis.simstate.Lawn;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.Mower;
import com.gatech.osmowsis.simstate.MowerStatus;
import com.gatech.osmowsis.simstate.SimulationState;
import com.gatech.osmowsis.square.SquareState;
import com.gatech.osmowsis.strategy.Knowledge;

// render class for display output
public class Render {
	private StringBuilder outputText;

	public Render() {
		super();
		outputText = new StringBuilder();
	}

	public String getOutputText() {
		return outputText.toString();
	}

	public void setOutputText(String outputText) {
		this.outputText.setLength(0);
		this.outputText.append(outputText);
	}

	public void appendOutputText(String outputText) {
		if (this.outputText.length() > 0) {
			this.outputText.append("\n");
		}

		this.outputText.append(outputText);

	}

	// display lawn in console
	public void renderSimulationState(SimulationState simulationState) {
		if (simulationState.getLawn() != null && simulationState.getMowers() != null) {

			int i, j, k;
			Lawn lawn = simulationState.getLawn();
			int lawnWidth = lawn.getSize().getWidth();
			int lawnHeight = lawn.getSize().getLength();
			int charWidth = 4 * lawnWidth + 2;
			Mower[] mowers = simulationState.getMowers();

			// display the rows of the lawn from top to bottom
			for (j = lawnHeight - 1; j >= 0; j--) {
				renderHorizontalBar(charWidth);

				// display the Y-direction identifier
				System.out.print(j);

				// display the contents of each square on this row
				for (i = 0; i < lawnWidth; i++) {
					System.out.print("|");

					boolean print = false;

					// display mowers
					for (k = 0; k < mowers.length; k++) {
						if ((mowers[k].getStatus() == MowerStatus.ok) && mowers[k].getLocation().getxCoordinate() == i
								&& mowers[k].getLocation().getyCoordinate() == j) {
							System.out.print(mowers[k].getId() + " ");
							print = true;
						}
					}

					if (!print) {
						// display square state
						SquareState state = lawn.getLawnSquare(i, j).getSquareState();
						if (state == SquareState.empty) {
							System.out.print("  ");
						} else if (state == SquareState.grass) {
							System.out.print("g ");
						} else if (state == SquareState.gopher_grass) {
							System.out.print("gg");
						} else if (state == SquareState.gopher_empty) {
							System.out.print("ge");
						}

					}

				}
				System.out.println("|");
			}
			renderHorizontalBar(charWidth);

			// display the column X-direction identifiers
			System.out.print(" ");
			for (i = 0; i < lawnWidth; i++) {
				System.out.print("  " + i);
			}
			System.out.println("");

			// display the mower's direction and engergy
			for (k = 0; k < mowers.length; k++) {
				if (mowers[k].getStatus() == MowerStatus.crash) {
					continue;
				}
				System.out.println("dir m" + String.valueOf(k) + ": " + mowers[k].getDirection().toString());
				System.out.println("energy m" + String.valueOf(k) + ": " + mowers[k].getEnergy());
			}
			System.out.println("");

		}
	}

	// display knowledge in console
	public void renderKnowledge(Mower curMower) {
		if (curMower != null && curMower.getKnowledge() != null && curMower.getKnowledge().getLawn() != null) {

			int i, j;
			Knowledge knowledge = curMower.getKnowledge();
			Lawn lawn = curMower.getKnowledge().getLawn();
			int lawnWidth = lawn.getSize().getWidth();
			int lawnHeight = lawn.getSize().getLength();
			int charWidth = 2 * lawnWidth + 2;

			// display the rows of the lawn from top to bottom
			for (j = lawnHeight - 1; j >= 0; j--) {
				renderHorizontalBar(charWidth);

				// display the Y-direction identifier
				System.out.print(j);

				// display the contents of each square on this row
				for (i = 0; i < lawnWidth; i++) {
					System.out.print("|");

					boolean print = false;

					// display mower
					if (knowledge.getLocation().getxCoordinate() == i
							&& knowledge.getLocation().getyCoordinate() == j) {
						if (curMower.getStatus() == MowerStatus.ok) {
							System.out.print(curMower.getId());
						} else {
							System.out.print("#");
						}

						print = true;
					}

					if (!print) {
						// display square state
						SquareState state = lawn.getLawnSquare(i, j).getSquareState();
						if (state == SquareState.empty) {
							System.out.print("  ");
						} else if (state == SquareState.grass) {
							System.out.print("g ");
						} else if (state == SquareState.gopher_grass) {
							System.out.print("gg");
						} else if (state == SquareState.gopher_empty) {
							System.out.print("ge");
						} else if (state == SquareState.fence) {
							System.out.print("f ");
						} else if (state == SquareState.mower) {
							System.out.print("m ");
						} else if (state == SquareState.unknown) {
							System.out.print("u ");
						}
					}

				}

				System.out.println("|");
			}
			renderHorizontalBar(charWidth);

			// display the column X-direction identifiers
			System.out.print(" ");
			for (i = 0; i < lawnWidth; i++) {
				System.out.print(" " + i);
			}
			System.out.println("");

		}
	}

	private void renderHorizontalBar(int size) {
		System.out.print(" ");
		for (int k = 0; k < size; k++) {
			System.out.print("-");
		}
		System.out.println("");
	}

	// display output of mower in console
	public void displayMowerActionAndResponses(Mower mower) {
		System.out.println(getMowerActionAndResponsesStr(mower));

	}

	// return output of mower action and response
	public String getMowerActionAndResponsesStr(Mower mower) {
		StringBuilder sb = new StringBuilder();
		if (mower != null) {
			// output of mower's actions
			sb.append("m" + String.valueOf(mower.getId()) + "," + mower.getTrackAction().getActionName());

			// display direction if it is steer action
			if (mower.getTrackAction() instanceof SteerAction) {
				sb.append("," + mower.getDirection().toString() + "\n");

			} else {
				sb.append("\n");
			}

			// output of simulation checks and/or responses
			if (mower.getTrackAction() instanceof MoveAction || mower.getTrackAction() instanceof SteerAction
					|| mower.getTrackAction() instanceof PassAction) {
				// output of mower's status - either ok or crash
				sb.append(mower.getStatus().toString());

			} else if (mower.getTrackAction() instanceof CircularScanAction
					|| mower.getTrackAction() instanceof LinearScanAction) {
				// output of scan results
				sb.append(mower.getStrTrackScanResults());

			} else {
				sb.append("Action is not recognized");
			}
		}

		return sb.toString();

	}

	// display output of gopher in console
	public void displayGopherActionAndResponses(Gopher gopher) {
		System.out.println(getGopherActionAndResponsesStr(gopher));

	}

	// return output of gopher action and response
	public String getGopherActionAndResponsesStr(Gopher gopher) {
		StringBuilder sb = new StringBuilder();
		if (gopher != null && gopher.getMowerSelected() != null && gopher.getLocation() != null) {
			Mower mowerSelected = gopher.getMowerSelected();
			Location destination = gopher.getLocation();

			// output of gopher's id, target mower and location
			sb.append("g" + gopher.getId() + "," + "m" + mowerSelected.getId() + "," + destination.toString() + "\n");

			// output of gopher status - ok
			sb.append(gopher.getStatus());

		}

		return sb.toString();

	}

}
