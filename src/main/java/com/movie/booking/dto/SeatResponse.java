package com.movie.booking.dto;

public class SeatResponse {

    private Long id;
    private Long screenId;
    private String seatNumber;
    private Long seatTypeId;
    private String status;

    public SeatResponse(Long id, Long screenId,String seatNumber,Long seatTypeId, String status) {
        this.id = id;
        this.screenId = screenId;
        this.seatNumber = seatNumber;
        this.seatTypeId = seatTypeId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getScreenId() {
        return screenId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Long getSeatTypeId() {
        return seatTypeId;
    }

    public String getStatus() {
        return status;
    }
}