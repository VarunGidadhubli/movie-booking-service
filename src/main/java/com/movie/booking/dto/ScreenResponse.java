package com.movie.booking.dto;

import com.movie.booking.entity.ScreenStatus;

public class ScreenResponse {

	private Long id;

	private Long theatreId;

	private Integer screenNumber;

	private ScreenStatus status;

	public ScreenResponse(Long id, Long theatreId, Integer screenNumber, ScreenStatus status) {
		this.id = id;
		this.theatreId = theatreId;
		this.screenNumber = screenNumber;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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