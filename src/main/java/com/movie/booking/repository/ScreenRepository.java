package com.movie.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Screen;
import com.movie.booking.entity.ScreenStatus;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long>{

	boolean existsByTheatreIdAndScreenNumberAndStatus(Long theatreId,Integer screenNumber,ScreenStatus status);
	Optional<Screen> findByTheatreIdAndScreenNumberAndStatus(Long theatreId, Integer screenNumber, ScreenStatus status);
}