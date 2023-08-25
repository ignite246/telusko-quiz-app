package com.rahul.project.quizapp.controllers;

import com.rahul.project.quizapp.dtos.QuizEvaluationRequest;
import com.rahul.project.quizapp.dtos.QuizEvaluationResponse;
import com.rahul.project.quizapp.dtos.QuizWrapper;
import com.rahul.project.quizapp.models.Quiz;
import com.rahul.project.quizapp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestParam("category") String category,
                                           @RequestParam(value = "num-of-ques", defaultValue = "5") Integer numOfQues,
                                           @RequestParam(value = "difficulty-level", defaultValue = "MIXED") String difficultyLevel) {

        final Quiz createdQuiz = quizService.createQuiz(category, numOfQues, difficultyLevel);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    @GetMapping("/get-quiz-by-id-with-ans/{id}")
    public ResponseEntity<Quiz> getQuizWithAns(@PathVariable("id") Integer quizId){
       final Quiz quiz = quizService.getQuizByIdWithAnswers(quizId);
       if(!Objects.isNull(quiz)){
           return new ResponseEntity<>(quiz,HttpStatus.FOUND);
       }
       else {
           return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
       }

    }

    @GetMapping("/get-quiz-by-id/{id}")
    public ResponseEntity<QuizWrapper> getQuiz(@PathVariable("id") Integer quizId){
        final QuizWrapper quizWrapper = quizService.getQuizById(quizId);
        return new ResponseEntity<>(quizWrapper,HttpStatus.FOUND);
    }

    @PostMapping("/submit")
    public QuizEvaluationResponse submitQuiz(@RequestBody QuizEvaluationRequest request){
        return quizService.evaluateQuiz(request);
    }
}
