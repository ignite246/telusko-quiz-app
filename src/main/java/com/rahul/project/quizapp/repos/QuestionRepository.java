package com.rahul.project.quizapp.repos;

import com.rahul.project.quizapp.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);
    List<Question> findByDifficultyLevel(String difficultyLevel);
    List<Question> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);

    @Query(value = "SELECT * FROM questions where category=:category ORDER BY RAND() LIMIT :numOfQues",nativeQuery = true)
    List<Question> findNRandomQuestionsByCategory(String category, Integer numOfQues);

    @Query(value = "SELECT * FROM questions where category=:category and difficulty_level=:level ORDER BY RAND() LIMIT :numOfQues",nativeQuery = true)
    List<Question> findNRandomQuestionsByCategoryAndLevel(String category, Integer numOfQues, String level);
}
