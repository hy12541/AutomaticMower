package com.gatech.osmowsis.ui.model;

// ui output
public class UIOutput {

	private String outputText;

	public UIOutput() {
		super();
		outputText = "";
		// TODO Auto-generated constructor stub
	}

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

	public void appendOutputText(String newOutPutText) {
		this.outputText += newOutPutText;
	}

}
