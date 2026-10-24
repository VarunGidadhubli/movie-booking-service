package com.movie.booking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.movie.booking.dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
				"Record not found", exception.getMessage(), LocalDateTime.now()));
	}

	@ExceptionHandler(MandatoryFieldMissingException.class)
	public ResponseEntity<ApiErrorResponse> handleMandatoryFieldMissingException(
			MandatoryFieldMissingException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
				"Mandatory fields are missing in the request", exception.getMessage(), LocalDateTime.now()));
	}

	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateRecordException(DuplicateRecordException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiErrorResponse(HttpStatus.CONFLICT.value(),
						"Duplicate record exists or the requests has duplicate data", exception.getMessage(),
						LocalDateTime.now()));
	}
}