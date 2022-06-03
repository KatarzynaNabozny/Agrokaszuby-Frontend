package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.service.ReservationService;
import com.agrokaszuby.front.agrokaszubyfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class ReservationForm extends FormLayout {

    private ReservationService service = ReservationService.getInstance();

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

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Reservation> binder = new Binder<>(Reservation.class);


    public ReservationForm(MainView mainView) {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        startDate = getDateTimePicker("Beginning of reservation");
        endDate = getDateTimePicker("End of reservation");
        add(startDate, endDate, firstName, lastName, phoneNumber,
                city, postalCode, street, email, buttons);
        setSizeFull();

        binder.bindInstanceFields(this);
        this.mainView = mainView;

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
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
        service.saveReservation(reservation);
        setReservation(null);
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
