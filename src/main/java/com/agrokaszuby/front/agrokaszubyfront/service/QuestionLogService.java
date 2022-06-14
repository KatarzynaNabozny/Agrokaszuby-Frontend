package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.QuestionLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionLogService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String questionLogUrl = "http://localhost:8090/agrokaszuby/backend/question_log";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(QuestionLog questionLog) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.findAndRegisterModules();
        String questionLogInString = null;
        try {
            questionLogInString = objectMapper.writeValueAsString(questionLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(questionLogInString, headers);
        ResponseEntity<QuestionLog> questionLogResponseEntity = null;
        try {
            questionLogResponseEntity = restTemplate.postForEntity(questionLogUrl, request, QuestionLog.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

    }
}
