package com.rahul.project.quizapp.dtos;

import lombok.Data;

@Data
public class QuestionWrapper {
    private Integer id;
    private String description;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(Integer id, String description, String option1, String option2, String option3, String option4) {
        this.id = id;
        this.description = description;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
