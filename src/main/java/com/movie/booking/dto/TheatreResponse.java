package com.movie.booking.dto;

import com.movie.booking.entity.TheatreStatus;

public class TheatreResponse {

	private Long id;

	private String name;
	
	private String address;
	
	private String city;
	
	private String pincode;
	
	private TheatreStatus status;
	
	private Long createdByUserId;
	
	private Long approvedByUserId;

	public TheatreResponse(Long id, String name, String address, String city, String pincode, TheatreStatus status,
			Long createdByUserId, Long approvedByUserId) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.pincode = pincode;
		this.status = status;
		this.createdByUserId = createdByUserId;
		this.approvedByUserId = approvedByUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TheatreStatus getStatus() {
		return status;
	}

	public void setStatus(TheatreStatus status) {
		this.status = status;
	}

	public Long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Long getApprovedByUserId() {
		return approvedByUserId;
	}

	public void setApprovedByUserId(Long approvedByUserId) {
		this.approvedByUserId = approvedByUserId;
	}
	
}