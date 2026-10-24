package com.movie.booking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movie.booking.dto.UserRequest;
import com.movie.booking.dto.UserResponse;
import com.movie.booking.entity.Role;
import com.movie.booking.entity.User;
import com.movie.booking.exception.DuplicateRecordException;
import com.movie.booking.exception.RecordNotFoundException;
import com.movie.booking.repository.RoleRepository;
import com.movie.booking.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final EntityManager entityManager;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			EntityManager entityManager) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.entityManager = entityManager;
	}
	
	@Transactional
	public UserResponse registerCustomer(UserRequest userRequest) {
		
		if(userRepository.existsByEmailIgnoreCase(userRequest.getEmail())) {
			throw new DuplicateRecordException("Email id already exists");
		}
		
		Role customerRole=roleRepository.findByNameIgnoreCase("CUSTOMER").
				orElseThrow(()->new RecordNotFoundException("Custome role doesn't exist"));
		
		User user=new User();
		user.setUsername(userRequest.getUsername());
		user.setEmail(userRequest.getEmail());
		user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
		user.setRole(customerRole);
		User savedUser=userRepository.save(user);
		entityManager.flush();
		entityManager.refresh(savedUser);
		
		return new UserResponse(savedUser.getId(),savedUser.getUsername(),savedUser.getEmail(),
                savedUser.getRole().getId(),savedUser.getRole().getName(),savedUser.getCreatedAt(),
                savedUser.getUpdatedAt());
	}
}