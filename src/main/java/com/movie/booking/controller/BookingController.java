
package com.movie.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.BookingRequest;
import com.movie.booking.dto.BookingResponse;
import com.movie.booking.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {

		BookingResponse response = bookingService.createBooking(bookingRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}