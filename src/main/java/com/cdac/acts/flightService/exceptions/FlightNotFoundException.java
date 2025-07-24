package com.cdac.acts.flightService.exceptions;

public class FlightNotFoundException extends RuntimeException{

	public FlightNotFoundException(String message) {
		
		super(message);
	}
}
