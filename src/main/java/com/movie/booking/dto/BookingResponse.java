package com.movie.booking.dto;

import java.math.BigDecimal;
import java.util.List;

import com.movie.booking.entity.BookingStatus;

public class BookingResponse {

	private Long id;

	private Long userId;

	private Long showId;

	private List<Long> seatIds;

	private BookingStatus status;

	private BigDecimal totalAmount;
	
	public BookingResponse() {
		
	}

	public BookingResponse(Long id, Long userId, Long showId, List<Long> seatIds, BookingStatus status,BigDecimal totalAmount) {
		this.id = id;
		this.userId = userId;
		this.showId = showId;
		this.seatIds = seatIds;
		this.status = status;
		this.totalAmount = totalAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Long> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<Long> seatIds) {
		this.seatIds = seatIds;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}