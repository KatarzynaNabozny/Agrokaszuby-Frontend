package com.agrokaszuby.front.agrokaszubyfront.domain;


import com.agrokaszuby.front.agrokaszubyfront.service.CommentService;
import com.agrokaszuby.front.agrokaszubyfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentForm extends FormLayout {
    private final int CHAR_LIMIT = 350;
    private CommentService service;

    private MainView mainView;

    private TextField fromName = new TextField("Your name");
    private TextField email = new TextField("E-mail");
    private TextArea content = new TextArea("Content");
    private TextField subject = new TextField("Subject");

    private Button send = new Button("Send");
    private Button rollback = new Button("Rollback");
    private Button back = new Button("Back");

    private Binder<Comment> binder = new Binder<>(Comment.class);

    @Autowired
    public CommentForm(CommentService service, MainView mainView) {
        this.service = service;
        content.setLabel("Comment");
        content.setMaxLength(CHAR_LIMIT);
        content.setValueChangeMode(ValueChangeMode.EAGER);
        content.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + CHAR_LIMIT);
        });
        content.setPlaceholder("Here you can give a comment after stay in Agrokaszuby");

        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(send, rollback, back);

        add(fromName, email, subject, content, buttons);
        setSizeFull();

        email.setRequired(true);
        subject.setRequired(true);

        binder.bindInstanceFields(this);
        this.mainView = mainView;

        send.addClickListener(event -> save());
        rollback.addClickListener(event -> delete());
        back.addClickListener(event -> back());
    }

    void back() {
        setComment(null);
    }

    void delete() {
        Comment comment = binder.getBean();
        service.deleteComment(comment);
        setComment(null);
    }

    void save() {
        Comment comment = binder.getBean();
        service.saveComment(comment);
        setComment(null);
    }

    public void setComment(Comment comment) {
        binder.setBean(comment);
        if (comment == null) {
            setVisible(false);
        } else {
            setVisible(true);
            fromName.focus();
        }
    }


}
