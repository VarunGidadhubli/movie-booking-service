package com.movie.booking.dto;

import com.movie.booking.entity.ScreenStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ScreenRequest {

	@NotNull
	@Positive
	private Long theatreId;
	
	@NotNull
	@Positive
	private Integer screenNumber;
	
	@NotNull
	private ScreenStatus status;

	public Long getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(Long theatreId) {
		this.theatreId = theatreId;
	}

	public Integer getScreenNumber() {
		return screenNumber;
	}

	public void setScreenNumber(Integer screenNumber) {
		this.screenNumber = screenNumber;
	}

	public ScreenStatus getStatus() {
		return status;
	}

	public void setStatus(ScreenStatus status) {
		this.status = status;
	}
	
}