package com.rahul.project.quizapp.services;

import com.rahul.project.quizapp.models.Question;
import com.rahul.project.quizapp.models.Quiz;
import com.rahul.project.quizapp.repos.QuestionRepository;
import com.rahul.project.quizapp.repos.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Quiz createQuiz(String category, Integer numOfQues, String difficultyLevel) {
        Quiz quiz = new Quiz();

        quiz.setDifficultyLevel(difficultyLevel);
        if(difficultyLevel.equalsIgnoreCase("MIXED")) {
            List<Question> questions = questionRepository.findNRandomQuestionsByCategory(category, numOfQues);
            quiz.setQuestions(questions);
        }
        else{
            List<Question> questions = questionRepository.findNRandomQuestionsByCategoryAndLevel(category, numOfQues, difficultyLevel);
            quiz.setQuestions(questions);
        }

        Quiz createdQuiz = quizRepository.save(quiz);

        return createdQuiz;

    }

}
