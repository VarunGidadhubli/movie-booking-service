package com.movie.booking.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovieRequest {

	@NotBlank
	private String title;
	
	@NotNull
	private LocalDate releaseDate;
	
	@NotNull
	@Positive
	private Integer durationMinutes;
	
	//We can have description as optional hence no annotation here
	private String description;
	
	@NotBlank
	private String language;
	
	public MovieRequest() {
		
	}

	public MovieRequest(String title, LocalDate releaseDate, Integer durationMinutes, String description,
			String language) {
		this.title = title;
		this.releaseDate = releaseDate;
		this.durationMinutes = durationMinutes;
		this.description = description;
		this.language = language;
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