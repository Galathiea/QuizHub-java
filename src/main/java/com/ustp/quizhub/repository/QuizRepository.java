package com.ustp.quizhub.repository;

import com.ustp.quizhub.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findBySubject(String subject);
    List<Quiz> findByCreatorId(Long creatorId);
    List<Quiz> findByTitleContainingIgnoreCase(String title);
    List<Quiz> findByIsPublicTrue();
} 