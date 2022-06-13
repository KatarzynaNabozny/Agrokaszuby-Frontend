package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.CurrencyExchangeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private PriceService priceService;

    @Test
    void getPrice_shouldCalculateCorrectPLNPrice() {
        //GIVEN
        //WHEN
        BigDecimal actualPrice = priceService.getPrice(LocalDateTime.now(), LocalDateTime.now().plusDays(1), Currency.PLN);
        //THEN
        BigDecimal expectedPrice = BigDecimal.valueOf(200);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void getPrice_shouldCalculateCorrectUSDPrice() {
        //GIVEN
        CurrencyExchangeDTO currencyDTO = CurrencyExchangeDTO.builder().rate(BigDecimal.valueOf(0.23)).build();
        given(currencyExchangeService.getExchangeRatePLNtoUSD()).willReturn(currencyDTO);
        //WHEN
        BigDecimal actualPrice = priceService.getPrice(LocalDateTime.now(), LocalDateTime.now().plusDays(2), Currency.USD);
        //THEN
        BigDecimal expectedPrice = new BigDecimal(92);
        assertEquals(expectedPrice.setScale(2), actualPrice);
    }
}