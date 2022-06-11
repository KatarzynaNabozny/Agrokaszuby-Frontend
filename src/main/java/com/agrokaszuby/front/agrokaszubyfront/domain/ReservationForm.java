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
import com.vaadin.flow.data.binder.ErrorLevel;
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
        price.getElement().getStyle().set("color", "red");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setEnabled(false);
        delete.setEnabled(false);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        startDate = getDateTimePicker("Beginning of reservation");
        endDate = getDateTimePicker("End of reservation");
        add(startDate, endDate, firstName, lastName, phoneNumber,
                city, postalCode, street, email, currency, price, buttons);
        setSizeFull();

        startDate.setRequiredIndicatorVisible(true);
        endDate.setRequiredIndicatorVisible(true);
        endDate.setHelperText("Must be after Beginning of reservation");
        email.setRequired(true);
        currency.setRequired(true);

        binder.bindInstanceFields(this);
        this.mainView = mainView;

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        currency.addValueChangeListener(event -> updatePriceIfNeeded(binder.getBean()));
        startDate.addValueChangeListener(event -> updatePriceIfNeeded(binder.getBean()));
        endDate.addValueChangeListener(event -> updatePriceIfNeeded(binder.getBean()));

        email.addValueChangeListener(event -> isReadyToSave(binder.getBean()));


    }

    private Binder.BindingBuilder<Reservation, String> validateEmptyTextField(
            String errorMessage, TextField textField) {
        return binder.forField(textField)
                .withValidator(e -> {
                    textField.addClassName("warn");
                    return e.equals("") || e != null;
                }, errorMessage, ErrorLevel.WARNING);
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
        if (isReadyToSave(reservation)) {
            service.saveReservation(reservation);
            setReservation(null);
            save.setEnabled(false);
        }
    }

    private boolean isReadyToSave(Reservation reservation) {
        if (isReadyToDelete(reservation) &&
                reservation.getCurrency() != null &&
                reservation.getPrice() != null
        ) {
            save.setEnabled(true);
            return true;
        } else {
            save.setEnabled(false);
            return false;
        }
    }

    private boolean isReadyToDelete(Reservation reservation) {
        if (reservation.getStartDate() != null &&
                reservation.getEndDate() != null &&
                isValidEmail(reservation) &&
                isEndDateAfterStartDate(reservation)
        ) {
            delete.setEnabled(true);
            return true;
        } else {
            delete.setEnabled(false);
            return false;
        }
    }

    private boolean isValidEmail(Reservation reservation) {
        String emailValue = reservation.getEmail();
        if (emailValue != null && !emailValue.isEmpty()) {
            return true;
        } else {
            validateEmptyTextField("e-mail is required", this.email)
                    .bind(Reservation::getEmail, Reservation::setEmail);
            return false;
        }
    }

    private boolean isReadyToUpdatePrice(Reservation reservation) {
        if (reservation.getStartDate() != null &&
                reservation.getEndDate() != null &&
                reservation.getCurrency() != null &&
                isEndDateAfterStartDate(reservation)
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEndDateAfterStartDate(Reservation reservation) {
        LocalDateTime endDate = reservation.getEndDate();
        LocalDateTime startDate = reservation.getStartDate();

        if (startDate == null || endDate == null) {
            return false;
        } else if (endDate.isAfter(startDate)) {
            return true;
        } else {
            binder.forField(this.startDate)
                    .withValidator(start -> {
                        if (start != null) {
                            return start.isBefore(endDate);
                        } else {
                            return false;
                        }
                    }, "Start date should be before end date of reservation")
                    .bind(Reservation::getStartDate, Reservation::setStartDate);

            binder.forField(this.endDate)
                    .withValidator(end -> {
                        if (end != null) {
                            return end.isAfter(startDate);
                        } else {
                            return false;
                        }
                    }, "End date should be after start date of reservation")
                    .bind(Reservation::getEndDate, Reservation::setEndDate);
            return false;
        }
    }

    private void updatePriceIfNeeded(Reservation reservation) {
        if (reservation != null) {
            if (isReadyToUpdatePrice(reservation)) {
                updatePrice(reservation);
            }
            isReadyToSave(reservation);
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
