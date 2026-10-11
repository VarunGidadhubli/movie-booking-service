package com.movie.booking.exception;

public class RoleAlreadyExistsException extends RuntimeException{

	public RoleAlreadyExistsException(String message) {
		super(message);
	}
}