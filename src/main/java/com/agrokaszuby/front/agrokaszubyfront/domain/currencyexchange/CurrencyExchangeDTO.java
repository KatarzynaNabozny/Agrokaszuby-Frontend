package com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeDTO {
    private Long currencyExchangeId;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
    private LocalDate date;
}
