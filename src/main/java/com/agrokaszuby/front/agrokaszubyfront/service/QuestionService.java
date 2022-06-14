package com.agrokaszuby.front.agrokaszubyfront.service;

import com.agrokaszuby.front.agrokaszubyfront.domain.Question;
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
public class QuestionService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private final String questionUrl = "http://localhost:8090/agrokaszuby/backend/question";
    private final String deleteQuestionBySubjectAndEmailUrl = questionUrl + "/delete/subject_email";

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    }

    public void deleteQuestion(Question question) {
        if (question != null) {
            String email = question.getEmail();
            String subject = question.getSubject();
            if (email != null && subject != null) {
                restTemplate = new RestTemplate();
                try {
                    String deleteUrl = deleteQuestionBySubjectAndEmailUrl + "?subject=" + subject + "&email=" + email;
                    restTemplate.delete(deleteUrl);
                } catch (RestClientException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}