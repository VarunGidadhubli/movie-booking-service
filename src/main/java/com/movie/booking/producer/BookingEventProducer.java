package com.movie.booking.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.movie.booking.event.BookingCreatedEvent;

@Service
public class BookingEventProducer {

	private static final String TOPIC = "booking-created";
	
	private final KafkaTemplate<String, BookingCreatedEvent> kafkaTemplate;

	public BookingEventProducer(KafkaTemplate<String, BookingCreatedEvent> kafkaTemplate) {
		
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void publishBookingCreatedEvent(BookingCreatedEvent bookingCreatedEvent) {
		kafkaTemplate.send(TOPIC, bookingCreatedEvent.getBookingId().toString(), bookingCreatedEvent);
	}
	
	
}