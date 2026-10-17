package com.movie.booking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "screens")
public class Screen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "theatre_id", nullable = false)
	private Theatre theatre;

	@Column(name = "screen_number", nullable = false)
	private Integer screenNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ScreenStatus status;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	public Screen() {

	}

	public Screen(Long id, Theatre theatre, Integer screenNumber, ScreenStatus status, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.theatre = theatre;
		this.screenNumber = screenNumber;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}