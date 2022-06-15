package com.agrokaszuby.front.agrokaszubyfront.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationLogTest {

    @Test
    void builder() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now();
        //WHEN
        ReservationLog reservationLog = ReservationLog.builder()
                .reservationLogId(1L)
                .email("email")
                .event("event")
                .successful(Boolean.TRUE)
                .date(now)
                .build();
        //THEN
        assertEquals(1L, reservationLog.getReservationLogId());
        assertEquals("email", reservationLog.getEmail());
        assertEquals("event", reservationLog.getEvent());
        assertEquals(Boolean.TRUE, reservationLog.getSuccessful());
        assertEquals(now, reservationLog.getDate());
    }
}
