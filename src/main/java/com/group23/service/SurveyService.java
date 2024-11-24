package com.group23.service;

import com.group23.dto.QuestionForm;
import com.group23.dto.SurveyResultDTO;
import com.group23.model.*;
import com.group23.repository.SurveyRepository;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for handling survey operations.
 */
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    public Survey updateSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Boolean deleteSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + id));
        try{
            surveyRepository.delete(survey);
        }catch (Exception e){
            System.out.println("Failed to delete survey.");
            return false;
        }
        return true;
    }

    public List<Survey> listAllSurveys() {
        return surveyRepository.findAll();
    }

    public void closeSurvey(Long id) {
        Survey survey = getSurveyById(id);
        if (survey != null) {
            survey.setIsOpen(false);
            surveyRepository.save(survey);
        }
    }

    public List<SurveyResultDTO> exportAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream()
                .map(this::convertToSurveyResultDTO)
                .collect(Collectors.toList());
    }

    private QuestionForm convertQuestionToDTO(Question question) {
        QuestionForm form = new QuestionForm();
        form.setText(question.getText());
        form.setType(question.getClass().getSimpleName());

        if (question instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
            form.setOptions(mcq.getOptions().stream()
                    .map(Option::getText)
                    .collect(Collectors.toList()));
        } else if (question instanceof NumericRangeQuestion) {
            NumericRangeQuestion nrq = (NumericRangeQuestion) question;
            form.setMinValue(nrq.getMinValue());
            form.setMaxValue(nrq.getMaxValue());
        }

        return form;
    }



    private SurveyResultDTO convertToSurveyResultDTO(Survey survey) {
        SurveyResultDTO result = new SurveyResultDTO();
        result.setSurveyId(survey.getId());
        result.setTitle(survey.getTitle());
        result.setDescription(survey.getDescription());
        result.setOpen(survey.getIsOpen());
        result.setQuestions(survey.getQuestions().stream()
                .map(this::convertQuestionToDTO)
                .collect(Collectors.toList()));
        result.setOpenEndedResults(new HashMap<>());
        result.setNumericResults(new HashMap<>());
        result.setChoiceResults(new HashMap<>());
        return result;
    }



    public void importSurveys(List<SurveyResultDTO> surveyResults) {
        for (SurveyResultDTO dto : surveyResults) {
            Survey survey = new Survey();
            survey.setId(dto.getSurveyId());
            survey.setTitle(dto.getTitle());
            survey.setDescription(dto.getDescription());
            survey.setIsOpen(dto.isOpen());

            // Process questions
            List<Question> questions = dto.getQuestions().stream()
                    .map(this::convertFromQuestionForm)
                    .collect(Collectors.toList());
            for (Question question : questions) {
                question.setSurvey(survey); // Link questions to survey
            }
            survey.setQuestions(questions);

            // Process responses
            processResponses(survey, dto);

            // Save the survey
            surveyRepository.save(survey);
        }
    }

    private void processResponses(Survey survey, SurveyResultDTO dto) {
        for (Map.Entry<Long, List<String>> entry : dto.getOpenEndedResults().entrySet()) {
            Question question = getQuestionById(survey, entry.getKey());
            if (question instanceof OpenEndedQuestion) {
                for (String text : entry.getValue()) {
                    Answer answer = new Answer();
                    answer.setQuestion(question);
                    answer.setText(text);
                    question.getAnswers().add(answer);
                }
            }
        }

        for (Map.Entry<Long, Map<Integer, Integer>> entry : dto.getNumericResults().entrySet()) {
            Question question = getQuestionById(survey, entry.getKey());
            if (question instanceof NumericRangeQuestion) {
                for (Map.Entry<Integer, Integer> countEntry : entry.getValue().entrySet()) {
                    int value = countEntry.getKey();
                    int count = countEntry.getValue();
                    for (int i = 0; i < count; i++) {
                        Answer answer = new Answer();
                        answer.setQuestion(question);
                        answer.setNumber(value);
                        question.getAnswers().add(answer);
                    }
                }
            }
        }

        for (Map.Entry<Long, Map<Long, Integer>> entry : dto.getChoiceResults().entrySet()) {
            Question question = getQuestionById(survey, entry.getKey());
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
                for (Map.Entry<Long, Integer> countEntry : entry.getValue().entrySet()) {
                    Long optionId = countEntry.getKey();
                    int count = countEntry.getValue();
                    Option option = getOptionById(mcq, optionId);
                    for (int i = 0; i < count; i++) {
                        Answer answer = new Answer();
                        answer.setQuestion(question);
                        answer.setSelectedOptionId(option.getId());
                        question.getAnswers().add(answer);
                    }
                }
            }
        }
    }

    private Question getQuestionById(Survey survey, Long questionId) {
        return survey.getQuestions().stream()
                .filter(q -> questionId != null && questionId.equals(q.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));
    }

    private Option getOptionById(MultipleChoiceQuestion question, Long optionId) {
        return question.getOptions().stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Option not found with ID: " + optionId));
    }




    private Question convertFromQuestionForm(QuestionForm form) {
        switch (form.getType()) {
            case "MultipleChoiceQuestion":
                MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
                mcq.setText(form.getText());
                mcq.setOptions(form.getOptions().stream()
                        .map(optionText -> {
                            Option option = new Option();
                            option.setText(optionText);
                            return option;
                        })
                        .toList());
                return mcq;
            case "NumericRangeQuestion":
                NumericRangeQuestion nrq = new NumericRangeQuestion();
                nrq.setText(form.getText());
                nrq.setMinValue(form.getMinValue());
                nrq.setMaxValue(form.getMaxValue());
                return nrq;
            case "OpenEndedQuestion":
                OpenEndedQuestion oq = new OpenEndedQuestion();
                oq.setText(form.getText());
                return oq;
            default:
                throw new IllegalArgumentException("Unknown question type: " + form.getType());
        }
    }



}
