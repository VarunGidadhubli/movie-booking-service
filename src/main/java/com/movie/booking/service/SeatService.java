package com.movie.booking.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.dto.BulkSeatRequest;
import com.movie.booking.dto.SeatRequest;
import com.movie.booking.dto.SeatResponse;
import com.movie.booking.entity.Screen;
import com.movie.booking.entity.Seat;
import com.movie.booking.entity.SeatType;
import com.movie.booking.repository.ScreenRepository;
import com.movie.booking.repository.SeatRepository;
import com.movie.booking.repository.SeatTypeRepository;

@Service
public class SeatService {

	private final SeatRepository seatRepository;
	private final ScreenRepository screenRepository;
	private final SeatTypeRepository seatTypeRepository;

	public SeatService(SeatRepository seatRepository, ScreenRepository screenRepository,
			SeatTypeRepository seatTypeRepository) {
		this.seatRepository = seatRepository;
		this.screenRepository = screenRepository;
		this.seatTypeRepository = seatTypeRepository;
	}

	@Transactional
	public List<SeatResponse> createSeats(Long screenId, BulkSeatRequest bulkSeatRequest) {

		Screen screen = screenRepository.findById(screenId)
				.orElseThrow(() -> new IllegalStateException("Screen not found exception"));
		List<SeatRequest> listOfSeatsRequests = bulkSeatRequest.getSeats();
		HashSet<Seat> seatsToSave = new HashSet<>();
		Set<String> requestedSeatNumbers = new HashSet<>();

		for (SeatRequest seatRequest : listOfSeatsRequests) {

			if (seatRepository.existsByScreenIdAndSeatNumber(seatRequest.getScreenId(),seatRequest.getSeatNumber())) {
				throw new IllegalArgumentException("Theatre, Screen, Seat number combination already exist");
			}
			
			String seatNumber=seatRequest.getSeatNumber().trim().toUpperCase();
			
			if (!requestedSeatNumbers.add(seatNumber)) {
			    throw new IllegalArgumentException("Duplicate seat number exists in the request: " + seatNumber);
			}

			SeatType seatType = seatTypeRepository.findById(seatRequest.getSeatTypeId()).orElseThrow(
					() -> new IllegalArgumentException("Seat type not found: " + seatRequest.getSeatTypeId()));

			Seat seat = new Seat();
			seat.setScreen(screen);
			seat.setSeatNumber(seatNumber);
			seat.setSeatType(seatType);
			seat.setStatus("ACTIVE");

			seatsToSave.add(seat);
		}

		List<Seat> savedSeats = seatRepository.saveAll(seatsToSave);

		return savedSeats.stream().map(seat -> new SeatResponse(seat.getId(), screen.getId(), seat.getSeatNumber(),
				seat.getSeatType().getId(), seat.getStatus())).toList();

	}
}