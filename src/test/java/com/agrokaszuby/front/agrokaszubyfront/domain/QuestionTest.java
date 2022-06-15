package com.agrokaszuby.front.agrokaszubyfront.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {

    @Test
    void builder() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now();
        //WHEN
        Question question = Question.builder()
                .email("email")
                .date(now)
                .content("content")
                .subject("subject")
                .questionId(1L)
                .fromName("name")
                .build();
        //THEN
        assertEquals("email", question.getEmail());
        assertEquals(now, question.getDate());
        assertEquals("content", question.getContent());
        assertEquals("subject", question.getSubject());
        assertEquals(1L, question.getQuestionId());
        assertEquals("name", question.getFromName());
    }

}