package com.movie.booking.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleRequest {

	@NotBlank
	private String name;
	
	public RoleRequest() {
		
	}

	public RoleRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
