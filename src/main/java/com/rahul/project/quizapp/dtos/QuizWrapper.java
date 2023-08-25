package com.rahul.project.quizapp.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuizWrapper {

    private Integer id;
    private String difficultyLevel;
    private List<QuestionWrapper> questions;
}
