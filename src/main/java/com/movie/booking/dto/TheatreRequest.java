package com.movie.booking.dto;

import jakarta.validation.constraints.NotBlank;

public class TheatreRequest {

	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String pincode;

	public TheatreRequest() {
		
	}
	
	public TheatreRequest(String name, String address, String city, String pincode) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.pincode = pincode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
}