package com.agrokaszuby.front.agrokaszubyfront.view;

import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
import com.agrokaszuby.front.agrokaszubyfront.domain.ReservationForm;
import com.agrokaszuby.front.agrokaszubyfront.domain.weather.WeatherDTO;
import com.agrokaszuby.front.agrokaszubyfront.service.WeatherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private WeatherService weatherService = WeatherService.getInstance();
    private Grid<WeatherDTO> grid = new Grid<>(WeatherDTO.class);
    private Button addReservation = new Button("Reservation");
    private ReservationForm form = new ReservationForm(this);

    public MainView() {
        grid.setColumns("date", "maxTemperature");

        addReservation.addClickListener(e -> {
            form.setReservation(new Reservation());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addReservation);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        grid.setWidth("400px");

        add(toolbar, mainContent);
        form.setReservation(null);
        refreshTemperatureForecast();
    }

    public void refreshTemperatureForecast() {
        grid.setItems(weatherService.getSevenDayForecast());
    }
}