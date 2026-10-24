package com.movie.booking.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.dto.ShowRequest;
import com.movie.booking.dto.ShowResponse;
import com.movie.booking.entity.Movie;
import com.movie.booking.entity.Screen;
import com.movie.booking.entity.Show;
import com.movie.booking.entity.Theatre;
import com.movie.booking.exception.DuplicateRecordException;
import com.movie.booking.exception.RecordNotFoundException;
import com.movie.booking.repository.MovieRepository;
import com.movie.booking.repository.ScreenRepository;
import com.movie.booking.repository.ShowRepository;
import com.movie.booking.repository.TheatreRepository;

@Service
public class ShowService {

    private static final long SHOW_BUFFER_MINUTES = 20;

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    public ShowService(
            ShowRepository showRepository,
            MovieRepository movieRepository,
            TheatreRepository theatreRepository,
            ScreenRepository screenRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
    }

    @Transactional
    public ShowResponse createShow(ShowRequest request) {

        // 1. Verify that the movie exists.
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RecordNotFoundException("Movie not found: " + request.getMovieId()));

        // 2. Verify that the theatre exists.
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new RecordNotFoundException("Theatre not found: " + request.getTheatreId()));

        // 3. Verify that the screen exists.
        Screen screen = screenRepository.findById(request.getScreenId())
        		.orElseThrow(() -> new RecordNotFoundException("Screen not found: " + request.getScreenId()));

        // 4. Ensure that the screen belongs to the requested theatre.
        if (!screen.getTheatre().getId().equals(theatre.getId())) {
            throw new IllegalArgumentException("The selected screen does not belong to the selected theatre");
        }

        // 5. Calculate the show's end time from the movie runtime.
        LocalDateTime startDateTime = LocalDateTime.of(request.getShowDate(), request.getStartTime());

        LocalDateTime endDateTime = startDateTime.plusMinutes(movie.getDurationMinutes());

        if (!endDateTime.toLocalDate().equals(request.getShowDate())) {
            throw new IllegalArgumentException("Shows extending beyond midnight are not supported");
        }

        LocalTime endTime = endDateTime.toLocalTime();

        // 6. Calculate the 20-minute buffer boundaries.
        LocalTime newStartTimeMinusBuffer = request.getStartTime().minusMinutes(SHOW_BUFFER_MINUTES);

        LocalTime newEndTimeWithBuffer = endTime.plusMinutes(SHOW_BUFFER_MINUTES);

        // Reject shows whose buffer crosses midnight under this
        // same-date implementation.
        if (newStartTimeMinusBuffer.isAfter(request.getStartTime())
                || newEndTimeWithBuffer.isBefore(endTime)) {
            throw new IllegalArgumentException("The show and its 20-minute buffer must fit within the same date");
        }

        // 7. Check for conflicting shows on the same screen and date.
        boolean conflict = showRepository.existsConflictingShow(
                screen.getId(),
                request.getShowDate(),
                newStartTimeMinusBuffer,
                newEndTimeWithBuffer);

        if (conflict) {
            throw new DuplicateRecordException("Another show conflicts with this schedule or its 20-minute buffer");
        }

        // 8. Create and save the show.
        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setShowDate(request.getShowDate());
        show.setStartTime(request.getStartTime());
        show.setEndTime(endTime);
        show.setStatus(request.getStatus());

        Show savedShow = showRepository.save(show);

        // 9. Return the response.
        return new ShowResponse(
                savedShow.getId(),
                savedShow.getMovie().getId(),
                savedShow.getScreen().getId(),
                savedShow.getShowDate(),
                savedShow.getStartTime(),
                savedShow.getEndTime(),
                savedShow.getStatus());
    }
}