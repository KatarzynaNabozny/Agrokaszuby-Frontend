package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.QuestionLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionLogService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private HttpHeaders headers;
    private final String questionLogUrl = "http://localhost:8090/agrokaszuby/backend/question_log";

    @Autowired
    public QuestionLogService(RestTemplate restTemplate, ObjectMapper objectMapper, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.headers = headers;
    }

    public void save(QuestionLog questionLog) {
        String questionLogInString = null;
        try {
            questionLogInString = objectMapper.writeValueAsString(questionLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<QuestionLog> questionLogResponseEntity = null;
        try {
            questionLogResponseEntity = restTemplate.postForEntity(questionLogUrl, getRequest(questionLogInString), QuestionLog.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    protected HttpEntity<String> getRequest(String objectInString) {
        if(headers == null || headers.getContentType() == null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            headers = httpHeaders;
        }
        return new HttpEntity<>(objectInString, headers);
    }
}
