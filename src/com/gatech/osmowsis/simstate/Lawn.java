package com.gatech.osmowsis.simstate;

import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;

// lawn
public class Lawn {
	private Size size;
	private Square[][] lawnSquares;

	public Lawn() {
		super();

	}

	public Lawn(Size size, Square[][] lawnSquares) {
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

	// get lawn square based on x and y
	public Square getLawnSquare(int x, int y) {
		if (lawnSquares != null && x >= 0 && y >= 0 && x < lawnSquares.length && y < lawnSquares[x].length) {

			return lawnSquares[x][y];
		} else
			return null;

	}

	// get lawn square based on location
	public Square getLawnSquare(Location location) {
		return getLawnSquare(location.getxCoordinate(), location.getyCoordinate());

	}

	// calculate lawn area
	public int getArea() {
		int area = 0;

		if (size != null) {
			// calculate area
			area = size.getWidth() * size.getLength();
		}

		return area;
	}

	// calculate grass which has been cut
	public boolean grassAllCut() {

		if (getLawnSquares() != null) {
			// check if all grass squares are cut
			for (int i = 0; i < getSize().getWidth(); i++) {
				for (int j = 0; j < getSize().getLength(); j++) {
					if (getLawnSquare(i, j).getSquareState() == SquareState.grass
							|| getLawnSquare(i, j).getSquareState() == SquareState.gopher_grass) {
						return false;
					}
				}
			}
		}

		return true;
	}

	// calculate the remaining grass number
	public int getGrassNumber() {
		int numGrass = 0;
		for (int i = 0; i < size.getWidth(); i++) {
			for (int j = 0; j < size.getLength(); j++) {
				// get current grass number
				if (lawnSquares[i][j].getSquareState() == SquareState.grass
						|| getLawnSquare(i, j).getSquareState() == SquareState.gopher_grass) {
					numGrass++;
				}
			}
		}

		return numGrass;
	}

	// update square state by location
	public void setLawnSquareState(Location location, SquareState newState) {
		if (location != null && newState != null) {
			int x = location.getxCoordinate();
			int y = location.getyCoordinate();

			if (x >= 0 && y >= 0 && x < size.getWidth() && y < size.getLength()) {
				lawnSquares[x][y].setSquareState(newState);
			}

		}
	}

	// get square state by location
	public SquareState getLawnSquareState(Location location) {
		if (location != null) {
			int x = location.getxCoordinate();
			int y = location.getyCoordinate();

			if (x >= 0 && y >= 0 && x < size.getWidth() && y < size.getLength()) {
				return lawnSquares[x][y].getSquareState();
			}

		}

		return null;
	}

}
