package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public void shouldGetDate() {
        //GIVEN
        //WHEN
        LocalDate actualTimeStamp = currencyExchangeService.getExchangeRatePLNtoUSD().getDate();
        //THEN
        assertNotNull(actualTimeStamp);
    }

    @Test
    public void shouldGetUSDToCurrency() {
        //GIVEN
        //WHEN
        String actualBaseCurrency = currencyExchangeService.getExchangeRatePLNtoUSD().getToCurrency();
        //THEN
        assertEquals(Currency.USD.name(), actualBaseCurrency);
    }

}