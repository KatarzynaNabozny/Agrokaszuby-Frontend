package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.CurrencyExchangeDTO;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.CurrencyExchange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@NoArgsConstructor
public class CurrencyExchangeService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String currencyExchangeUrl = "http://localhost:8090/agrokaszuby/backend/currency_exchange";
    private final String currencyExchangeSearchByFromToCurrencyAndDateUrl = "http://localhost:8090/agrokaszuby/backend/" +
            "currency_exchange/search/from_to_currency_date?";

    private final String EXCHANGE_USD_PLN_URL = "https://openexchangerates.org/api/latest.json?" +
            "app_id=bdf9664efe044ad1b1821463563b756d" +
            "&symbols=PLN";

    public CurrencyExchangeDTO getExchangeRatePLNtoUSD() {
        CurrencyExchangeDTO currencyDTO = getCurrencyExchange();
        if (currencyDTO == null) {
            restTemplate = new RestTemplate();
            ResponseEntity<CurrencyExchange> currencyExchangeEntity = null;
            try {
                currencyExchangeEntity = restTemplate.getForEntity(EXCHANGE_USD_PLN_URL, CurrencyExchange.class);
            } catch (RestClientException e) {
                e.printStackTrace();
            }
            CurrencyExchange currencyExchange = currencyExchangeEntity.getBody();

            long timestamp = currencyExchange.getTimestamp() * 1000;
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

            Double plnRate = currencyExchange.getRates().get(Currency.PLN);
            BigDecimal rateUSDToPLN = BigDecimal.valueOf(Double.valueOf(plnRate));
            BigDecimal ratePLNtoUSD = BigDecimal.ONE.divide(rateUSDToPLN, new MathContext(15));

            currencyDTO = CurrencyExchangeDTO.builder()
                    .fromCurrency(Currency.PLN.name())
                    .toCurrency(Currency.USD.name())
                    .rate(ratePLNtoUSD)
                    .date(dateTime.toLocalDate())
                    .build();

            saveCurrencyExchange(currencyDTO);
        }

        return currencyDTO;
    }

    private void saveCurrencyExchange(CurrencyExchangeDTO currencyExchangeDTO) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.findAndRegisterModules();

        String currencyExchangeInString = null;
        try {
            currencyExchangeInString = objectMapper.writeValueAsString(currencyExchangeDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(currencyExchangeInString, headers);
        ResponseEntity<CurrencyExchangeDTO> currencyExchangeResponseEntity = null;
        try {
            currencyExchangeResponseEntity = restTemplate.postForEntity(currencyExchangeUrl, request, CurrencyExchangeDTO.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    private CurrencyExchangeDTO getCurrencyExchange() {
        String fromCurrency = Currency.PLN.name();
        String toCurrency = Currency.USD.name();
        LocalDate date = LocalDate.now();
        String parameters = "fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency + "&date=" + date;

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.findAndRegisterModules();

        ResponseEntity<CurrencyExchangeDTO> currencyExchangeEntity = null;
        try {
            currencyExchangeEntity = restTemplate.getForEntity(currencyExchangeSearchByFromToCurrencyAndDateUrl + parameters,
                    CurrencyExchangeDTO.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return currencyExchangeEntity != null ? currencyExchangeEntity.getBody() : null;
    }

}