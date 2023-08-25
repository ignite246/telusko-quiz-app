package com.rahul.project.quizapp.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class QuizEvaluationRequest {
    private Integer quizId;
    private Map<Integer,String> answers;
}
