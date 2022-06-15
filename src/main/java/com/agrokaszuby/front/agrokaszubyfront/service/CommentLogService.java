package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.CommentLog;
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
public class CommentLogService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private HttpHeaders headers;
    private final String commentLogUrl = "http://localhost:8090/agrokaszuby/backend/comment_log";


    @Autowired
    public CommentLogService(RestTemplate restTemplate, ObjectMapper objectMapper, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.headers = headers;
    }

    public void save(CommentLog commentLog) {
        String commentLogInString = null;
        try {
            commentLogInString = objectMapper.writeValueAsString(commentLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<CommentLog> commentLogResponseEntity = null;
        try {
            commentLogResponseEntity = restTemplate.postForEntity(commentLogUrl, getRequest(commentLogInString), CommentLog.class);
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
