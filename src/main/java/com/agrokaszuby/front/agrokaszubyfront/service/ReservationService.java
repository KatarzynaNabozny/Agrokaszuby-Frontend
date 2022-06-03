package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Movie;
import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ReservationService {

    private static ReservationService reservationService;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
//    private JSONObject movieJsonObject;
    private final String createReservationUrl = "http://localhost:8090/agrokaszuby/backend";;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void sendReservationMVP1(Reservation reservation) throws IOException {

        URL url = new URL("http://localhost:8084/movies");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
    }

//    public void sendReservationMovie(Reservation reservation) throws IOException {
//        createReservationUrl = "http://localhost:8084/movies";
//
//        restTemplate = new RestTemplate();
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        movieJsonObject = new JSONObject();
//        movieJsonObject.put("id", 8);
//        movieJsonObject.put("title", "Title");
//        movieJsonObject.put("author", "Some Autor");
//
//        HttpEntity<String> request = new HttpEntity<String>(movieJsonObject.toString(), headers);
//
//        ResponseEntity<Movie> responseEntityMovie = restTemplate.postForEntity(createReservationUrl, request, Movie.class);
//        Movie movie = responseEntityMovie.getBody();
//        String title = movie.getTitle();
//
//    }

    public void sendReservation(Reservation reservation) {
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
