package com.gatech.osmowsis.simsystem;

// final report
public class SummaryReport {
	private int lawnArea;
	private int originalGrassSquareNumber;
	private int emptySquareNumber;
	private int turnCompletedNumber;

	public SummaryReport(int lawnArea, int originalGrassSquareNumber, int emptySquareNumber, int turnCompletedNumber) {
		super();
		this.lawnArea = lawnArea;
		this.originalGrassSquareNumber = originalGrassSquareNumber;
		this.emptySquareNumber = emptySquareNumber;
		this.turnCompletedNumber = turnCompletedNumber;
	}

	public int getLawnArea() {
		return lawnArea;
	}

	public void setLawnArea(int lawnArea) {
		this.lawnArea = lawnArea;
	}

	public int getOriginalGrassSquareNumber() {
		return originalGrassSquareNumber;
	}

	public void setOriginalGrassSquareNumber(int originalGrassSquareNumber) {
		this.originalGrassSquareNumber = originalGrassSquareNumber;
	}

	public int getEmptySquareNumber() {
		return emptySquareNumber;
	}

	public void setEmptySquareNumber(int emptySquareNumber) {
		this.emptySquareNumber = emptySquareNumber;
	}

	public int getTurnCompletedNumber() {
		return turnCompletedNumber;
	}

	public void setTurnCompletedNumber(int turnCompletedNumber) {
		this.turnCompletedNumber = turnCompletedNumber;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(lawnArea) + "," + String.valueOf(originalGrassSquareNumber) + ","
				+ String.valueOf(emptySquareNumber) + "," + String.valueOf(turnCompletedNumber));

		return sb.toString();
	}

	// display report
	public void display() {

		System.out.println(toString());
	}

}
