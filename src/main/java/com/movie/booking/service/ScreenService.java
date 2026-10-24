package com.movie.booking.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.dto.ScreenRequest;
import com.movie.booking.dto.ScreenResponse;
import com.movie.booking.entity.Screen;
import com.movie.booking.entity.ScreenStatus;
import com.movie.booking.entity.Theatre;
import com.movie.booking.entity.User;
import com.movie.booking.exception.DuplicateRecordException;
import com.movie.booking.exception.RecordNotFoundException;
import com.movie.booking.repository.ScreenRepository;
import com.movie.booking.repository.TheatreRepository;
import com.movie.booking.repository.UserRepository;

@Service
public class ScreenService {

	private final ScreenRepository screenRepository;
	private final TheatreRepository theatreRepository;
	private final UserRepository userRepository;

	public ScreenService(ScreenRepository screenRepository, TheatreRepository theatreRepository,
			UserRepository userRepository) {
		this.screenRepository = screenRepository;
		this.theatreRepository = theatreRepository;
		this.userRepository = userRepository;
	}

	public ScreenResponse createScreen(ScreenRequest screenRequest) {

		Theatre theatre = theatreRepository.findById(screenRequest.getTheatreId()).orElseThrow(
				() -> new RecordNotFoundException("Theatre not found with id " + screenRequest.getTheatreId()));

		User currentUser = getAuthentication();
		boolean isAdmin = currentUser.getRole().getName().equalsIgnoreCase("ADMIN");

		boolean isOwner = theatre.getCreatedByUser().getId().equals(currentUser.getId());

		if (!isAdmin && !isOwner) {
			throw new AccessDeniedException("You can only manage screens for your own theatres");
		}
		;

		if (screenRepository.existsByTheatreIdAndScreenNumberAndStatus(screenRequest.getTheatreId(),
				screenRequest.getScreenNumber(),ScreenStatus.ACTIVE)) {
			throw new DuplicateRecordException("Theatre id and Screen already exists");
		}

		Screen screen = new Screen();
		screen.setTheatre(theatre);
		screen.setScreenNumber(screenRequest.getScreenNumber());
		screen.setStatus(screenRequest.getStatus());
		Screen savedScreen = screenRepository.save(screen);

		return new ScreenResponse(savedScreen.getId(), savedScreen.getTheatre().getId(), savedScreen.getScreenNumber(),
				savedScreen.getStatus());
	}

	@Transactional
	public void updateScreenStatus(Long theatreId, Integer screenNumber, ScreenStatus status) {

		Theatre theatre = theatreRepository.findById(theatreId)
				.orElseThrow(() -> new RecordNotFoundException("Theatre doesn't exist: " + theatreId));

		User currentUser = getAuthentication();

		boolean isAdmin = currentUser.getRole().getName().equalsIgnoreCase("ADMIN");
		boolean isOwner = theatre.getCreatedByUser().getId().equals(currentUser.getId());

		if (!isAdmin && !isOwner) {
			throw new AccessDeniedException("You can only update screens for theatres created by you");
		}

		Screen screen = screenRepository.findByTheatreIdAndScreenNumberAndStatus(theatreId, screenNumber,ScreenStatus.ACTIVE).orElseThrow(
				() -> new IllegalStateException("Screen " + screenNumber + " doesn't exist for theatre " + theatreId));

		screen.setStatus(status);
		screenRepository.save(screen);
	}

	private User getAuthentication() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			throw new IllegalStateException("Authenticated JWT is required");
		}

		Long userId = Long.valueOf(jwt.getSubject());

		return userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("Authenticated user not found"));
	}
}