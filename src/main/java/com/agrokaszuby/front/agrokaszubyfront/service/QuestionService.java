package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Comment;
import com.agrokaszuby.front.agrokaszubyfront.domain.QuestionLog;
import com.agrokaszuby.front.agrokaszubyfront.domain.Question;
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
public class QuestionService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String questionUrl = "http://localhost:8090/agrokaszuby/backend/question";
    private final String deleteQuestionBySubjectAndEmailUrl = questionUrl + "/delete/subject_email";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private QuestionLogService questionLogService;

    @Autowired
    public QuestionService(QuestionLogService questionLogService) {
        this.questionLogService = questionLogService;
    }

    public void saveQuestion(Question question) {

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.findAndRegisterModules();
        String questionInString = null;
        try {
            questionInString = objectMapper.writeValueAsString(question);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(questionInString, headers);
        ResponseEntity<Question> questionResponseEntity = null;
        try {
            questionResponseEntity = restTemplate.postForEntity(questionUrl, request, Question.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        QuestionLog questionLog = getQuestionLog(question.getEmail(), isSuccessful(questionResponseEntity), "SAVE");
        questionLogService.save(questionLog);
    }

    public void deleteQuestion(Question question) {
        Boolean isSuccessful = Boolean.FALSE;
        if (question != null) {
            String email = question.getEmail();
            String subject = question.getSubject();
            if (email != null && subject != null) {
                restTemplate = new RestTemplate();
                try {
                    String deleteUrl = deleteQuestionBySubjectAndEmailUrl + "?subject=" + subject + "&email=" + email;
                    restTemplate.delete(deleteUrl);
                    isSuccessful = Boolean.TRUE;
                } catch (RestClientException e) {
                    isSuccessful = Boolean.FALSE;
                    e.printStackTrace();
                }
            }
        }
        
        QuestionLog questionLog = getQuestionLog(question.getEmail(), isSuccessful, "ROLLBACK");
        questionLogService.save(questionLog);
    }

    private QuestionLog getQuestionLog(String email, Boolean successful, String event) {
        return QuestionLog.builder()
                .email(email)
                .event(event)
                .successful(successful)
                .date(LocalDateTime.now())
                .build();
    }

    private Boolean isSuccessful(ResponseEntity<Question> questionResponseEntity) {
        Boolean successful;
        if (questionResponseEntity != null) {
            successful = Boolean.TRUE;
        } else {
            successful = Boolean.FALSE;
        }
        return successful;
    }

}