package com.agrokaszuby.front.agrokaszubyfront.domain;

import com.agrokaszuby.front.agrokaszubyfront.service.CommentService;
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
class CommentFormTest {

    @Mock
    private CommentService service;
    @Mock
    private MainView mainView;
    @Mock
    private Binder<Comment> binder;

    @InjectMocks
    CommentForm commentForm;

    @Test
    void back() {
        //GIVEN
        commentForm.setVisible(true);
        //WHEN
        commentForm.back();
        //THEN
        assertEquals(false, commentForm.isVisible());
    }

    @Test
    void delete() {
        //GIVEN
        commentForm.setVisible(true);
        //WHEN
        commentForm.delete();
        //THEN
        assertEquals(false, commentForm.isVisible());
    }

    @Test
    void save() {
        //GIVEN
        commentForm.setVisible(true);
        //WHEN
        commentForm.save();
        //THEN
        assertEquals(false, commentForm.isVisible());
    }

    @Test
    void setComment() {
        //GIVEN
        commentForm.setVisible(true);
        //WHEN
        commentForm.setComment(null);
        //THEN
        assertEquals(false, commentForm.isVisible());
    }
}