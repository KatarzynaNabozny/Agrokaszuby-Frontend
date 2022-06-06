package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class CurrencyExchangeServiceTest {

    private CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    @Test
    public void shouldGetCurrencyExchangeRate() {
        //GIVEN
        //WHEN
        BigDecimal actualRate = currencyExchangeService.getExchangeRatePLNtoUSD().getRate();
        //THEN
        assertNotNull(actualRate);
    }

    @Test
    public void shouldGetTimeStamp() {
        //GIVEN
        //WHEN
        LocalDateTime actualTimeStamp = currencyExchangeService.getExchangeRatePLNtoUSD().getTimeStamp();
        //THEN
        assertNotNull(actualTimeStamp);
    }

    @Test
    public void shouldGetUSDBase() {
        //GIVEN
        //WHEN
        Currency actualBaseCurrency = currencyExchangeService.getExchangeRatePLNtoUSD().getBase();
        //THEN
        assertEquals(Currency.USD, actualBaseCurrency);
    }

}