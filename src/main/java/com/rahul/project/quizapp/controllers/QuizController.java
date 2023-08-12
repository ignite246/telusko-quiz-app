package com.rahul.project.quizapp.controllers;

import com.rahul.project.quizapp.models.Quiz;
import com.rahul.project.quizapp.services.QuizService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestParam("category") String category,
                                           @RequestParam("num-of-ques") Integer numOfQues,
                                           @RequestParam(value = "difficulty-level", defaultValue = "MIXED") String difficultyLevel) {

        final Quiz createdQuiz = quizService.createQuiz(category, numOfQues, difficultyLevel);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }
}
