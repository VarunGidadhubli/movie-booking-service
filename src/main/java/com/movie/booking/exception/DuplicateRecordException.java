package com.movie.booking.exception;

public class DuplicateRecordException extends RuntimeException {

	public DuplicateRecordException(String message) {
		super(message);
	}
}
