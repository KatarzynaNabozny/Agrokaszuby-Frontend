package com.agrokaszuby.front.agrokaszubyfront.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    
    @Test
    void builder() {
        LocalDateTime now = LocalDateTime.now();
        Comment comment = Comment.builder()
                .email("email")
                .date(now)
                .content("content")
                .subject("subject")
                .commentId(1L)
                .fromName("name")
                .build();
        //THEN
        assertEquals("email", comment.getEmail());
        assertEquals(now, comment.getDate());
        assertEquals("content", comment.getContent());
        assertEquals("subject", comment.getSubject());
        assertEquals(1L, comment.getCommentId());
        assertEquals("name", comment.getFromName());
    }

}