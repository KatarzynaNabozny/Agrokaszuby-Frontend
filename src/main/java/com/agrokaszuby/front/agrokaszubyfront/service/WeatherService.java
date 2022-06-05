package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.weather.Weather;
import com.agrokaszuby.front.agrokaszubyfront.domain.weather.WeatherDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private static WeatherService weatherService;
    private RestTemplate restTemplate;
    private final String AGROKASZUBY_WEATHER_FORECAST_URL = "https://api.openweathermap.org/data/2.5/onecall?" +
            "lat=54.45169&lon=18.21428&appid=29dfcc1425030e06fc5850aa06fc9ce1" +
            "&lang=en&units=metric&exclude=current,minutely,hourly";

    private WeatherService() {
    }

    public static WeatherService getInstance() {
        if (weatherService == null) {
            weatherService = new WeatherService();
        }
        return weatherService;
    }

    public List<WeatherDTO> getSevenDayForecast() {
        restTemplate = new RestTemplate();
        ResponseEntity<Weather> weatherResponseEntity = null;
        try {
            weatherResponseEntity = restTemplate.getForEntity(AGROKASZUBY_WEATHER_FORECAST_URL, Weather.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        List<WeatherDTO> weatherDTOs = new ArrayList<>();
        weatherResponseEntity.getBody().getDaily().stream().forEach(dailyForecast -> {
            long dateInMilliseconds = dailyForecast.getDt() * 1000;
            LocalDate date = LocalDate.ofInstant(Instant.ofEpochMilli(dateInMilliseconds), ZoneId.systemDefault());

            float maxTemperature = dailyForecast.getTemp().getMax();

            WeatherDTO weatherDTO = WeatherDTO.builder().maxTemperature(maxTemperature).date(date).build();
            weatherDTOs.add(weatherDTO);
        });

        return weatherDTOs;
    }
}