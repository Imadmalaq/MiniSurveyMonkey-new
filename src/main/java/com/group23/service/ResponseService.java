package com.group23.service;

import com.group23.model.Answer;
import com.group23.model.Question;
import com.group23.model.Response;
import com.group23.repository.QuestionRepository;
import com.group23.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling survey responses.
 */
@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, QuestionRepository questionRepository) {
        this.responseRepository = responseRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Saves a user's response to a survey.
     *
     * @param response the response to save
     * @return the saved response
     */
    @Transactional
    public Response saveResponse(Response response) {
        // Validate that all questions exist
        for (Answer answer : response.getAnswers()) {
            Long questionId = answer.getQuestion().getId();
            if (!questionRepository.existsById(questionId)) {
                throw new IllegalArgumentException("Question not found with ID: " + questionId);
            }
            answer.setResponse(response);
        }
        // Save the response along with answers
        return responseRepository.save(response);
    }
}
