package com.movie.booking.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

	public EmailAlreadyRegisteredException(String message) {
		super(message);
	}
}