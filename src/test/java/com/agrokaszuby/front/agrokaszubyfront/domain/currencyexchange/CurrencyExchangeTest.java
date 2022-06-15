package com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeTest {

    CurrencyExchange currencyExchange = new CurrencyExchange();

    @Test
    void setTimestamp() {
        //GIVEN
        int expected = 45;
        //WHEN
        currencyExchange.setTimestamp(expected);
        //THEN
        assertEquals(expected, currencyExchange.getTimestamp());
    }

    @Test
    void setBase() {
        //GIVEN
        String expected = "45";
        currencyExchange.setBase(expected);
        //THEN
        assertEquals(expected, currencyExchange.getBase());
    }

    @Test
    void setRates() {
        //GIVEN
        LinkedHashMap<Currency,Double> expected = new LinkedHashMap<>();
        //WHEN
        currencyExchange.setRates(expected);
        //THEN
        assertEquals(expected, currencyExchange.getRates());
    }
}