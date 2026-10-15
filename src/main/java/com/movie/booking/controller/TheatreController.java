package com.movie.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.TheatreRequest;
import com.movie.booking.dto.TheatreResponse;
import com.movie.booking.service.TheatreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

	private final TheatreService theatreService;

	public TheatreController(TheatreService theatreService) {
		this.theatreService = theatreService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TheatreResponse createTheatre(@Valid @RequestBody TheatreRequest theatreRequest) {
		return theatreService.createTheatre(theatreRequest);
	}
	
	@GetMapping("/{id}")
	public TheatreResponse findTheatreById(@PathVariable Long id) {
		return theatreService.getTheatreById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTheatreById(@PathVariable Long id) {
		theatreService.deleteTheatre(id);
	}
	
	@PutMapping("/{id}/approve")
	public TheatreResponse approveTheatre(@PathVariable Long id) {
		return theatreService.approveTheatre(id);
	}
	
	@PutMapping("/{id}/reject")
	public TheatreResponse rejectTheatre(@PathVariable Long id) {
		return theatreService.rejectTheatre(id);
	}
}