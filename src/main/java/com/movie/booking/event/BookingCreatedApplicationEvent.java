package com.movie.booking.event;

public class BookingCreatedApplicationEvent {

	private final BookingCreatedEvent bookingCreatedEvent;

	public BookingCreatedApplicationEvent(BookingCreatedEvent bookingCreatedEvent) {
		this.bookingCreatedEvent = bookingCreatedEvent;
	}

	public BookingCreatedEvent getBookingCreatedEvent() {
		return bookingCreatedEvent;
	}
}