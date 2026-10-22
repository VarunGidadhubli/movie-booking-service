package com.movie.booking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.dto.BookingRequest;
import com.movie.booking.dto.BookingResponse;
import com.movie.booking.entity.Booking;
import com.movie.booking.entity.BookingSeat;
import com.movie.booking.entity.BookingStatus;
import com.movie.booking.entity.Seat;
import com.movie.booking.entity.Show;
import com.movie.booking.entity.User;
import com.movie.booking.exception.ShowNotFoundException;
import com.movie.booking.repository.BookingRepository;
import com.movie.booking.repository.BookingSeatRepository;
import com.movie.booking.repository.SeatRepository;
import com.movie.booking.repository.ShowRepository;
import com.movie.booking.repository.UserRepository;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final BookingSeatRepository bookingSeatRepository;
	private final ShowRepository showRepository;
	private final UserRepository userRepository;
	private final SeatRepository seatRepository;

	public BookingService(BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository,
			ShowRepository showRepository, UserRepository userRepository, SeatRepository seatRepository) {
		this.bookingRepository = bookingRepository;
		this.bookingSeatRepository = bookingSeatRepository;
		this.showRepository = showRepository;
		this.userRepository = userRepository;
		this.seatRepository = seatRepository;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public BookingResponse createBooking(BookingRequest bookingRequest) {

		if (bookingRequest == null || bookingRequest.getShowId() == null) {
			throw new IllegalArgumentException("Show ID is required");
		}

		List<Long> seatIds = bookingRequest.getSeatIds();

		if (seatIds == null || seatIds.isEmpty()) {
			throw new IllegalArgumentException("At least one seat must be selected");
		}

		if (seatIds.contains(null)) {
			throw new IllegalArgumentException("Seat IDs cannot contain null");
		}

		if (new HashSet<>(seatIds).size() != seatIds.size()) {
			throw new IllegalArgumentException("Duplicate seat IDs are not allowed");
		}

		User currentUser = getAuthentication();

		Show show = showRepository.findById(bookingRequest.getShowId())
				.orElseThrow(() -> new ShowNotFoundException("Show doesn't exist: " + bookingRequest.getShowId()));

		List<Long> sortedSeatIds = new ArrayList<>(seatIds);
		sortedSeatIds.sort(Long::compareTo);

		List<Seat> selectedSeats = new ArrayList<>();

		for (Long seatId : sortedSeatIds) {
			Seat seat = seatRepository.findByIdForUpdate(seatId).orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId));

			if (!seat.getScreen().getId().equals(show.getScreen().getId())) {
				throw new IllegalArgumentException("Seat " + seatId + " does not belong to the screen for this show");
			}

			selectedSeats.add(seat);
		}

		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		for (Long seatId : sortedSeatIds) {

			bookingSeatRepository.expirePendingReservation(show.getId(), seatId, now);

			if (bookingSeatRepository.countActiveReservations(show.getId(), seatId, now) > 0) {
				throw new IllegalArgumentException("Seat " + seatId + " is no longer available");
			}
		}

		// 4. Calculate the booking amount.
		BigDecimal pricePerSeat = BigDecimal.valueOf(300); // Temporary price
		BigDecimal totalAmount = pricePerSeat.multiply(BigDecimal.valueOf(selectedSeats.size()));

		// 5. Create the pending booking.
		Booking booking = new Booking();
		booking.setUser(currentUser);
		booking.setShow(show);
		booking.setStatus(BookingStatus.PENDING);
		booking.setTotalAmount(totalAmount);

		Booking savedBooking = bookingRepository.save(booking);

		LocalDateTime lockExpiry = now.plusMinutes(10);

		// 6. Create one booking-seat row per selected seat.
		List<BookingSeat> bookingSeats = new ArrayList<>();

		for (Seat seat : selectedSeats) {

			BookingSeat bookingSeat = new BookingSeat();
			bookingSeat.setBooking(savedBooking);
			bookingSeat.setShow(show);
			bookingSeat.setSeat(seat);
			bookingSeat.setPrice(pricePerSeat);
			bookingSeat.setStatus("PENDING");
			bookingSeat.setLockExpiry(lockExpiry);

			bookingSeats.add(bookingSeat);
		}

		bookingSeatRepository.saveAll(bookingSeats);

		return new BookingResponse(savedBooking.getId(), currentUser.getId(), show.getId(), seatIds,
				BookingStatus.PENDING, totalAmount);
	}

	private User getAuthentication() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			throw new IllegalStateException("Authenticated JWT is required");
		}

		Long userId = Long.valueOf(jwt.getSubject());

		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
	}
}
