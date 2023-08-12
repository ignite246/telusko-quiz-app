package com.rahul.project.quizapp.services;

import com.rahul.project.quizapp.models.Question;
import com.rahul.project.quizapp.repos.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Integer questionId) {
        return questionRepository.findById(questionId).get();
    }


    public List<Question> getQuestionOfSameCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public List<Question> getQuestionOfSameDifficultyLevel(String difficultyLevel) {
        return questionRepository.findByDifficultyLevel(difficultyLevel);
    }

    public List<Question> getQuestionOfSameCategoryAndDifficultyLevel(String category, String difficultyLevel) {
        return questionRepository.findByCategoryAndDifficultyLevel(category, difficultyLevel);
    }

}
