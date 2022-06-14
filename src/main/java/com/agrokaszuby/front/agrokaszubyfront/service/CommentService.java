package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Comment;
import com.agrokaszuby.front.agrokaszubyfront.domain.CommentLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String commentUrl = "http://localhost:8090/agrokaszuby/backend/comment";
    private final String deleteCommentBySubjectAndEmailUrl = commentUrl + "/delete/subject_email";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private CommentLogService commentLogService;

    @Autowired
    public CommentService(CommentLogService commentLogService) {
        this.commentLogService = commentLogService;
    }

    public void saveComment(Comment comment) {

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.findAndRegisterModules();
        String commentInString = null;
        try {
            commentInString = objectMapper.writeValueAsString(comment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(commentInString, headers);
        ResponseEntity<Comment> commentResponseEntity = null;
        try {
            commentResponseEntity = restTemplate.postForEntity(commentUrl, request, Comment.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        CommentLog commentLog = getCommentLog(comment.getEmail(), isSuccessful(commentResponseEntity), "SAVE");
        commentLogService.save(commentLog);
    }

    private CommentLog getCommentLog(String email, Boolean successful, String event) {
        return CommentLog.builder()
                .email(email)
                .event(event)
                .successful(successful)
                .date(LocalDateTime.now())
                .build();
    }

    private Boolean isSuccessful(ResponseEntity<Comment> commentResponseEntity) {
        Boolean successful;
        if (commentResponseEntity != null) {
            successful = Boolean.TRUE;
        } else {
            successful = Boolean.FALSE;
        }
        return successful;
    }

    public void deleteComment(Comment comment) {
        Boolean isSuccessful = Boolean.FALSE;
        if (comment != null) {
            String email = comment.getEmail();
            String subject = comment.getSubject();
            if (email != null && subject != null) {
                restTemplate = new RestTemplate();
                try {
                    String deleteUrl = deleteCommentBySubjectAndEmailUrl + "?subject=" + subject + "&email=" + email;
                    restTemplate.delete(deleteUrl);
                    isSuccessful = Boolean.TRUE;
                } catch (RestClientException e) {
                    isSuccessful = Boolean.FALSE;
                    e.printStackTrace();
                }
            }
        }

        CommentLog commentLog = getCommentLog(comment.getEmail(), isSuccessful, "ROLLBACK");
        commentLogService.save(commentLog);
    }
}
