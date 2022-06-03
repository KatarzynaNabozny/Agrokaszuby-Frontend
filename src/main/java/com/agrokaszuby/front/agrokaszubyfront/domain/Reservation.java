package com.agrokaszuby.front.agrokaszubyfront.domain;


import lombok.*;

import java.time.LocalDateTime;

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

}
