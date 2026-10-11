package com.movie.booking.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.movie.booking.dto.MovieRequest;
import com.movie.booking.dto.MovieResponse;
import com.movie.booking.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MovieResponse createMovie(@Valid @RequestBody MovieRequest movieRequest) {
		
		return movieService.createMovie(movieRequest);
	}
	
	@GetMapping("/{id}")
	public MovieResponse getMovieById(@PathVariable Long id) {
		
		return movieService.getMovieById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMovie(@PathVariable Long id) {
		movieService.deleteMovie(id);
	}
	
	@PutMapping("/{id}")
	public MovieResponse updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequest movieRequest) {
		return movieService.updateMovie(id, movieRequest);
	}
	
	@GetMapping
	public Page<MovieResponse> getMovies(@PageableDefault(size=10,sort="id") Pageable pageable){
		return movieService.getMovies(pageable);
	}
	
}