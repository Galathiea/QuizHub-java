package com.ustp.quizhub.repository;

import com.ustp.quizhub.model.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, Long> {
    List<FlashcardSet> findBySubject(String subject);
    List<FlashcardSet> findByCreatorId(Long creatorId);
    List<FlashcardSet> findByTitleContainingIgnoreCase(String title);
    List<FlashcardSet> findByIsPublicTrue();
} 