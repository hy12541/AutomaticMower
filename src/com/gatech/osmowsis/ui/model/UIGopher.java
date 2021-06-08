package com.gatech.osmowsis.ui.model;

import com.gatech.osmowsis.simstate.Location;

// ui gopher
public class UIGopher {

	private int id;
	private Location location;
	private boolean isCurrentGopher;
	private boolean isNextGopher;

	public UIGopher() {
		super();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UIGopher(Location location) {
		super();
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isCurrentGopher() {
		return isCurrentGopher;
	}

	public void setCurrentGopher(boolean isCurrentGopher) {
		this.isCurrentGopher = isCurrentGopher;
	}

	public boolean isNextGopher() {
		return isNextGopher;
	}

	public void setNextGopher(boolean isNextGopher) {
		this.isNextGopher = isNextGopher;
	}

}
