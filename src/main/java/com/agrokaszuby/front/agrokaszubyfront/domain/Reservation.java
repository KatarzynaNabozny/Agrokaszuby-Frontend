package com.agrokaszuby.front.agrokaszubyfront.domain;


import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import lombok.*;

import java.math.BigDecimal;
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
    private Currency currency;
    private BigDecimal price;


    private Reservation(ReservationBuilder builder) {
        this.reservationId = builder.reservationId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
        this.street = builder.street;
        this.email = builder.email;
        this.currency = builder.currency;
        this.price = builder.price;
    }


    public static class ReservationBuilder {

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

        public ReservationBuilder() {
        }

        public ReservationBuilder withReservationId(Long reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public ReservationBuilder withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationBuilder withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ReservationBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ReservationBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ReservationBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ReservationBuilder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public ReservationBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public ReservationBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ReservationBuilder withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public ReservationBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Reservation build() {
            Reservation reservation =  new Reservation(this);
            return reservation;
        }
    }
}
