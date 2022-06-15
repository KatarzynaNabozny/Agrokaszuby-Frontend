package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.ReservationLog;
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
public class ReservationLogService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private HttpHeaders headers;
    private final String reservationLogUrl = "http://localhost:8090/agrokaszuby/backend/reservation_log";


    @Autowired
    public ReservationLogService(RestTemplate restTemplate, ObjectMapper objectMapper, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.headers = headers;
    }

    public void save(ReservationLog reservationLog) {
        String reservationLogInString = null;
        try {
            reservationLogInString = objectMapper.writeValueAsString(reservationLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ReservationLog> reservationLogResponseEntity = null;
        try {
            reservationLogResponseEntity = restTemplate.postForEntity(reservationLogUrl, getRequest(reservationLogInString), ReservationLog.class);
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
