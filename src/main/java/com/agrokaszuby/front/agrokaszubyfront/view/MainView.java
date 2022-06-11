package com.agrokaszuby.front.agrokaszubyfront.view;

import com.agrokaszuby.front.agrokaszubyfront.domain.Reservation;
import com.agrokaszuby.front.agrokaszubyfront.domain.ReservationForm;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.domain.weather.WeatherDTO;
import com.agrokaszuby.front.agrokaszubyfront.service.PriceService;
import com.agrokaszuby.front.agrokaszubyfront.service.WeatherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@PreserveOnRefresh
@Route("")
public class MainView extends VerticalLayout {

    private WeatherService weatherService = WeatherService.getInstance();
    private Grid<WeatherDTO> grid = new Grid<>(WeatherDTO.class);
    private Button addReservation = new Button("Reservation");

    private PriceService priceService;
    private ReservationForm form;

    @Autowired
    public MainView(PriceService priceService) {
        this.priceService = priceService;
        this.form = new ReservationForm(priceService, this);

        grid.setColumns("date", "maxTemperature");

        addReservation.addClickListener(e -> {
            form.setReservation(Reservation.builder().currency(Currency.PLN).build());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addReservation);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        grid.setWidth("440px");

        add(toolbar, mainContent);
        form.setReservation(null);
        refreshTemperatureForecast();
    }

    public void refreshTemperatureForecast() {
        grid.setItems(weatherService.getSevenDayForecast());
    }
}