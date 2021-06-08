package com.gatech.osmowsis.simsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class LogFile {
	private StringBuilder logText;
	private String logName;
	private String logPath;
	private String description;

	public LogFile() {
		super();
		logText = new StringBuilder();
		logName = "osmowsis.log";
		File logFile = new File(logName);
		if (logFile != null) {
			logPath = logFile.getAbsolutePath();
		}

		description = "Log File: " + logPath + "\n";

	}

	public StringBuilder getLogText() {
		return logText;
	}

	public void setLogText(StringBuilder logText) {
		this.logText = logText;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public void appendLogText(String output) {
		logText.append(output + "\n");

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void writeToLog() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(logPath));
			writer.write(logText.toString());
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
