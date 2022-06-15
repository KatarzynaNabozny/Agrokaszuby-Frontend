package com.agrokaszuby.front.agrokaszubyfront.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentLogTest {
    
    @Test
    void builder() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now();
        //WHEN
        CommentLog commentLog = CommentLog.builder()
                .commentLogId(1L)
                .email("email")
                .event("event")
                .successful(Boolean.TRUE)
                .date(now)
                .build();
        //THEN
        assertEquals(1L, commentLog.getCommentLogId());
        assertEquals("email", commentLog.getEmail());
        assertEquals("event", commentLog.getEvent());
        assertEquals(Boolean.TRUE, commentLog.getSuccessful());
        assertEquals(now, commentLog.getDate());
    }
}
