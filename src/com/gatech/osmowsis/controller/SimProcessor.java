package com.gatech.osmowsis.controller;

import com.gatech.osmowsis.simstate.Gopher;
import com.gatech.osmowsis.simstate.Lawn;
import com.gatech.osmowsis.simstate.Mower;
import com.gatech.osmowsis.simstate.RechargingPad;
import com.gatech.osmowsis.simstate.SimulationState;
import com.gatech.osmowsis.simstate.Size;
import com.gatech.osmowsis.simsystem.SimulationMonitor;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.ui.model.UIGopher;
import com.gatech.osmowsis.ui.model.UILawn;
import com.gatech.osmowsis.ui.model.UIMower;
import com.gatech.osmowsis.ui.model.UIRechargingPad;
import com.gatech.osmowsis.ui.model.UISimState;

// helper class of SimController
public class SimProcessor {

	// read simulation state from simulation monitor
	public static UISimState getUISimState(SimulationMonitor simulationMonitor) {
		UISimState uiSimState = new UISimState();

		if (simulationMonitor != null && simulationMonitor.getSimulationState() != null) {
			if (simulationMonitor.getError() == null) {
				uiSimState = convertToUISimState(simulationMonitor.getSimulationState());
				uiSimState.setUiOutput(simulationMonitor.getRender().getOutputText());
				uiSimState.setSummaryReportShowed(simulationMonitor.isSummaryReportShowed());
				uiSimState.setSimRunCompleted(simulationMonitor.isSimRunCompleted());
				uiSimState.setTerminated(simulationMonitor.isTerminated());
				uiSimState.determineCurrentAndNextUiObject();
				// set all scanned squares
				uiSimState.setAllScannedSquares(simulationMonitor.getSimulationState().getAllScannedSquares());

			} else {
				// save error for GUI
				uiSimState.setUiError("ERROR: " + simulationMonitor.getError().getMesssage());
				// show error in console if there is any error
				System.out.println("ERROR: " + simulationMonitor.getError().getMesssage());

			}
		} else {
			// save error for GUI
			uiSimState.setUiError("ERROR: Test scenario file not found.");
			// show error in console if there is any error
			System.out.println("ERROR: Test scenario file not found.");

		}

		return uiSimState;
	}

	// convert simulation state to ui simulation state
	public static UISimState convertToUISimState(SimulationState simulationState) {
		UISimState uiSimState = null;
		if (simulationState != null) {
			uiSimState = new UISimState();
			Lawn simLawn = simulationState.getLawn();
			uiSimState.setUilawn(convertToUiLawn(simLawn));

			Mower[] simMowers = simulationState.getMowers();
			uiSimState.setUimowers(convertToUiMowers(simMowers));

			Gopher[] simGophers = simulationState.getGophers();
			uiSimState.setUigophers(convertToUiGophers(simGophers));

			RechargingPad[] simRechargingPads = simulationState.getRechargingPads().toArray(new RechargingPad[0]);
			uiSimState.setUiRechargingPads(convertToUiRechargingPads(simRechargingPads));

			uiSimState.setTotalMowerNumber(simulationState.getTotalMowerNumber());
			uiSimState.setTotalGopherNumber(simulationState.getTotalGopherNumber());
			uiSimState.setTotalRechargingPadNumber(simulationState.getTotalRechargingPadNumber());

		}

		return uiSimState;

	}

	// convert simulation lawn to ui lawn
	public static UILawn convertToUiLawn(Lawn simLawn) {
		UILawn uilawn = null;
		if (simLawn != null) {
			int width = simLawn.getSize().getWidth();
			int length = simLawn.getSize().getLength();

			uilawn = new UILawn();
			// set size
			uilawn.setSize(new Size(width, length));

			Square[][] simLawnSquares = simLawn.getLawnSquares();
			Square[][] uilawnSquares = new Square[width][length];
			for (int i = 0; i < width; i++)
				for (int j = 0; j < length; j++) {
					uilawnSquares[i][j] = new Square(simLawnSquares[i][j].getSquareState());
				}

			// set lawn squares
			uilawn.setLawnSquares(uilawnSquares);

		}

		return uilawn;
	}

	// convert simulation mower to ui mower
	public static UIMower[] convertToUiMowers(Mower[] simMowers) {
		UIMower[] uiMowers = null;
		if (simMowers != null && simMowers.length > 0) {
			uiMowers = new UIMower[simMowers.length];

			for (int i = 0; i < simMowers.length; i++) {
				uiMowers[i] = new UIMower();
				uiMowers[i].setId(simMowers[i].getId());
				uiMowers[i].setLocation(simMowers[i].getLocation());
				uiMowers[i].setDirection(simMowers[i].getDirection());
				uiMowers[i].setMowerStatus(simMowers[i].getStatus());
				uiMowers[i].setEnergy(simMowers[i].getEnergy());
				uiMowers[i].setTrackAction(simMowers[i].getTrackAction());
				uiMowers[i].setCurrentMower(simMowers[i].isCurrentMower());
				uiMowers[i].setNextMower(simMowers[i].isNextMower());
			}
		}

		return uiMowers;
	}

	// convert simulation gopher to ui gopher
	public static UIGopher[] convertToUiGophers(Gopher[] simGophers) {
		UIGopher[] uiGophers = null;
		if (simGophers != null && simGophers.length > 0) {
			uiGophers = new UIGopher[simGophers.length];

			for (int i = 0; i < simGophers.length; i++) {
				uiGophers[i] = new UIGopher();
				uiGophers[i].setId(simGophers[i].getId());
				uiGophers[i].setLocation(simGophers[i].getLocation());
				uiGophers[i].setCurrentGopher(simGophers[i].isCurrentGopher());
				uiGophers[i].setNextGopher(simGophers[i].isNextGopher());

			}
		}

		return uiGophers;
	}

	// convert simluation recharging pad to ui recharging pad
	public static UIRechargingPad[] convertToUiRechargingPads(RechargingPad[] simRechargingPads) {
		UIRechargingPad[] uiRechargingPads = null;
		if (simRechargingPads != null && simRechargingPads.length > 0) {
			uiRechargingPads = new UIRechargingPad[simRechargingPads.length];

			for (int i = 0; i < simRechargingPads.length; i++) {
				uiRechargingPads[i] = new UIRechargingPad();
				uiRechargingPads[i].setLocation(simRechargingPads[i].getLocation());

			}
		}
		return uiRechargingPads;

	}

}
