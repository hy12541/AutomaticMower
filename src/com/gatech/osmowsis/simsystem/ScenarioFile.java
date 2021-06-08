package com.gatech.osmowsis.simsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// to save scenario file in lines
public class ScenarioFile {
	public static final String DELIMITER = ",";
	private List<String> lines;
	private OsMowSisError error;

	public ScenarioFile() {
		super();
		lines = new ArrayList<>();
	}

	public ScenarioFile(List<String> lines) {
		super();
		this.lines = lines;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public OsMowSisError getError() {
		return error;
	}

	public void setError(OsMowSisError error) {
		this.error = error;
	}

	// read scenario file by command
	public void readTestFile(String testFileName) {
		List<String> lines = new ArrayList<String>();
		String line = "";
		if (testFileName != null && !testFileName.isEmpty()) {
			try (Scanner input = new Scanner(new File(testFileName))) {
				while (input.hasNext()) {
					line = input.next();
					lines.add(line);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				setError(OsMowSisError.INVALID_FILE);
			}

		}

		// save lines read from test file
		setLines(lines);
	}

	// read scenario file by gui
	public void readTestFile(File testFile) {
		List<String> lines = new ArrayList<String>();
		String line = "";
		if (testFile != null) {
			try (Scanner input = new Scanner(testFile)) {
				while (input.hasNext()) {
					line = input.next();
					lines.add(line);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				setError(OsMowSisError.INVALID_FILE);
			}

		}

		// save lines read from test file
		setLines(lines);
	}
}
