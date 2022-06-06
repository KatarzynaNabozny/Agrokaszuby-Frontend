package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.CurrencyDTO;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.CurrencyExchange;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@NoArgsConstructor
public class CurrencyExchangeService {

    private RestTemplate restTemplate;

    private final String EXCHANGE_USD_PLN_URL = "https://openexchangerates.org/api/latest.json?" +
            "app_id=bdf9664efe044ad1b1821463563b756d" +
            "&symbols=PLN";

    public CurrencyDTO getExchangeRatePLNtoUSD() {
        restTemplate = new RestTemplate();
        ResponseEntity<CurrencyExchange> currencyExchangeEntity = null;
        try {
            currencyExchangeEntity = restTemplate.getForEntity(EXCHANGE_USD_PLN_URL, CurrencyExchange.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        CurrencyExchange currencyExchange = currencyExchangeEntity.getBody();

        long timestamp = currencyExchange.getTimestamp() * 1000;
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        Double plnRate = currencyExchange.getRates().get(Currency.PLN);
        BigDecimal rateUSDToPLN = BigDecimal.valueOf(Double.valueOf(plnRate));
        BigDecimal ratePLNtoUSD = BigDecimal.ONE.divide(rateUSDToPLN, new MathContext(15));

        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .base(Currency.valueOf(currencyExchange.getBase()))
                .timeStamp(date)
                .rate(ratePLNtoUSD)
                .build();

        return currencyDTO;
    }


}