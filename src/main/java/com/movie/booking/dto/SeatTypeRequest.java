package com.movie.booking.dto;

import jakarta.validation.constraints.NotBlank;

public class SeatTypeRequest {

	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}