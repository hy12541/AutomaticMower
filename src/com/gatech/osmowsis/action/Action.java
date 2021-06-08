package com.gatech.osmowsis.action;

// base action
public abstract class Action {

	protected String actionName = "action";
	protected int energyNeeded;

	public String getActionName() {
		return actionName;
	}

	public int getEnergyNeeded() {
		return energyNeeded;
	}

}
