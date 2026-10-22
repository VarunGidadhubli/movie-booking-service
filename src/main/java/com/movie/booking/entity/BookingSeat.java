package com.movie.booking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_seats")
public class BookingSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;

	@ManyToOne
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@Column(precision = 10, scale = 2)
	private BigDecimal price;

	private String status;

	@Column(name = "lock_expiry")
	private LocalDateTime lockExpiry;

	@Column(name = "active_seat_id", insertable = false, updatable = false)
	private Long activeSeatId;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	public BookingSeat() {

	}

	public BookingSeat(Long id, Booking booking, Show show, Seat seat, BigDecimal price, String status,
			LocalDateTime lockExpiry, LocalDateTime createdAt, LocalDateTime updatedAt, Long activeSeatId) {
		this.id = id;
		this.booking = booking;
		this.show = show;
		this.seat = seat;
		this.price = price;
		this.status = status;
		this.lockExpiry = lockExpiry;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.activeSeatId = activeSeatId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getLockExpiry() {
		return lockExpiry;
	}

	public void setLockExpiry(LocalDateTime lockExpiry) {
		this.lockExpiry = lockExpiry;
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

	public Long getActiveSeatId() {
		return activeSeatId;
	}

	public void setActiveSeatId(Long activeSeatId) {
		this.activeSeatId = activeSeatId;
	}

}