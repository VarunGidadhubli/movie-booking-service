package com.movie.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.movie.booking.entity.ShowStatus;

public class ShowResponse {

	private Long id;

	private Long movieId;

	private Long screenId;

	private LocalDate showDate;

	private LocalTime startTime;

	private LocalTime endTime;

	private ShowStatus status;

	public ShowResponse() {

	}

	public ShowResponse(Long id, Long movieId, Long screenId, LocalDate showDate, LocalTime startTime,
			LocalTime endTime, ShowStatus status) {
		this.id = id;
		this.movieId = movieId;
		this.screenId = screenId;
		this.showDate = showDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public ShowStatus getStatus() {
		return status;
	}

	public void setStatus(ShowStatus status) {
		this.status = status;
	}

}