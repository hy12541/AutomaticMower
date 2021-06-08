package com.gatech.osmowsis.simsystem;

import com.gatech.osmowsis.ui.frame.MainJFrame;

// main class - entry point
public class Main {
	public static void main(String[] args) {

//		used to get absolute path for input file
//		System.out.println(new File(".").getAbsoluteFile());

		String testFileName = "";
		boolean showState = false;

		// check for the test scenario file name
		if (args.length < 1) {
			System.out.println("ERROR: " + OsMowSisError.INVALID_FILE_NAME.getMesssage());
			return;
		} else {
			testFileName = args[0];
		}

		// check if show lawn state
		if (args.length >= 2 && (args[1].equals("-v") || args[1].equals("-verbose"))) {
			showState = true;
		}

		// create new instance of main gui
		MainJFrame mainJFrame = new MainJFrame(testFileName, showState);

	}

}
