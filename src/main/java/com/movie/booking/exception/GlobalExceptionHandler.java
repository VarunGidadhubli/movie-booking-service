package com.movie.booking.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.movie.booking.dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleMovieNotFound(MovieNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(404, "Not found", exception.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception){
		
		String message=exception.getBindingResult().getFieldErrors().stream()
				.map(error->error.getField()+" : "+error.getDefaultMessage())
				.collect(Collectors.joining(","));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad request", message, LocalDateTime.now()));
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleRoleNotFound(RoleNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),"Not found", exception.getMessage(),LocalDateTime.now()));
	}
	
	@ExceptionHandler(RoleAlreadyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleRoleAlreadyExistsException(RoleAlreadyExistsException exception){
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ApiErrorResponse(HttpStatus.NO_CONTENT.value(), "Conflict", exception.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(EmailAlreadyRegisteredException.class)
	public ResponseEntity<ApiErrorResponse> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException exception){
		return ResponseEntity.status(HttpStatus.CONFLICT).
				body(new ApiErrorResponse(HttpStatus.CONFLICT.value(), "Duplicate", exception.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(TheatreNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleTheatreNotFoundException(TheatreNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), "Not found", exception.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(TheatreScreenDuplicateException.class)
	public ResponseEntity<ApiErrorResponse> handleTheatreScreenDuplicateException(TheatreScreenDuplicateException exception){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiErrorResponse(HttpStatus.CONFLICT.value(), "Duplicate", exception.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(SeatTypeAlreadyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleSeatTypeAlreadyExistsException(SeatTypeAlreadyExistsException exception){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiErrorResponse(HttpStatus.CONFLICT.value(), "Duplicate", exception.getMessage(), LocalDateTime.now()));
	}
}