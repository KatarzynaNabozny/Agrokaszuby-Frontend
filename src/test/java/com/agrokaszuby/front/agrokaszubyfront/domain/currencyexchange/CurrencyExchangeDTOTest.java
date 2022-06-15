package com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyExchangeDTOTest {

    @Test
    void builder() {
        //GIVEN
        LocalDate now = LocalDate.now();
        //WHEN
        CurrencyExchangeDTO exchangeDTO = CurrencyExchangeDTO.builder()
                .currencyExchangeId(1L)
                .fromCurrency("PLN")
                .toCurrency("USD")
                .rate(BigDecimal.TEN)
                .date(now)
                .build();
        //THEN
        assertEquals(1L, exchangeDTO.getCurrencyExchangeId());
        assertEquals("PLN", exchangeDTO.getFromCurrency());
        assertEquals("USD", exchangeDTO.getToCurrency());
        assertEquals(BigDecimal.TEN, exchangeDTO.getRate());
        assertEquals(now, exchangeDTO.getDate());

    }
}