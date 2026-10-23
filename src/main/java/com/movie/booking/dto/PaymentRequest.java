package com.movie.booking.dto;

import com.movie.booking.entity.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {

	@NotNull
	private Long bookingId;

	@NotNull
	private PaymentMethod paymentMethod;

	public PaymentRequest() {

	}

	public PaymentRequest(@NotNull Long bookingId, @NotBlank PaymentMethod paymentMethod) {
		this.bookingId = bookingId;
		this.paymentMethod = paymentMethod;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}