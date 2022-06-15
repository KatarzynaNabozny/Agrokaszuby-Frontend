package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.QuestionLog;
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
class QuestionLogServiceTest {

    private final String questionLogUrl = "http://localhost:8090/agrokaszuby/backend/question_log";

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private QuestionLogService questionLogService;

    @Test
    public void save_shouldSaveQuestionLog() throws JsonProcessingException {
        //GIVEN
        QuestionLog questionLog = QuestionLog.builder().event("SAVE").build();
        HttpEntity<String> request = getStringHttpEntity();
        //WHEN
        when(objectMapper.writeValueAsString(questionLog)).thenReturn("");
        when(restTemplate.postForEntity(questionLogUrl, request, QuestionLog.class))
                .thenReturn(new ResponseEntity(questionLog, HttpStatus.OK));
        //THEN
        questionLogService.save(questionLog);
        verify(restTemplate, Mockito.times(1)).postForEntity(questionLogUrl, request, QuestionLog.class);
    }

    private HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("", headers);
        return request;
    }
}