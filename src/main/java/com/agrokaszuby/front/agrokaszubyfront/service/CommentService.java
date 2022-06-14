package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Comment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String commentUrl = "http://localhost:8090/agrokaszuby/backend/comment";
    private final String deleteCommentBySubjectAndEmailUrl = commentUrl + "/delete/subject_email";

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    }

    public void deleteComment(Comment comment) {
        if (comment != null) {
            String email = comment.getEmail();
            String subject = comment.getSubject();
            if (email != null && subject != null) {
                restTemplate = new RestTemplate();
                try {
                    String deleteUrl = deleteCommentBySubjectAndEmailUrl + "?subject=" + subject + "&email=" + email;
                    restTemplate.delete(deleteUrl);
                } catch (RestClientException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
