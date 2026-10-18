package com.movie.booking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.SeatTypeRequest;
import com.movie.booking.dto.SeatTypeResponse;
import com.movie.booking.service.SeatTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/seattype")
public class SeatTypeController {

	private final SeatTypeService seatTypeService;

	public SeatTypeController(SeatTypeService seatTypeService) {
		this.seatTypeService = seatTypeService;
	}
	
	@PostMapping
	public SeatTypeResponse createSeatType(@Valid @RequestBody SeatTypeRequest seatTypeRequest) {
		return seatTypeService.createSeatType(seatTypeRequest);
	}
	
	@GetMapping
	public List<SeatTypeResponse> findAllSeatTypes() {
		return seatTypeService.findAllSeatTypes();
	}
}