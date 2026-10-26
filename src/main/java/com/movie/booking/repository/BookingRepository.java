package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Booking;
import com.movie.booking.entity.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

	@Modifying
	@Query("""
	    UPDATE Booking b
	    SET b.status = :expiredStatus
	    WHERE b.status = :pendingStatus
	      AND EXISTS (
	          SELECT bs.id
	          FROM BookingSeat bs
	          WHERE bs.booking.id = b.id
	      )
	      AND NOT EXISTS (
	          SELECT bs.id
	          FROM BookingSeat bs
	          WHERE bs.booking.id = b.id
	            AND bs.status <> 'EXPIRED'
	      )
	    """)
	int expireBookingsWithAllSeatsExpired(
	        @Param("pendingStatus") BookingStatus pendingStatus,
	        @Param("expiredStatus") BookingStatus expiredStatus);
	
	@Modifying
	@Query(value = """
			UPDATE bookings b
			SET b.status = 'EXPIRED'
			WHERE b.status = 'PENDING'
			  AND EXISTS (
			      SELECT 1
			      FROM payments p
			      WHERE p.booking_id = b.id
			        AND p.status = 'EXPIRED'
			  )
			""", nativeQuery = true)
	int expirePendingBookings();
}
