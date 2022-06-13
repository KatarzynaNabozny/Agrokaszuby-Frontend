package com.agrokaszuby.front.agrokaszubyfront.view;

import com.agrokaszuby.front.agrokaszubyfront.domain.Question;
import com.agrokaszuby.front.agrokaszubyfront.domain.QuestionForm;
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

import java.time.LocalDateTime;

@Component
@PreserveOnRefresh
@Route("")
public class MainView extends VerticalLayout {

    private WeatherService weatherService = WeatherService.getInstance();
    private Grid<WeatherDTO> grid = new Grid<>(WeatherDTO.class);
    private Button addReservation = new Button("Reservation");
    private Button question = new Button("Question");

    private PriceService priceService;
    private ReservationForm form;
    private QuestionForm questionForm;

    @Autowired
    public MainView(PriceService priceService) {
        this.priceService = priceService;
        this.form = new ReservationForm(priceService, this);
        this.questionForm = new QuestionForm(this);

        grid.setColumns("date", "maxTemperature");

        addReservation.addClickListener(e -> {
            form.setReservation(new Reservation.ReservationBuilder().withCurrency(Currency.PLN).build());
        });

        question.addClickListener(e -> {
            questionForm.setQuestion(Question.builder().date(LocalDateTime.now()).build());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addReservation, question);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form, questionForm);
        grid.setWidth("440px");

        add(toolbar, mainContent);
        form.setReservation(null);
        questionForm.setQuestion(null);
        refreshTemperatureForecast();
    }

    public void refreshTemperatureForecast() {
        grid.setItems(weatherService.getSevenDayForecast());
    }
}