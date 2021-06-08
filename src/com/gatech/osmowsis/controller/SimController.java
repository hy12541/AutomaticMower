package com.gatech.osmowsis.controller;

import java.io.File;
import com.gatech.osmowsis.simsystem.SimulationMonitor;
import com.gatech.osmowsis.ui.model.UISimState;
import com.gatech.osmowsis.ui.service.ModelService;

public class SimController {

	private SimulationMonitor simulationMonitor;

	// initialize simulation state
	public void initialize(String testFileName, Boolean showState) {
		UISimState uiSimState = null;

		// read and parse test file
		// setup initial simulation state
		simulationMonitor = new SimulationMonitor(testFileName, showState);

		// convert to UI simulation state
		uiSimState = SimProcessor.getUISimState(simulationMonitor);

		// set ui models in service
		ModelService.setUISimState(uiSimState);

		// set SimController in service
		ModelService.setSimController(this);

	}

	// poll next object
	public void next() {
		UISimState uiSimState = null;

		if (simulationMonitor != null) {

			// poll next object and execute its action
			simulationMonitor.next();
		}

		// convert to UI simulation state
		uiSimState = SimProcessor.getUISimState(simulationMonitor);
		// update UI models
		ModelService.setUISimState(uiSimState);

	}

	// terminate simulation run
	public void stop() {

		if (simulationMonitor != null) {
			// update value in SimState
			simulationMonitor.setTerminated(true);
		}

	}

	// restart current simulation run by reading scenario file from GUI
	public void startByFile(File testFile) {

		UISimState uiSimState = null;
		if (simulationMonitor != null) {
			// re-initialize value in SimState
			simulationMonitor.startByFile(testFile);
			;

			// convert to UI simulation state
			uiSimState = SimProcessor.getUISimState(simulationMonitor);

			// set ui models in service
			ModelService.setUISimState(uiSimState);

		}

	}

	// restart current simulation run based on existing scenario file
	public void restart() {

		UISimState uiSimState = null;
		if (simulationMonitor != null) {
			// re-initialize value in SimState
			simulationMonitor.restart();

			// convert to UI simulation state
			uiSimState = SimProcessor.getUISimState(simulationMonitor);

			// set ui models in service
			ModelService.setUISimState(uiSimState);

		}

	}

	// exit the system
	public void exit() {
		System.exit(0);

	}
}
