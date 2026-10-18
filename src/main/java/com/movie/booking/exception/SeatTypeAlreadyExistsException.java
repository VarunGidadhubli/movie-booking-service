package com.movie.booking.exception;

public class SeatTypeAlreadyExistsException extends RuntimeException {

    public SeatTypeAlreadyExistsException(String message) {
        super(message);
    }
}