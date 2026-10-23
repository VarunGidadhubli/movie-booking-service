package com.movie.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.BookingPaymentResponse;
import com.movie.booking.dto.BookingRequest;
import com.movie.booking.entity.PaymentMethod;
import com.movie.booking.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping("/{paymentMethod}")
	public ResponseEntity<BookingPaymentResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest, 
			@PathVariable PaymentMethod paymentMethod) {

		BookingPaymentResponse response = bookingService.createBooking(bookingRequest,paymentMethod);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}