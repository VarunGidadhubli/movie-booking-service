package com.movie.booking.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.BookingSeat;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

	@Query("""
			SELECT COUNT(bs)
			FROM BookingSeat bs
			WHERE bs.show.id = :showId
			  AND bs.seat.id = :seatId
			  AND (
			      bs.status = 'CONFIRMED'
			      OR (
			          bs.status = 'PENDING'
			          AND bs.lockExpiry > :now
			      )
			  )
			""")
	long countActiveReservations(@Param("showId") Long showId, @Param("seatId") Long seatId,
			@Param("now") LocalDateTime now);

	@Modifying
	@Query("""
			UPDATE BookingSeat bs
			SET bs.status = 'EXPIRED'
			WHERE bs.show.id = :showId
			  AND bs.seat.id = :seatId
			  AND bs.status = 'PENDING'
			  AND bs.lockExpiry <= :now
			""")
	int expirePendingReservation(@Param("showId") Long showId, @Param("seatId") Long seatId,
			@Param("now") LocalDateTime now);

	@Modifying
	@Query("""
			UPDATE BookingSeat bs
			SET bs.status = 'EXPIRED'
			WHERE bs.status = 'PENDING'
			  AND bs.lockExpiry <= :now
			""")
	int expirePendingSeats(@Param("now") LocalDateTime now);
	
	@Modifying
	@Query(value = """
			UPDATE booking_seats bs
			JOIN booking b ON b.id = bs.booking_id
			JOIN payments p ON p.booking_id = b.id
			SET bs.status = 'NOT_BOOKED'
			WHERE b.status = 'PENDING'
			  AND p.status = 'INITIATED'
			  AND bs.lock_expiry < CURRENT_TIMESTAMP
			""", nativeQuery = true)
	int releaseExpiredSeats();

}