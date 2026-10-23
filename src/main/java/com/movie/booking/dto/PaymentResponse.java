package com.movie.booking.dto;

import java.math.BigDecimal;

import com.movie.booking.entity.PaymentMethod;
import com.movie.booking.entity.PaymentStatus;

public class PaymentResponse {

	private Long id;

	private Long bookingId;

	private BigDecimal amount;

	private String transactionId;

	private PaymentStatus status;

	private PaymentMethod paymentMethod;

	public PaymentResponse() {

	}

	public PaymentResponse(Long id, Long bookingId, BigDecimal amount, String transactionId, PaymentStatus status,PaymentMethod paymentMethod) {
		this.id = id;
		this.bookingId = bookingId;
		this.amount = amount;
		this.transactionId = transactionId;
		this.status = status;
		this.paymentMethod = paymentMethod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}