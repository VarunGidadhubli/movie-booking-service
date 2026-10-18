package com.movie.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatRequest {

	@NotBlank
	private String seatNumber;

	@NotNull
	private Long seatTypeId;
	
	@NotNull
	private Long screenId;

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Long getSeatTypeId() {
		return seatTypeId;
	}

	public void setSeatTypeId(Long seatTypeId) {
		this.seatTypeId = seatTypeId;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
}