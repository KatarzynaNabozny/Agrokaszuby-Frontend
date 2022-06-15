package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.service.QuestionService;
import com.agrokaszuby.front.agrokaszubyfront.view.MainView;
import com.vaadin.flow.data.binder.Binder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionFormTest {

    @Mock
    private QuestionService service;
    @Mock
    private MainView mainView;
    @Mock
    private Binder<Question> binder;

    @InjectMocks
    QuestionForm questionForm;

    @Test
    void back() {
        //GIVEN
        questionForm.setVisible(true);
        //WHEN
        questionForm.back();
        //THEN
        assertEquals(false, questionForm.isVisible());
    }

    @Test
    void delete() {
        //GIVEN
        questionForm.setVisible(true);
        //WHEN
        questionForm.delete();
        //THEN
        assertEquals(false, questionForm.isVisible());
    }

    @Test
    void save() {
        //GIVEN
        questionForm.setVisible(true);
        //WHEN
        questionForm.save();
        //THEN
        assertEquals(false, questionForm.isVisible());
    }

    @Test
    void setQuestion() {
        //GIVEN
        questionForm.setVisible(true);
        //WHEN
        questionForm.setQuestion(null);
        //THEN
        assertEquals(false, questionForm.isVisible());
    }
}