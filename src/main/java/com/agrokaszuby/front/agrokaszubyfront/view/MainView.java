package com.agrokaszuby.front.agrokaszubyfront.view;

import com.agrokaszuby.front.agrokaszubyfront.domain.*;
import com.agrokaszuby.front.agrokaszubyfront.domain.currencyexchange.Currency;
import com.agrokaszuby.front.agrokaszubyfront.domain.weather.WeatherDTO;
import com.agrokaszuby.front.agrokaszubyfront.service.CommentService;
import com.agrokaszuby.front.agrokaszubyfront.service.PriceService;
import com.agrokaszuby.front.agrokaszubyfront.service.QuestionService;
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
    private Button comment = new Button("Leave a comment");

    private ReservationForm form;
    private QuestionForm questionForm;
    private CommentForm commentForm;

    @Autowired
    public MainView(QuestionService questionService, CommentService commentService, PriceService priceService) {
        this.form = new ReservationForm(priceService, this);
        this.questionForm = new QuestionForm(questionService, this);
        this.commentForm = new CommentForm(commentService, this);

        grid.setColumns("date", "maxTemperature");

        addReservation.addClickListener(e -> {
            form.setReservation(new Reservation.ReservationBuilder().withCurrency(Currency.PLN).build());
        });

        question.addClickListener(e -> {
            questionForm.setQuestion(Question.builder().date(LocalDateTime.now()).build());
        });

        comment.addClickListener(e -> {
            commentForm.setComment(Comment.builder().date(LocalDateTime.now()).build());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addReservation, question, comment);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form, questionForm, commentForm);
        grid.setWidth("440px");

        add(toolbar, mainContent);

        form.setReservation(null);
        questionForm.setQuestion(null);
        commentForm.setComment(null);

        refreshTemperatureForecast();
    }

    public void refreshTemperatureForecast() {
        grid.setItems(weatherService.getSevenDayForecast());
    }
}