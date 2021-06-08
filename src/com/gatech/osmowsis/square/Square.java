package com.gatech.osmowsis.square;

// squares
public class Square {
	private SquareState squareState;

	public Square(SquareState squareState) {
		super();
		this.squareState = squareState;
	}

	public SquareState getSquareState() {
		return squareState;
	}

	public void setSquareState(SquareState squareState) {
		this.squareState = squareState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((squareState == null) ? 0 : squareState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (squareState != other.squareState)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Square [squareState=" + squareState + "]";
	}

}
