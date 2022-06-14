package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.ReservationLog;
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
public class ReservationLogService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String reservationLogUrl = "http://localhost:8090/agrokaszuby/backend/reservation_log";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(ReservationLog reservationLog) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.findAndRegisterModules();
        String reservationLogInString = null;
        try {
            reservationLogInString = objectMapper.writeValueAsString(reservationLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(reservationLogInString, headers);
        ResponseEntity<ReservationLog> reservationLogResponseEntity = null;
        try {
            reservationLogResponseEntity = restTemplate.postForEntity(reservationLogUrl, request, ReservationLog.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

    }
}
