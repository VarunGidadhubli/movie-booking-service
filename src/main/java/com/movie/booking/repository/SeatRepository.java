package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.booking.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    boolean existsByScreenIdAndSeatNumber(Long screenId, String seatNumber);
}