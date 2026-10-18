package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.SeatType;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long>{

	boolean existsByName(String name);
}