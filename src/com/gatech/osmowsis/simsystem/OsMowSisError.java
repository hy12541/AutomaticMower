package com.gatech.osmowsis.simsystem;

// errors
public enum OsMowSisError {
	INVALID_FILE_NAME(0, "Test scenario file name not found."), INVALID_FILE(1, "Test scenario file not found."),
	INVALID_FILE_INFORMATION(2, "Information of test scenario file not found.");

	private final int code;
	private final String messsage;

	private OsMowSisError(int code, String messsage) {
		this.code = code;
		this.messsage = messsage;
	}

	public int getCode() {
		return code;
	}

	public String getMesssage() {
		return messsage;
	}

}
