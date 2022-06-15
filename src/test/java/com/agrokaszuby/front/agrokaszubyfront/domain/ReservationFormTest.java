package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.service.PriceService;
import com.agrokaszuby.front.agrokaszubyfront.view.MainView;
import com.vaadin.flow.data.binder.Binder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationFormTest {

    @Mock
    private PriceService priceService;
    @Mock
    private MainView mainView;
    @Mock
    private Binder<Reservation> binder;

    @InjectMocks
    ReservationForm reservationForm;

    @Test
    void back() {
        //GIVEN
        reservationForm.setVisible(true);
        //WHEN
        reservationForm.back();
        //THEN
        assertEquals(false, reservationForm.isVisible());
    }

    @Test
    void getDateTimePicker() {
    }

    @Test
    void getStartOrNextMonthIsoString() {
    }

    @Test
    void isReadyToSave() {
        //GIVEN
        Reservation reservation = null;
        //WHEN
        boolean isReadyToSave = reservationForm.isReadyToSave(reservation);
        //THEN
        assertEquals(false, isReadyToSave);
    }

    @Test
    void isReadyToDelete() {
        //GIVEN
        Reservation reservation = null;
        //WHEN
        boolean isReadyToDelete = reservationForm.isReadyToDelete(reservation);
        //THEN
        assertEquals(false, isReadyToDelete);
    }

    @Test
    void isValidEmail() {
        //GIVEN
        Reservation reservation = new Reservation.ReservationBuilder().build();
        //WHEN
        boolean isValidEmail = reservationForm.isValidEmail(reservation);
        //THEN
        assertEquals(false, isValidEmail);
    }

    @Test
    void isReadyToUpdatePrice() {
        //GIVEN
        Reservation reservation = new Reservation.ReservationBuilder().build();
        //WHEN
        boolean isReadyToUpdatePrice = reservationForm.isReadyToUpdatePrice(reservation);
        //THEN
        assertEquals(false, isReadyToUpdatePrice);
    }

    @Test
    void isEndDateAfterStartDate() {
    }

    @Test
    void updatePriceIfNeeded() {
        //GIVEN
        Reservation reservation = new Reservation.ReservationBuilder()
                .withStartDate(LocalDateTime.now())
                .withEndDate(LocalDateTime.now().plusDays(3))
                .withCurrency(Currency.PLN)
                .build();
        //WHEN
        when(priceService.getPrice(any(), any(), any())).thenReturn(BigDecimal.TEN);
        reservationForm.updatePriceIfNeeded(reservation);
        //THEN
        assertEquals(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP), reservation.getPrice());
    }

    @Test
    void updatePrice() {
    }

    @Test
    void setReservation() {
    }
}