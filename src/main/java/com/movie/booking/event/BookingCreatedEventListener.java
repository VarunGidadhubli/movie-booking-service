package com.movie.booking.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.movie.booking.producer.BookingEventProducer;

@Component
public class BookingCreatedEventListener {

	private final BookingEventProducer bookingEventProducer;

	public BookingCreatedEventListener(BookingEventProducer bookingEventProducer) {
		this.bookingEventProducer = bookingEventProducer;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleBookingCreated(BookingCreatedApplicationEvent event) {

		bookingEventProducer.publishBookingCreatedEvent(event.getBookingCreatedEvent());
	}
}