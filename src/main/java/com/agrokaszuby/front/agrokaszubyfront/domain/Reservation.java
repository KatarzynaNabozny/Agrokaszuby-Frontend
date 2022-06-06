package com.agrokaszuby.front.agrokaszubyfront.domain;


import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Reservation {

    private Long reservationId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String postalCode;
    private String street;
    private String email;
    private Currency currency;
    private BigDecimal price;

}
