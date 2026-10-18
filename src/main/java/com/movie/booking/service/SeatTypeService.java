package com.movie.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movie.booking.dto.SeatTypeRequest;
import com.movie.booking.dto.SeatTypeResponse;
import com.movie.booking.entity.SeatType;
import com.movie.booking.exception.SeatTypeAlreadyExistsException;
import com.movie.booking.repository.SeatTypeRepository;

@Service
public class SeatTypeService {

	private final SeatTypeRepository seatTypeRepository;

	public SeatTypeService(SeatTypeRepository seatTypeRepository) {
		this.seatTypeRepository = seatTypeRepository;
	}

	public SeatTypeResponse createSeatType(SeatTypeRequest seatTypeRequest) {

		String name = seatTypeRequest.getName().trim().toUpperCase();
		
		if(seatTypeRepository.existsByName(name)) {
			throw new SeatTypeAlreadyExistsException(name+" already exists");
		}
		
		SeatType seatType = new SeatType();
		seatType.setName(name);
		SeatType createdSeatType = seatTypeRepository.save(seatType);

		return new SeatTypeResponse(createdSeatType.getId(), createdSeatType.getName());
	}

	public List<SeatTypeResponse> findAllSeatTypes(){
		
		return seatTypeRepository.findAll().stream().
			map(seattype->new SeatTypeResponse(seattype.getId(),seattype.getName())).toList();
	}
}