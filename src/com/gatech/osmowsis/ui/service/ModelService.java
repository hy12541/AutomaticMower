package com.gatech.osmowsis.ui.service;

import com.gatech.osmowsis.ui.model.UIRechargingPad;
import java.util.Map;
import com.gatech.osmowsis.controller.SimController;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.ui.model.UIGopher;
import com.gatech.osmowsis.ui.model.UILawn;
import com.gatech.osmowsis.ui.model.UIMower;
import com.gatech.osmowsis.ui.model.UISimState;
import com.gatech.osmowsis.ui.panel.LawnPanel;
import com.gatech.osmowsis.ui.panel.NextObjectPanel;
import com.gatech.osmowsis.ui.panel.SidePanel;

// save ui state and ui element
public class ModelService {
	private static UISimState uiSimState;
	private static SimController simController;
	private static LawnPanel lawnPanel;
	private static NextObjectPanel nextObjectPanel;
	private static SidePanel sidePanel;

	public static UISimState getUISimState() {
		return uiSimState;
	}

	public static void setUISimState(UISimState uiSimState) {
		ModelService.uiSimState = uiSimState;
	}

	public static void setSimController(SimController simController) {
		ModelService.simController = simController;
	}

	public static UILawn getUILawn() {
		if (uiSimState != null) {
			return uiSimState.getUilawn();
		}

		return null;
	}

	public static UIMower[] getUIMowers() {
		if (uiSimState != null) {
			return uiSimState.getUimowers();
		}

		return null;
	}

	public static UIGopher[] getUIGophers() {
		if (uiSimState != null) {
			return uiSimState.getUigophers();
		}

		return null;
	}

	public static UIRechargingPad[] getRechargingPads() {
		if (uiSimState != null) {
			return uiSimState.getUiRechargingPads();
		}

		return null;
	}

	public static Map<Location, Square> getAllScannedSquares() {
		if (uiSimState != null) {
			return uiSimState.getAllScannedSquares();
		}

		return null;
	}

	public static String getUiOutputText() {
		String output = "";
		if (uiSimState != null) {

			if (uiSimState.getUiError() != null && !uiSimState.getUiError().isEmpty()) {
				output = uiSimState.getUiError();
			} else if (uiSimState.getUiOutput() != null && !uiSimState.getUiOutput().isEmpty()) {
				output = uiSimState.getUiOutput();
			}
		
		}

		return output;
	}

	public static String getUiError() {
		if (uiSimState != null) {
			return uiSimState.getUiError();
		}

		return "";
	}

	public static SimController getSimController() {

		return simController;
	}

	public static LawnPanel getLawnPanel() {
		return lawnPanel;
	}

	public static void setLawnPanel(LawnPanel lawnPanel) {
		ModelService.lawnPanel = lawnPanel;
	}

	public static NextObjectPanel getNextObjectPanel() {
		return nextObjectPanel;
	}

	public static void setNextObjectPanel(NextObjectPanel nextObjectPanel) {
		ModelService.nextObjectPanel = nextObjectPanel;
	}

	public static SidePanel getSidePanel() {
		return sidePanel;
	}

	public static void setSidePanel(SidePanel sidePanel) {
		ModelService.sidePanel = sidePanel;
	}

}
