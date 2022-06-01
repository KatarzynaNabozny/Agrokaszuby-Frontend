package com.agrokaszuby.front.agrokaszubyfront.view;

import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
import com.agrokaszuby.front.agrokaszubyfront.domain.ReservationForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private Button addReservation = new Button("Reservation");
    private ReservationForm form = new ReservationForm(this);

    public MainView() {
        addReservation.addClickListener(e -> {
            form.setReservation(new Reservation());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addReservation);
        HorizontalLayout mainContent = new HorizontalLayout(form);

        add(toolbar, mainContent);
        form.setReservation(null);
        setSizeFull();

    }

}