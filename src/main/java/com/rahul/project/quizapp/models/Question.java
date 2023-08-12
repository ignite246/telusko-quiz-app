package com.rahul.project.quizapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private String difficultyLevel;
    private String description;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
}
