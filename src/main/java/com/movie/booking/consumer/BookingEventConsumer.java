package com.movie.booking.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.movie.booking.event.BookingCreatedEvent;

@Service
public class BookingEventConsumer {

	@KafkaListener(topics = "booking-created", groupId = "booking-service-test")

	public void consumeBookingCreatedEvent(BookingCreatedEvent event) {

		System.out.println("Received Booking Created Event");
		System.out.println("Booking ID: " + event.getBookingId());
		System.out.println("User ID: " + event.getUserId());
		System.out.println("Show ID: " + event.getShowId());
		System.out.println("Total Amount: " + event.getTotalAmount());
	}
}