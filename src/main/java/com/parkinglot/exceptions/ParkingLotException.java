package com.parkinglot.exceptions;

public class ParkingLotException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ParkingLotException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
