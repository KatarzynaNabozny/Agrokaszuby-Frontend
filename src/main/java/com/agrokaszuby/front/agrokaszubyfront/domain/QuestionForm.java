package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.stereotype.Component;

@Component
public class QuestionForm extends FormLayout {

    private final int CHAR_LIMIT = 350;
    private Binder<Question> binder = new Binder<>(Question.class);

    private MainView mainView;

    private TextField from = new TextField("Your name");
    private TextField subject = new TextField("Subject");
    private TextField email = new TextField("E-mail");
    private TextArea content = new TextArea("Content");

    private Button send = new Button("Send");
    private Button rollback = new Button("Rollback");
    private Button back = new Button("Back");

    public QuestionForm(MainView mainView) {

        content.setLabel("Question");
        content.setMaxLength(CHAR_LIMIT);
        content.setValueChangeMode(ValueChangeMode.EAGER);
        content.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + CHAR_LIMIT);
        });
        content.setPlaceholder("Here you can ask your question");

        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(send, rollback, back);

        add(from, email, subject, content, buttons);
        setSizeFull();
        this.mainView = mainView;

        send.addClickListener(event -> save());
        rollback.addClickListener(event -> delete());
        back.addClickListener(event -> back());
    }

    private void back() {
        setQuestion(null);
    }

    private void delete() {
        setQuestion(null);
    }

    private void save() {
        setQuestion(null);
    }


    public void setQuestion(Question question) {
        binder.setBean(question);
        if (question == null) {
            setVisible(false);
        } else {
            setVisible(true);
            from.focus();
        }
    }
}
