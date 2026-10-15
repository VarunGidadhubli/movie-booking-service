package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long>{

}