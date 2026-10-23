package com.movie.booking.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class BookingRequest {

	@NotNull
	private Long showId;
	
	@NotNull
	private List<Long> seatIds;

	public BookingRequest() {

	}

	public BookingRequest(Long showId, List<Long> seatIds) {
		this.showId = showId;
		this.seatIds = seatIds;
	}

	public Long getShowId() {
		return showId;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}

	public List<Long> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<Long> seatIds) {
		this.seatIds = seatIds;
	}

}