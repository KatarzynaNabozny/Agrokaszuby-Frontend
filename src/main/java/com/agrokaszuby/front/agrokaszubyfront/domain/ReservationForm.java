package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.service.PriceService;
import com.agrokaszuby.front.agrokaszubyfront.service.ReservationService;
import com.agrokaszuby.front.agrokaszubyfront.service.WeatherService;
import com.agrokaszuby.front.agrokaszubyfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Component
public class ReservationForm extends FormLayout {

    private ReservationService service = ReservationService.getInstance();
    private WeatherService weatherservice = WeatherService.getInstance();

    private PriceService priceService;

    private MainView mainView;
    private DateTimePicker startDate;
    private DateTimePicker endDate;

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField phoneNumber = new TextField("Phone number");

    private TextField city = new TextField("City");
    private TextField postalCode = new TextField("Postal code");
    private TextField street = new TextField("Street");
    private TextField email = new TextField("E-mail");

    private ComboBox<Currency> currency = new ComboBox<>("Currency");
    private BigDecimalField price = new BigDecimalField("Price");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Reservation> binder = new Binder<>(Reservation.class);

    @Autowired
    public ReservationForm(PriceService priceService, MainView mainView) {
        this.priceService = priceService;
        currency.setItems(Currency.values());
        price.setEnabled(false);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        startDate = getDateTimePicker("Beginning of reservation");
        endDate = getDateTimePicker("End of reservation");
        add(startDate, endDate, firstName, lastName, phoneNumber,
                city, postalCode, street, email, currency, price, buttons);
        setSizeFull();

        startDate.setRequiredIndicatorVisible(true);
        endDate.setRequiredIndicatorVisible(true);
        email.setRequired(true);
        currency.setRequired(true);

        binder.bindInstanceFields(this);
        this.mainView = mainView;

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        currency.addValueChangeListener(event -> updatePriceIfNeeded());
        startDate.addValueChangeListener(event -> updatePriceIfNeeded());
        endDate.addValueChangeListener(event -> updatePriceIfNeeded());
    }

    private DateTimePicker getDateTimePicker(String caption) {
        DateTimePicker picker = new DateTimePicker();
        picker.setLabel(caption);

        picker.getElement().executeJs(
                "this.initialPosition = $0",
                getStartOrNextMonthIsoString()
        );
        return picker;
    }

    private String getStartOrNextMonthIsoString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate startOfNextMonth = LocalDate.now(ZoneId.systemDefault())
                .with(TemporalAdjusters.firstDayOfNextMonth());

        return formatter.format(startOfNextMonth);
    }

    private void save() {
        Reservation reservation = binder.getBean();
        if (reservation.getStartDate() != null &&
                reservation.getEndDate() != null &&
                reservation.getEmail() != null &&
                reservation.getCurrency() != null &&
                reservation.getPrice() != null) {
            service.saveReservation(reservation);
            setReservation(null);
        }
    }

    private void updatePriceIfNeeded() {
        Reservation reservation = binder.getBean();
        if (reservation != null) {
            Currency currency = reservation.getCurrency();
            LocalDateTime startDate = reservation.getStartDate();
            LocalDateTime endDate = reservation.getEndDate();
            if (currency != null && startDate != null && endDate != null) {
                updatePrice(reservation);
            }
        }
    }

    private void updatePrice(Reservation reservation) {
        BigDecimal price = priceService.getPrice(reservation.getStartDate(), reservation.getEndDate(), reservation.getCurrency());
        reservation.setPrice(price.setScale(2, BigDecimal.ROUND_HALF_UP));
        binder.setBean(reservation);
    }

    private void delete() {
        Reservation reservation = binder.getBean();
        service.delete(reservation);
        setReservation(null);
    }

    public void setReservation(Reservation reservation) {
        binder.setBean(reservation);
        if (reservation == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }
}
