package com.movie.booking.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class BulkSeatRequest {

	@NotEmpty
	@Valid
	private List<SeatRequest> seats;

	public List<SeatRequest> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatRequest> seats) {
		this.seats = seats;
	}

}