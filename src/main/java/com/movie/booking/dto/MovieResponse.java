package com.movie.booking.dto;

import java.time.LocalDate;

public class MovieResponse {

	private Long id;

	private String title;

	private LocalDate releaseDate;

	private Integer durationMinutes;

	private String description;

	private String language;

	public MovieResponse() {

	}

	public MovieResponse(Long id, String title, LocalDate releaseDate, Integer durationMinutes, String description,
			String language) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.durationMinutes = durationMinutes;
		this.description = description;
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
