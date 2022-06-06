package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PriceService {

    private BigDecimal PLN_PRICE_PER_DAY_FOR_HOUSE_RENT = BigDecimal.valueOf(200);
    private BigDecimal USD_PRICE_PER_DAY_FOR_HOUSE_RENT = null;

    private final CurrencyExchangeService service;

    @Autowired
    public PriceService(CurrencyExchangeService service) {
        this.service = service;
    }

    public BigDecimal getPrice(LocalDateTime from, LocalDateTime to, Currency currency) {
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal numberOfDays = BigDecimal.valueOf(getSleepingNumberOfDays(from, to));

        if(numberOfDays.equals(BigDecimal.ZERO)){
            numberOfDays = numberOfDays.add(BigDecimal.ONE);
        }

        if (currency.equals(Currency.PLN)) {
            price = getPLNPricePerDay().multiply(numberOfDays);
        } else if (currency.equals(Currency.USD)) {
            BigDecimal ratePLNtoUSD = service.getExchangeRatePLNtoUSD().getRate();
            BigDecimal usdOneDayRentPrice = getPLNPricePerDay().multiply(ratePLNtoUSD);
            setUSDPrice(usdOneDayRentPrice);

            price = getUSDPricePerDay().multiply(numberOfDays);
        }

        return price;
    }

    private long getSleepingNumberOfDays(LocalDateTime from, LocalDateTime to) {
        Duration between = Duration.between(from, to);
        return between.toDays();
    }

    public BigDecimal getUSDPricePerDay() {
        return USD_PRICE_PER_DAY_FOR_HOUSE_RENT;
    }

    public BigDecimal getPLNPricePerDay() {
        return PLN_PRICE_PER_DAY_FOR_HOUSE_RENT;
    }

    public void setPLNPrice(BigDecimal price) {
        this.PLN_PRICE_PER_DAY_FOR_HOUSE_RENT = price;
    }

    public void setUSDPrice(BigDecimal price) {
        this.USD_PRICE_PER_DAY_FOR_HOUSE_RENT = price;
    }
}
