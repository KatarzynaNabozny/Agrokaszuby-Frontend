package com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

    private LocalDateTime timeStamp;
    private Currency base;
    private BigDecimal rate;
}
