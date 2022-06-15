package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
import com.agrokaszuby.front.agrokaszubyfront.domain.ReservationLog;
import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
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

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private static ReservationService reservationService;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String reservationUrl = "http://localhost:8090/agrokaszuby/backend/reservation";
    private ObjectMapper objectMapper;
    private ReservationLogService reservationLogService;

    private ReservationService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);

        this.reservationLogService = new ReservationLogService(
                restTemplate, objectMapper, headers);
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void saveReservation(Reservation reservation) {
        String reservationInString = null;
        try {
            reservationInString = objectMapper.writeValueAsString(reservation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(reservationInString, headers);
        ResponseEntity<Reservation> reservationResponseEntity = null;
        try {
            reservationResponseEntity = restTemplate.postForEntity(reservationUrl, request, Reservation.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        ReservationLog reservationLog = getReservationLog(reservation.getEmail(), isSuccessful(reservationResponseEntity), "SAVE");
        reservationLogService.save(reservationLog);
    }

    public void delete(Reservation reservation) {
        Boolean isSuccessful = Boolean.FALSE;
        if (reservation != null) {
            String email = reservation.getEmail();
            LocalDateTime startDate = reservation.getStartDate();
            LocalDateTime endDate = reservation.getEndDate();

            if (email != null && startDate != null && endDate != null) {
                restTemplate = new RestTemplate();
                try {
                    String deleteUrl = reservationUrl + "?email=" + email + "&startDate=" + startDate + "&endDate=" + endDate;
                    restTemplate.delete(deleteUrl);
                    isSuccessful = Boolean.TRUE;
                } catch (RestClientException e) {
                    isSuccessful = Boolean.FALSE;
                    e.printStackTrace();
                }
            }
        }
        ReservationLog reservationLog = getReservationLog(reservation.getEmail(), isSuccessful, "DELETE");
        reservationLogService.save(reservationLog);
    }

    private ReservationLog getReservationLog(String email, Boolean successful, String event) {
        return ReservationLog.builder()
                .email(email)
                .event(event)
                .successful(successful)
                .date(LocalDateTime.now())
                .build();
    }

    private Boolean isSuccessful(ResponseEntity<Reservation> reservationResponseEntity) {
        Boolean successful;
        if (reservationResponseEntity != null) {
            successful = Boolean.TRUE;
        } else {
            successful = Boolean.FALSE;
        }
        return successful;
    }
}
