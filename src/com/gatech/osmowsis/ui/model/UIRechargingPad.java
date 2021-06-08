package com.gatech.osmowsis.ui.model;

import com.gatech.osmowsis.simstate.Location;

// ui recharging pad
public class UIRechargingPad {

	private Location location;

	public UIRechargingPad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UIRechargingPad(Location location) {
		super();
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
