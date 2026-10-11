package com.movie.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	boolean existsByNameIgnoreCase(String name);
	Optional<Role> findByNameIgnoreCase(String name);
}