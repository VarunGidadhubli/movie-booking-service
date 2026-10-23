package com.movie.booking.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.repository.BookingRepository;
import com.movie.booking.repository.BookingSeatRepository;
import com.movie.booking.repository.PaymentRepository;

@Component
public class PaymentAndBookingStatusScheduler {

	private final PaymentRepository paymentRepository;
	private final BookingRepository bookingRepository;
	private final BookingSeatRepository bookingSeatRepository;

	public PaymentAndBookingStatusScheduler(PaymentRepository paymentRepository, BookingRepository bookingRepository,
			BookingSeatRepository bookingSeatRepository) {
		this.paymentRepository = paymentRepository;
		this.bookingRepository = bookingRepository;
		this.bookingSeatRepository = bookingSeatRepository;
	}

	@Scheduled(fixedDelay = 300000)
	@Transactional
	public void updatePaymentAndBookingStatusToCancelled() {

		bookingSeatRepository.releaseExpiredSeats();
	    paymentRepository.expireInitiatedPayments();
	    bookingRepository.expirePendingBookings();
	}
}