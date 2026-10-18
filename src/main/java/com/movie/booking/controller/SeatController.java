package com.movie.booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.movie.booking.dto.BulkSeatRequest;
import com.movie.booking.dto.SeatResponse;
import com.movie.booking.service.SeatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/screen")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/{screenId}/seats")
    @ResponseStatus(HttpStatus.CREATED)
    public List<SeatResponse> createSeats(@PathVariable Long screenId,@Valid @RequestBody BulkSeatRequest bulkSeatRequest) {

        return seatService.createSeats(screenId, bulkSeatRequest);
    }
}