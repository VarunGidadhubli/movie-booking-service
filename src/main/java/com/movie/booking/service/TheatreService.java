package com.movie.booking.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.dto.ScreenResponse;
import com.movie.booking.dto.TheatreRequest;
import com.movie.booking.dto.TheatreResponse;
import com.movie.booking.entity.Screen;
import com.movie.booking.entity.Theatre;
import com.movie.booking.entity.TheatreStatus;
import com.movie.booking.entity.User;
import com.movie.booking.exception.RecordNotFoundException;
import com.movie.booking.repository.TheatreRepository;
import com.movie.booking.repository.UserRepository;

@Service
public class TheatreService {

	private final TheatreRepository theatreRepository;
	private final UserRepository userRepository;

	public TheatreService(TheatreRepository theatreRepository, UserRepository userRepository) {
		this.theatreRepository = theatreRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public TheatreResponse createTheatre(TheatreRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			throw new IllegalStateException("Authenticated JWT is required");
		}

		Long userId = Long.valueOf(jwt.getSubject());

		User creator = userRepository.findById(userId)
				.orElseThrow(() -> new RecordNotFoundException("Authenticated user not found"));

		Theatre theatre = new Theatre();
		theatre.setName(request.getName());
		theatre.setAddress(request.getAddress());
		theatre.setCity(request.getCity());
		theatre.setPincode(request.getPincode());
		theatre.setStatus(TheatreStatus.PENDING);
		theatre.setCreatedByUser(creator);

		Theatre savedTheatre = theatreRepository.save(theatre);

		return toResponse(savedTheatre);
	}

	@Transactional(readOnly = true)
	public TheatreResponse getTheatreById(Long id) {

		Theatre theatre = theatreRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Theatre not found with id " + id));

		return toResponse(theatre);
	}

	@Transactional
	public void deleteTheatre(Long id) {

		Theatre theatre = theatreRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Theatre not found with id " + id));

		theatre.setStatus(TheatreStatus.DELETED);
		theatreRepository.save(theatre);
	}

	@Transactional
	public TheatreResponse approveTheatre(Long id) {

		Theatre theatre = theatreRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Theatre not found with id " + id));

		if (theatre.getStatus() != TheatreStatus.PENDING) {
			throw new IllegalStateException("Only " + TheatreStatus.PENDING + " can be approved");
		}

		User admin = getAuthenticatedUser();
		theatre.setStatus(TheatreStatus.APPROVED);
		theatre.setApprovedByUser(admin);

		Theatre savedTheatre = theatreRepository.save(theatre);
		return toResponse(savedTheatre);
	}
	
	@Transactional
	public TheatreResponse rejectTheatre(Long id) {
		Theatre theatre = theatreRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Theatre not found with id " + id));

		if (theatre.getStatus() != TheatreStatus.PENDING) {
			throw new IllegalStateException("Only pending theatres can be rejected");
		}

		theatre.setStatus(TheatreStatus.REJECTED);

		Theatre savedTheatre = theatreRepository.save(theatre);
		return toResponse(savedTheatre);
	}
	
	@Transactional
	public List<ScreenResponse> getScreensByTheatreId(Long theatreId){
		
		Theatre theatre = theatreRepository.findById(theatreId).
				orElseThrow(()->new RecordNotFoundException("Theatre is not found "+theatreId));
		List<Screen> listOfScreens = theatre.getScreens();
		
		return listOfScreens.stream().map(screen->new ScreenResponse(screen.getId(), 
				screen.getTheatre().getId(), screen.getScreenNumber(), screen.getStatus())).toList();
	}

	private TheatreResponse toResponse(Theatre theatre) {

		Long createdByUserId = theatre.getCreatedByUser().getId();

		Long approvedByUserId = theatre.getApprovedByUser() == null ? null : theatre.getApprovedByUser().getId();

		return new TheatreResponse(theatre.getId(), theatre.getName(), theatre.getAddress(), theatre.getCity(),
				theatre.getPincode(), theatre.getStatus(), createdByUserId, approvedByUserId);
	}

	private User getAuthenticatedUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
			throw new IllegalStateException("Authenticated JWT is required");
		}

		Long userId = Long.valueOf(jwt.getSubject());
		return userRepository.findById(userId)
				.orElseThrow(() -> new RecordNotFoundException("Authenticated user not found"));
	}
}