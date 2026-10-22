package com.movie.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie.booking.entity.Seat;

import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    boolean existsByScreenIdAndSeatNumber(Long screenId, String seatNumber);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id = :seatId")
    Optional<Seat> findByIdForUpdate(@Param("seatId") Long seatId);
}