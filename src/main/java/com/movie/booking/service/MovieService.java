package com.movie.booking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.movie.booking.dto.MovieRequest;
import com.movie.booking.dto.MovieResponse;
import com.movie.booking.entity.Movie;
import com.movie.booking.exception.RecordNotFoundException;
import com.movie.booking.repository.MovieRepository;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public MovieResponse createMovie(MovieRequest movieRequest) {

		Movie movie = new Movie();
		movie.setTitle(movieRequest.getTitle());
		movie.setReleaseDate(movieRequest.getReleaseDate());
		movie.setDurationMinutes(movieRequest.getDurationMinutes());
		movie.setDescription(movieRequest.getDescription());
		movie.setLanguage(movieRequest.getLanguage());

		Movie responseMovie = movieRepository.save(movie);

		return new MovieResponse(responseMovie.getId(), responseMovie.getTitle(), responseMovie.getReleaseDate(),
				responseMovie.getDurationMinutes(), responseMovie.getDescription(), responseMovie.getLanguage());
	}

	public void deleteMovie(Long id) {
		Movie movie=movieRepository.findById(id).orElseThrow(()->new RecordNotFoundException("Movie not found with id: " + id));
		movieRepository.delete(movie);
	}

	public MovieResponse getMovieById(Long id) {

		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Movie not found with id: " + id));
		return new MovieResponse(movie.getId(), movie.getTitle(), movie.getReleaseDate(), movie.getDurationMinutes(),
				movie.getDescription(), movie.getLanguage());
	}

	public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {

		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Movie not found with id: " + id));
		movie.setTitle(movieRequest.getTitle());
		movie.setReleaseDate(movieRequest.getReleaseDate());
		movie.setDurationMinutes(movieRequest.getDurationMinutes());
		movie.setDescription(movieRequest.getDescription());
		movie.setLanguage(movieRequest.getLanguage());

		Movie updatedMovie = movieRepository.save(movie);
		return new MovieResponse(updatedMovie.getId(), updatedMovie.getTitle(), updatedMovie.getReleaseDate(),
				updatedMovie.getDurationMinutes(), updatedMovie.getDescription(), updatedMovie.getLanguage());
	}

	public Page<MovieResponse> getMovies(Pageable pageable) {

		Page<Movie> movies = movieRepository.findAll(pageable);
		return movies.map(movie -> new MovieResponse(movie.getId(), movie.getTitle(), movie.getReleaseDate(),
				movie.getDurationMinutes(), movie.getDescription(), movie.getLanguage()));
	}

}