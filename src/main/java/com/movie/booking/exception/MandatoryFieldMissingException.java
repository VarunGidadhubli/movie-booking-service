package com.movie.booking.exception;

public class MandatoryFieldMissingException extends RuntimeException{

	public MandatoryFieldMissingException(String message) {
		super(message);
	}
}
