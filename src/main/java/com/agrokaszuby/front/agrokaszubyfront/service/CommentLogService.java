package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.CommentLog;
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
public class CommentLogService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String commentLogUrl = "http://localhost:8090/agrokaszuby/backend/comment_log";

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public void save(CommentLog commentLogLog) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.findAndRegisterModules();
        String commentLogInString = null;
        try {
            commentLogInString = objectMapper.writeValueAsString(commentLogLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(commentLogInString, headers);
        ResponseEntity<CommentLog> commentLogResponseEntity = null;
        try {
            commentLogResponseEntity = restTemplate.postForEntity(commentLogUrl, request, CommentLog.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
    }
}
