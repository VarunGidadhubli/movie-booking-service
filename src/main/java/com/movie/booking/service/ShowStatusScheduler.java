package com.movie.booking.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.movie.booking.entity.ShowStatus;
import com.movie.booking.repository.ShowRepository;

@Component
public class ShowStatusScheduler {

    private static final ZoneId INDIA_ZONE = ZoneId.of("Asia/Kolkata");

    private final ShowRepository showRepository;

    public ShowStatusScheduler(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Scheduled(fixedDelay = 900000)
    @Transactional
    public void updateShowStatuses() {
        LocalDateTime now = LocalDateTime.now(INDIA_ZONE);

        int completed = showRepository.markShowsCompleted(
                now.toLocalDate(),
                now.toLocalTime(),
                ShowStatus.NOT_STARTED,
                ShowStatus.RUNNING,
                ShowStatus.COMPLETED
        );

        int running = showRepository.markShowsRunning(
                now.toLocalDate(),
                now.toLocalTime(),
                ShowStatus.NOT_STARTED,
                ShowStatus.RUNNING
        );

        if (running > 0 || completed > 0) {
            System.out.println("Show statuses updated. Running: " + running + ", completed: " + completed);
        }
    }
}