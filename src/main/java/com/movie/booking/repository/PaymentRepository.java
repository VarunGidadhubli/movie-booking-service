package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}