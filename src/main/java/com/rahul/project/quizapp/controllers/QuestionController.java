package com.rahul.project.quizapp.controllers;

import com.rahul.project.quizapp.models.Question;
import com.rahul.project.quizapp.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("/get-all-questions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/get-by-id/{id}")
    public Question getQuestionById(@PathVariable("id") Integer questionId) {
        return questionService.getQuestionById(questionId);
    }

    @GetMapping("/get-by-category")
    public List<Question> getQuestionOfSameCategory(@RequestParam("category") String  category) {
        return questionService.getQuestionOfSameCategory(category);
    }

    @GetMapping("/get-by-difficulty-level")
    public List<Question> getQuestionWithSameDifficultyLevel(@RequestParam("difficulty-level") String difficultyLevel) {
        return questionService.getQuestionOfSameDifficultyLevel(difficultyLevel);
    }



}
