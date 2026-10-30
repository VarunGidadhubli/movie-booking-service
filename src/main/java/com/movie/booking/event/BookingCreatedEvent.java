package com.movie.booking.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingCreatedEvent {

	private Long bookingId;
	private Long userId;
	private Long showId;
	private BigDecimal totalAmount;
	private LocalDateTime createdAt;

	public BookingCreatedEvent() {

	}

	public BookingCreatedEvent(Long bookingId, Long userId, Long showId, BigDecimal totalAmount,
			LocalDateTime createdAt) {
		this.bookingId = bookingId;
		this.userId = userId;
		this.showId = showId;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShowId() {
		return showId;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}