package com.movie.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.ScreenRequest;
import com.movie.booking.dto.ScreenResponse;
import com.movie.booking.entity.ScreenStatus;
import com.movie.booking.service.ScreenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/screen")
public class ScreenController {

	private final ScreenService screenService;
	
	public ScreenController(ScreenService screenService) {
		this.screenService = screenService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ScreenResponse createScreen(@Valid @RequestBody ScreenRequest screenRequest) {
		
		return screenService.createScreen(screenRequest);
	}
	
	@PutMapping({"/{theatreId}/{screenNumber}"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateScreen(@PathVariable Long theatreId, @PathVariable Integer screenNumber
			,@RequestParam ScreenStatus status) {
		screenService.updateScreenStatus(theatreId,screenNumber,status);
	}
}