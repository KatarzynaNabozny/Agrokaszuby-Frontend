package com.agrokaszuby.front.agrokaszubyfront.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionLogTest {
    @Test
    void builder() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now();
        //WHEN
         QuestionLog  questionLog =  QuestionLog.builder()
                . questionLogId(1L)
                .email("email")
                .event("event")
                .successful(Boolean.TRUE)
                .date(now)
                .build();
        //THEN
        assertEquals(1L,  questionLog.getQuestionLogId());
        assertEquals("email",  questionLog.getEmail());
        assertEquals("event",  questionLog.getEvent());
        assertEquals(Boolean.TRUE,  questionLog.getSuccessful());
        assertEquals(now,  questionLog.getDate());
    }
}
