ALTER TABLE booking_seats
ADD COLUMN active_seat_id BIGINT
GENERATED ALWAYS AS (
    CASE
        WHEN status IN ('PENDING', 'CONFIRMED') THEN seat_id
        ELSE NULL
    END
) VIRTUAL;

CREATE UNIQUE INDEX uk_booking_seat_show_active
ON booking_seats (show_id, active_seat_id);

ALTER TABLE booking_seats
DROP INDEX uk_booking_seat_show;