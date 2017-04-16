package com.example.service.exception;

public class UnavailableReservationException extends RuntimeException {
	public UnavailableReservationException(String message) {
		super (message);
	}

}
