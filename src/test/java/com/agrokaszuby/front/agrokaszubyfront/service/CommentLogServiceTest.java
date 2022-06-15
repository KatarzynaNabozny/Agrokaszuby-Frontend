package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.CommentLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentLogServiceTest {

    private final String commentLogUrl = "http://localhost:8090/agrokaszuby/backend/comment_log";

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CommentLogService commentLogService;

    @Test
    public void save_shouldSaveCommentLog() throws JsonProcessingException {
        //GIVEN
        CommentLog commentLog = CommentLog.builder().event("SAVE").build();
        HttpEntity<String> request = getStringHttpEntity();
        //WHEN
        when(objectMapper.writeValueAsString(commentLog)).thenReturn("");
        when(restTemplate.postForEntity(commentLogUrl, request, CommentLog.class))
                .thenReturn(new ResponseEntity(commentLog, HttpStatus.OK));
        //THEN
        commentLogService.save(commentLog);
        verify(restTemplate, Mockito.times(1)).postForEntity(commentLogUrl, request, CommentLog.class);
    }

    private HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("", headers);
        return request;
    }
}