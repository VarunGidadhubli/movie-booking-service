package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	@Modifying
	@Query(value = """
	    UPDATE payments p
	    JOIN booking b ON b.id = p.booking_id
	    SET p.status = 'EXPIRED'
	    WHERE b.status = 'PENDING'
	      AND p.status = 'INITIATED'
	      AND EXISTS (
	          SELECT 1
	          FROM booking_seats bs
	          WHERE bs.booking_id = b.id
	            AND bs.lock_expiry < CURRENT_TIMESTAMP
	      )
	    """, nativeQuery = true)
	int expireInitiatedPayments();
}