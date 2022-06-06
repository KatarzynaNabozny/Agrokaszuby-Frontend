package com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchange {
    private long timestamp;
    private String base;
    private LinkedHashMap<Currency,Double> rates;
}
