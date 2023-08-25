package com.rahul.project.quizapp.services;

import com.rahul.project.quizapp.dtos.QuestionWrapper;
import com.rahul.project.quizapp.dtos.QuizEvaluationRequest;
import com.rahul.project.quizapp.dtos.QuizEvaluationResponse;
import com.rahul.project.quizapp.dtos.QuizWrapper;
import com.rahul.project.quizapp.models.Question;
import com.rahul.project.quizapp.models.Quiz;
import com.rahul.project.quizapp.repos.QuestionRepository;
import com.rahul.project.quizapp.repos.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Quiz createQuiz(String category, Integer numOfQues, String difficultyLevel) {
        Quiz quiz = new Quiz();

        quiz.setDifficultyLevel(difficultyLevel);
        if (difficultyLevel.equalsIgnoreCase("MIXED")) {
            List<Question> questions = questionRepository.findNRandomQuestionsByCategory(category, numOfQues);
            quiz.setQuestions(questions);
        } else {
            List<Question> questions = questionRepository.findNRandomQuestionsByCategoryAndLevel(category, numOfQues, difficultyLevel);
            quiz.setQuestions(questions);
        }

        Quiz createdQuiz = quizRepository.save(quiz);

        return createdQuiz;

    }

    public Quiz getQuizByIdWithAnswers(Integer quizId) {
        final Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        Quiz quizFound = null;
        try {
            if (quizOptional.isPresent()) {
                quizFound = quizOptional.get();
            } else {
                throw new RuntimeException("Quiz doesn't exist with ID :: " + quizId);
            }

        } catch (RuntimeException ex) {
            log.info("Exception occurred while fetching quiz with ans :: {}", ex.getMessage());
        }
        return quizFound;
    }

    public QuizWrapper getQuizById(Integer quizId) {
        final Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        QuizWrapper quizWrapper = null;
        try {
            if (quizOptional.isPresent()) {
                Quiz quizFound = quizOptional.get();

                quizWrapper = new QuizWrapper();
                quizWrapper.setId(quizFound.getId());
                quizWrapper.setDifficultyLevel(quizFound.getDifficultyLevel());

                final List<QuestionWrapper> questionForClient =
                        quizFound.getQuestions().stream().map(question -> {
                            QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getDescription(),
                                    question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
                            return questionWrapper;
                        }).collect(Collectors.toList());

                quizWrapper.setQuestions(questionForClient);


            } else {
                throw new RuntimeException("Quiz doesn't exist with ID :: " + quizId);
            }

        } catch (RuntimeException ex) {
            log.info("Exception occurred while fetching quiz with ID :: {} :: {}", quizId, ex.getMessage());
        }
        return quizWrapper;
    }

    public QuizEvaluationResponse evaluateQuiz(QuizEvaluationRequest request) {
        final List<Question> quesAnsFromSource = getQuizByIdWithAnswers(request.getQuizId()).getQuestions();
        final Map<Integer, String> quesAnsFromUser = request.getAnswers();

        log.info("quesAnsFromSource :: {}", quesAnsFromSource);
        log.info("quesAnsFromUser :: {}", quesAnsFromUser);

        final QuizEvaluationResponse quizEvaluationResponse = new QuizEvaluationResponse();
        quizEvaluationResponse.setQuizId(request.getQuizId());
        Map<Integer, String> questWithCorrectAnsMap = new HashMap<>(quesAnsFromUser.size());

        List<Integer> score=new ArrayList<>();
        quesAnsFromUser.forEach((quesId, chosenOption) -> {
            String correctOption = getCorrectOption(quesId, quesAnsFromSource);
            questWithCorrectAnsMap.put(quesId, correctOption!=null?correctOption:"INVALID QUESTION ID");
            if (chosenOption.equalsIgnoreCase(correctOption)) {
                log.info("Correct");
                score.add(1);
            }

        });

        quizEvaluationResponse.setMarksObtained(score.size());
        quizEvaluationResponse.setAnswers(questWithCorrectAnsMap);

        return quizEvaluationResponse;

    }

    private String getCorrectOption(Integer quesId, List<Question> questAnsList) {
        for (Question question : questAnsList) {
            if (question.getId().equals(quesId)) {
                return question.getCorrectOption();
            }
        }
        return null;
    }
}
