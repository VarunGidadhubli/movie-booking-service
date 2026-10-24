package com.movie.booking.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.entity.BookingStatus;
import com.movie.booking.repository.BookingRepository;
import com.movie.booking.repository.BookingSeatRepository;

@Component
public class LockReleaseScheduler {

	private final BookingSeatRepository bookingSeatRepository;
	private final BookingRepository bookingRepository;

	public LockReleaseScheduler(BookingSeatRepository bookingSeatRepository, BookingRepository bookingRepository) {
		this.bookingSeatRepository = bookingSeatRepository;
		this.bookingRepository = bookingRepository;
	}

	@Scheduled(fixedDelay = 60000)
	@Transactional
	public void releaseLock() {

		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		// Expire pending seat reservations whose locks have elapsed.
		bookingSeatRepository.expirePendingSeats(now);

		// Expire bookings only when all their seat reservations are expired.
		bookingRepository.expireBookingsWithAllSeatsExpired(BookingStatus.PENDING, BookingStatus.EXPIRED);
	}
}