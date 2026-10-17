package com.movie.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long>{

	boolean existsByTheatreIdAndScreenNumber(Long theatreId, Integer screenNumber);
	Optional<Screen> findByTheatreIdAndScreenNumber(Long theatreId, Integer screenNumber);
}