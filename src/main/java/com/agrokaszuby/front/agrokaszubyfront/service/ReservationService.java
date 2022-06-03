package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
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
public class ReservationService {

    private static ReservationService reservationService;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String createReservationUrl = "http://localhost:8090/agrokaszuby/backend";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void saveReservation(Reservation reservation) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.findAndRegisterModules();
        String reservationInString = null;
        try {
            reservationInString = objectMapper.writeValueAsString(reservation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(reservationInString, headers);
        ResponseEntity<Reservation> reservationResponseEntity = null;
        try {
            reservationResponseEntity = restTemplate.postForEntity(createReservationUrl, request, Reservation.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    public void delete(Reservation reservation) {
    }
}
