package com.movie.booking.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Show;
import com.movie.booking.entity.ShowStatus;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM Show s
        WHERE s.screen.id = :screenId
          AND s.showDate = :showDate
          AND s.startTime < :newEndTimeWithBuffer
          AND s.endTime > :newStartTimeMinusBuffer
        """)
    boolean existsConflictingShow(
            @Param("screenId") Long screenId,
            @Param("showDate") LocalDate showDate,
            @Param("newStartTimeMinusBuffer") LocalTime newStartTimeMinusBuffer,
            @Param("newEndTimeWithBuffer") LocalTime newEndTimeWithBuffer);
    
    @Modifying
    @Query("""
        UPDATE Show s
        SET s.status = :running
        WHERE s.status = :notStarted
          AND (
              s.showDate < :today
              OR (s.showDate = :today AND s.startTime <= :now)
          )
          AND s.showDate = :today
          AND s.endTime > :now
        """)
    int markShowsRunning(
        @Param("today") LocalDate today,
        @Param("now") LocalTime now,
        @Param("notStarted") ShowStatus notStarted,
        @Param("running") ShowStatus running
    );
    
    @Modifying
    @Query("""
        UPDATE Show s
        SET s.status = :completed
        WHERE s.status IN (:notStarted, :running)
          AND (
              s.showDate < :today
              OR (s.showDate = :today AND s.endTime <= :now)
          )
        """)
    int markShowsCompleted(
        @Param("today") LocalDate today,
        @Param("now") LocalTime now,
        @Param("notStarted") ShowStatus notStarted,
        @Param("running") ShowStatus running,
        @Param("completed") ShowStatus completed
    );
}