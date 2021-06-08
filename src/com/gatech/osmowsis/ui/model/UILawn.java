package com.gatech.osmowsis.ui.model;

import com.gatech.osmowsis.simstate.Size;
import com.gatech.osmowsis.square.Square;

// ui lawn
public class UILawn {
	private Size size;
	private Square[][] lawnSquares;

	public UILawn() {
		super();

	}

	public UILawn(Size size, Square[][] lawnSquares) {
		super();
		this.size = size;
		this.lawnSquares = lawnSquares;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Square[][] getLawnSquares() {
		return lawnSquares;
	}

	public void setLawnSquares(Square[][] lawnSquares) {
		this.lawnSquares = lawnSquares;
	}

}
