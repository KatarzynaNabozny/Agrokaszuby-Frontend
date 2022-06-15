package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    @Test
    void builder() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now();
        //WHEN
        Reservation reservation = new Reservation.ReservationBuilder()
                .withReservationId(1L)
                .withStartDate(now)
                .withEndDate(now)
                .withFirstName("first")
                .withLastName("last")
                .withPhoneNumber("4546")
                .withCity("City")
                .withPostalCode("456461")
                .withStreet("Street")
                .withEmail("email")
                .withCurrency(Currency.PLN)
                .withPrice(BigDecimal.TEN)
                .build();
        //THEN
        assertEquals(1L, reservation.getReservationId());
        assertEquals(now, reservation.getStartDate());
        assertEquals(now, reservation.getEndDate());
        assertEquals("first", reservation.getFirstName());
        assertEquals("last", reservation.getLastName());
        assertEquals("4546", reservation.getPhoneNumber());
        assertEquals("City", reservation.getCity());
        assertEquals("456461", reservation.getPostalCode());
        assertEquals("Street", reservation.getStreet());
        assertEquals("email", reservation.getEmail());
        assertEquals(Currency.PLN, reservation.getCurrency());
        assertEquals(BigDecimal.TEN, reservation.getPrice());

    }
}
