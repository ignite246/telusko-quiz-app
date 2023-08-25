package com.rahul.project.quizapp.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class QuizEvaluationResponse {
    private Integer quizId;
    private Integer marksObtained;
    private Map<Integer, String> answers;
}
