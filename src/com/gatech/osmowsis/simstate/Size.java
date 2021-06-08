package com.gatech.osmowsis.simstate;

// size of lawn
public class Size {
	private int width;
	private int length;

	public Size(int width, int length) {
		super();
		this.width = width;
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
