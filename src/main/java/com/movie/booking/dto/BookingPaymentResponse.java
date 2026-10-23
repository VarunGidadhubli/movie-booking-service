package com.movie.booking.dto;

public class BookingPaymentResponse {

	private BookingResponse bookingResponse;
	private PaymentResponse paymentResponse;

	public BookingPaymentResponse() {

	}

	public BookingPaymentResponse(BookingResponse bookingResponse, PaymentResponse paymentResponse) {
		this.bookingResponse = bookingResponse;
		this.paymentResponse = paymentResponse;
	}

	public BookingResponse getBookingResponse() {
		return bookingResponse;
	}

	public void setBookingResponse(BookingResponse bookingResponse) {
		this.bookingResponse = bookingResponse;
	}

	public PaymentResponse getPaymentResponse() {
		return paymentResponse;
	}

	public void setPaymentResponse(PaymentResponse paymentResponse) {
		this.paymentResponse = paymentResponse;
	}

}