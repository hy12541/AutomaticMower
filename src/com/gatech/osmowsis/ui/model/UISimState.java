package com.gatech.osmowsis.ui.model;

import java.util.Map;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.ui.model.UIGopher;;

// ui simulation state
public class UISimState {
	private UILawn uilawn;
	private int totalMowerNumber;
	private int totalGopherNumber;
	private int totalRechargingPadNumber;
	private UIMower[] uimowers;
	private UIGopher[] uigophers;
	private UIRechargingPad[] uiRechargingPads;
	private String uiOutput;
	private String uiError;

	private boolean simRunCompleted = false;
	private boolean terminated = false;
	private boolean summaryReportShowed = false;

	private UIMower currentUiMower = null;
	private UIGopher currentUiGopher = null;
	private UIMower nextUiMower = null;
	private UIGopher nextUiGopher = null;

	private Map<Location, Square> allScannedSquares;

	public UISimState() {
		super();

	}

	public UILawn getUilawn() {
		return uilawn;
	}

	public void setUilawn(UILawn uilawn) {
		this.uilawn = uilawn;
	}

	public int getTotalMowerNumber() {
		return totalMowerNumber;
	}

	public void setTotalMowerNumber(int totalMowerNumber) {
		this.totalMowerNumber = totalMowerNumber;
	}

	public int getTotalGopherNumber() {
		return totalGopherNumber;
	}

	public void setTotalGopherNumber(int totalGopherNumber) {
		this.totalGopherNumber = totalGopherNumber;
	}

	public int getTotalRechargingPadNumber() {
		return totalRechargingPadNumber;
	}

	public void setTotalRechargingPadNumber(int totalRechargingPadNumber) {
		this.totalRechargingPadNumber = totalRechargingPadNumber;
	}

	public UIMower[] getUimowers() {
		return uimowers;
	}

	public void setUimowers(UIMower[] uimowers) {
		this.uimowers = uimowers;
	}

	public UIGopher[] getUigophers() {
		return uigophers;
	}

	public void setUigophers(UIGopher[] uigophers) {
		this.uigophers = uigophers;
	}

	public UIRechargingPad[] getUiRechargingPads() {
		return uiRechargingPads;
	}

	public void setUiRechargingPads(UIRechargingPad[] uiRechargingPads) {
		this.uiRechargingPads = uiRechargingPads;
	}

	public String getUiOutput() {
		return uiOutput;
	}

	public void setUiOutput(String uiOutput) {
		this.uiOutput = uiOutput;
	}

	public String getUiError() {
		return uiError;
	}

	public void setUiError(String uiError) {
		this.uiError = uiError;
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

	public boolean isSummaryReportShowed() {
		return summaryReportShowed;
	}

	public void setSummaryReportShowed(boolean summaryReportShowed) {
		this.summaryReportShowed = summaryReportShowed;
	}

	// get next and current object in string format
	public String getNextAndCurrentUiObjectStr() {
		StringBuilder sb = new StringBuilder();
		if (!summaryReportShowed) {
			sb.append(getCurrentUiObjectStr());
			sb.append(getNextUiObjectStr());
		} else {
			sb.append("Simulation run is completed. ");
		}

		return sb.toString();
	}

	// get next object in string format
	private String getNextUiObjectStr() {
		StringBuilder sb = new StringBuilder();

		if (nextUiMower != null) {
			sb.append("Mower" + nextUiMower.getId());
		} else if (nextUiGopher != null) {
			sb.append("Gopher" + nextUiGopher.getId());
		}

		if (sb.length() > 0) {
			sb.append(" will be polled next.");
		}

		return sb.toString();

	}

	// get current object in string format
	private String getCurrentUiObjectStr() {
		StringBuilder sb = new StringBuilder();

		if (currentUiMower != null) {
			sb.append("Mower" + currentUiMower.getId());
			sb.append(" is executing " + currentUiMower.getTrackAction().getActionName() + " action. ");

		} else if (currentUiGopher != null) {
			sb.append("Gopher" + currentUiGopher.getId());
			sb.append(" is moving now. ");
		}
		/*
		 * if(sb.length()>0) { sb.append(" is polled now. "); }
		 */

		return sb.toString();

	}

	// calculate next and current object
	public void determineCurrentAndNextUiObject() {
		determineCurrentUiObject();
		determineNextUiObject();
	}

	// calculate next object
	private void determineNextUiObject() {
		nextUiMower = getNextUiMower();
		if (nextUiMower == null) {
			nextUiGopher = getNextUiGopher();
		}
	}

	// calculate next mower
	private UIMower getNextUiMower() {
		UIMower nextMower = null;
		if (uimowers != null && uimowers.length > 0) {
			for (int i = 0; i < uimowers.length; i++) {
				if (uimowers[i].isNextMower()) {
					nextMower = uimowers[i];
					break;
				}
			}
		}

		return nextMower;
	}

	// calculate next gopher
	private UIGopher getNextUiGopher() {
		UIGopher nextGopher = null;
		if (uigophers != null && uigophers.length > 0) {
			for (int i = 0; i < uigophers.length; i++) {
				if (uigophers[i].isNextGopher()) {
					nextGopher = uigophers[i];
					break;
				}
			}
		}

		return nextGopher;
	}

	private void determineCurrentUiObject() {
		currentUiMower = getCurrentUiMower();
		if (currentUiMower == null) {
			currentUiGopher = getCurrentUiGopher();
		}
	}

	// calculate current mower
	private UIMower getCurrentUiMower() {
		UIMower currentMower = null;
		if (uimowers != null && uimowers.length > 0) {
			for (int i = 0; i < uimowers.length; i++) {
				if (uimowers[i].isCurrentMower()) {
					currentMower = uimowers[i];
					break;
				}
			}
		}

		return currentMower;
	}

	// calculate current gopher
	private UIGopher getCurrentUiGopher() {
		UIGopher currentGopher = null;
		if (uigophers != null && uigophers.length > 0) {
			for (int i = 0; i < uigophers.length; i++) {
				if (uigophers[i].isCurrentGopher()) {
					currentGopher = uigophers[i];
					break;
				}
			}
		}

		return currentGopher;
	}

	public Map<Location, Square> getAllScannedSquares() {
		return allScannedSquares;
	}

	public void setAllScannedSquares(Map<Location, Square> allScannedSquares) {
		this.allScannedSquares = allScannedSquares;
	}

}
