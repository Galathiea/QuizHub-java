package com.ustp.quizhub.controller;

import com.ustp.quizhub.model.Quiz;
import com.ustp.quizhub.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    // Automatically create a quiz from a flashcard set
    @PostMapping("/from-flashcard-set/{flashcardSetId}")
    public ResponseEntity<Quiz> createQuizFromFlashcardSet(@PathVariable Long flashcardSetId) {
        try {
            Quiz quiz = quizService.createQuizFromFlashcardSet(flashcardSetId);
            return ResponseEntity.ok(quiz);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
} 