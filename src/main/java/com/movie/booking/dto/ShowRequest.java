package com.movie.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.movie.booking.entity.ShowStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ShowRequest {

	@NotNull
	@Positive
	private Long movieId;
	
	@NotNull
	private Long theatreId;
	
	@NotNull
	@Positive
	private Long screenId;
	
	@NotNull
	private LocalDate showDate;
	
	@NotNull
	private LocalTime startTime;
	
	@NotNull
	private ShowStatus status;
	
	public ShowRequest() {
		
	}

	public ShowRequest(Long movieId, Long screenId, LocalDate showDate, LocalTime startTime, 
			ShowStatus status, Long theatreId) {
		this.movieId = movieId;
		this.screenId = screenId;
		this.showDate = showDate;
		this.startTime = startTime;
		this.status = status;
		this.theatreId = theatreId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public LocalDate getShowDate() {
		return showDate;
	}

	public void setShowDate(LocalDate showDate) {
		this.showDate = showDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public ShowStatus getStatus() {
		return status;
	}

	public void setStatus(ShowStatus status) {
		this.status = status;
	}

	public Long getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(Long theatreId) {
		this.theatreId = theatreId;
	}
	
}