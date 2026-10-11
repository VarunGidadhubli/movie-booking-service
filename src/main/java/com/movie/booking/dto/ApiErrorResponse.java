package com.movie.booking.dto;

import java.time.LocalDateTime;

public class ApiErrorResponse {

	private Integer status;

	private String error;

	private String message;

	private LocalDateTime timestamp;

	public ApiErrorResponse(Integer status, String error, String message, LocalDateTime timestamp) {
		this.status = status;
		this.error = error;
		this.message = message;
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}